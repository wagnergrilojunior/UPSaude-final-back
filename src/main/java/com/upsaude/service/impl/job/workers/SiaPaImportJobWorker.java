package com.upsaude.service.impl.job.workers;

import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.entity.sistema.importacao.ImportJob;
import com.upsaude.entity.sistema.importacao.ImportJobError;
import com.upsaude.entity.referencia.sia.SiaPa;
import com.upsaude.enums.ImportJobStatusEnum;
import com.upsaude.enums.ImportJobTipoEnum;
import com.upsaude.exception.ImportJobFatalException;
import com.upsaude.exception.ImportJobRecoverableException;
import com.upsaude.importacao.sia.file.SiaPaCsvParser;
import com.upsaude.integration.supabase.SupabaseStorageService;
import com.upsaude.mapper.sia.SiaPaEntityMapper;
import com.upsaude.repository.sistema.importacao.ImportJobErrorRepository;
import com.upsaude.repository.sistema.importacao.ImportJobJobRepository;
import com.upsaude.service.impl.job.jdbc.JdbcEntityBatchWriter;
import com.upsaude.service.job.ImportJobWorker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SiaPaImportJobWorker implements ImportJobWorker {

    private static final int ERROR_SAMPLE_LIMIT = 200;

    private final ImportJobJobRepository importJobJobRepository;
    private final ImportJobErrorRepository importJobErrorRepository;

    private final SupabaseStorageService supabaseStorageService;
    private final SiaPaCsvParser csvParser;
    private final SiaPaEntityMapper entityMapper;
    private final JdbcEntityBatchWriter jdbcEntityBatchWriter;

    @Qualifier("jobTransactionManager")
    private final PlatformTransactionManager jobTransactionManager;

    @PersistenceContext(unitName = "job")
    private EntityManager entityManager;

    @Value("${import.job.batch-size.sia-pa:${import.job.batch-size:1000}}")
    private int batchSize;

    @Value("${sia.import.encoding:UTF-8}")
    private String encoding;

    @Value("${import.job.progress-log-interval-seconds:15}")
    private int progressLogIntervalSeconds;

    @Value("${import.job.heartbeat-timeout-seconds:300}")
    private int heartbeatTimeoutSeconds;

    @Value("${import.job.tx.timeout-seconds.sia-pa:60}")
    private int batchTxTimeoutSeconds;

    @Override
    public ImportJobTipoEnum getTipo() {
        return ImportJobTipoEnum.SIA_PA;
    }

    @Override
    public void processar(java.util.UUID jobId) {
        if (jobId == null) return;

        Charset charset = Charset.forName(StringUtils.hasText(encoding) ? encoding : "UTF-8");
        int effectiveBatchSize = clamp(batchSize, 100, 5000);
        long progressLogIntervalMs = Math.max(1, progressLogIntervalSeconds) * 1000L;

        TransactionTemplate txReadSnapshot = new TransactionTemplate(jobTransactionManager);
        txReadSnapshot.setReadOnly(true);
        txReadSnapshot.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionTemplate txBatchCommit = new TransactionTemplate(jobTransactionManager);
        txBatchCommit.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

        txBatchCommit.setTimeout(Math.max(5, batchTxTimeoutSeconds));

        ImportJob jobSnapshot = txReadSnapshot.execute(status -> importJobJobRepository.findById(jobId).orElse(null));
        if (jobSnapshot == null) {
            log.error("Job {} não encontrado para processamento SIA_PA", jobId);
            return;
        }

        if (jobSnapshot.getStatus() != ImportJobStatusEnum.PROCESSANDO) {
            log.warn("Job {} (SIA_PA) não está em PROCESSANDO. Status atual: {}. Ignorando processamento.", jobId, jobSnapshot.getStatus());
            return;
        }

        validarJob(jobSnapshot);

        java.util.UUID tenantId = jobSnapshot.getTenant() != null ? jobSnapshot.getTenant().getId() : null;

        long checkpoint = jobSnapshot.getCheckpointLinha() != null ? jobSnapshot.getCheckpointLinha() : 0L; 
        long lastCommittedLineIndex = checkpoint; 
        long linhasLidasTotal = jobSnapshot.getLinhasLidas() != null ? jobSnapshot.getLinhasLidas() : 0L;
        long linhasProcessadasTotal = jobSnapshot.getLinhasProcessadas() != null ? jobSnapshot.getLinhasProcessadas() : 0L;
        long linhasInseridasTotal = jobSnapshot.getLinhasInseridas() != null ? jobSnapshot.getLinhasInseridas() : 0L;
        long linhasErroTotal = jobSnapshot.getLinhasErro() != null ? jobSnapshot.getLinhasErro() : 0L;

        long lastProgressLogAtMs = System.currentTimeMillis();
        long lastHeartbeatAtMs = System.currentTimeMillis();
        long inicio = System.currentTimeMillis();

        long heartbeatIntervalMs = Math.min(30_000, (heartbeatTimeoutSeconds * 1000L) / 2);

        atualizarHeartbeat(txBatchCommit, jobId);
        lastHeartbeatAtMs = System.currentTimeMillis();

        List<SiaPa> batch = new ArrayList<>(Math.max(100, effectiveBatchSize));

        try (InputStream is = supabaseStorageService.downloadStream(jobSnapshot.getStorageBucket(), jobSnapshot.getStoragePath());
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, charset))) {

            long downloadEndMs = System.currentTimeMillis();
            if ((downloadEndMs - lastHeartbeatAtMs) >= heartbeatIntervalMs) {
                atualizarHeartbeat(txBatchCommit, jobId);
                lastHeartbeatAtMs = downloadEndMs;
            }

            String headerLine = reader.readLine();
            if (!StringUtils.hasText(headerLine)) {
                throw new IllegalArgumentException("Arquivo CSV vazio ou sem cabeçalho");
            }

            String[] headers = parseHeaders(headerLine);

            for (long i = 0; i < checkpoint; i++) {
                String skipped = reader.readLine();
                if (skipped == null) {
                    break;
                }

                if (i > 0 && i % 10000 == 0) {
                    long skipCheckMs = System.currentTimeMillis();
                    if ((skipCheckMs - lastHeartbeatAtMs) >= heartbeatIntervalMs) {
                        atualizarHeartbeat(txBatchCommit, jobId);
                        lastHeartbeatAtMs = skipCheckMs;
                    }
                }
            }

            String linha;
            long linhaDataIndex = checkpoint; 

            while ((linha = reader.readLine()) != null) {
                linhaDataIndex++;
                linhasLidasTotal++;

                if (!StringUtils.hasText(linha)) {
                    continue;
                }

                try {
                    Map<String, String> fields = csvParser.parseLine(linha, headers);

                    String procedimento = fields.get("PA_PROC_ID");
                    if (!StringUtils.hasText(procedimento)) {
                        linhasErroTotal++;
                        salvarErroSeCouber(txBatchCommit, jobId, tenantId, linhaDataIndex, "VALIDACAO", "Campo PA_PROC_ID obrigatório ausente", linha);
                        continue;
                    }

                    SiaPa entity = entityMapper.mapToSiaPa(fields, jobSnapshot.getCompetenciaAno() != null && jobSnapshot.getCompetenciaMes() != null
                                    ? jobSnapshot.getCompetenciaAno() + jobSnapshot.getCompetenciaMes()
                                    : null,
                            jobSnapshot.getUf());

                    if (entity == null) {
                        linhasErroTotal++;
                        salvarErroSeCouber(txBatchCommit, jobId, tenantId, linhaDataIndex, "MAPPER", "Entidade nula após mapeamento", linha);
                        continue;
                    }

                    batch.add(entity);
                    linhasProcessadasTotal++;

                    long nowMs = System.currentTimeMillis();
                    if ((nowMs - lastHeartbeatAtMs) >= heartbeatIntervalMs) {
                        atualizarHeartbeat(txBatchCommit, jobId);
                        lastHeartbeatAtMs = nowMs;
                    }

                    if (batch.size() >= effectiveBatchSize) {
                        long commitLineIndex = linhaDataIndex;
                        int batchSizeAtual = batch.size();
                        persistirBatchEAtualizarCheckpoint(
                                txBatchCommit, jobId, batch,
                                linhasLidasTotal, linhasProcessadasTotal, linhasInseridasTotal + batchSizeAtual, linhasErroTotal,
                                commitLineIndex
                        );
                        linhasInseridasTotal += batchSizeAtual;
                        lastCommittedLineIndex = commitLineIndex;
                        batch.clear();

                        if ((nowMs - lastProgressLogAtMs) >= progressLogIntervalMs) {
                            log.info("Job {} (SIA_PA) progresso: lidas={}, processadas={}, inseridas={}, erros={}, checkpoint={}",
                                    jobId, linhasLidasTotal, linhasProcessadasTotal, linhasInseridasTotal, linhasErroTotal, lastCommittedLineIndex);
                            lastProgressLogAtMs = nowMs;
                        }
                    }
                } catch (Exception e) {
                    linhasErroTotal++;
                    salvarErroSeCouber(txBatchCommit, jobId, tenantId, linhaDataIndex, "EXCEPTION", e.getMessage(), linha);
                }
            }

            if (!batch.isEmpty()) {
                long commitLineIndex = linhaDataIndex;
                int batchSizeAtual = batch.size();
                persistirBatchEAtualizarCheckpoint(
                        txBatchCommit, jobId, batch,
                        linhasLidasTotal, linhasProcessadasTotal, linhasInseridasTotal + batchSizeAtual, linhasErroTotal,
                        commitLineIndex
                );
                linhasInseridasTotal += batchSizeAtual;
                lastCommittedLineIndex = commitLineIndex;
                batch.clear();
            }

            long duracao = System.currentTimeMillis() - inicio;
            finalizar(txBatchCommit, jobId, linhasLidasTotal, linhasProcessadasTotal, linhasInseridasTotal, linhasErroTotal, lastCommittedLineIndex, duracao);
        } catch (ImportJobRecoverableException | ImportJobFatalException e) {
            throw e;
        } catch (IOException e) {

            log.warn("Erro de IO durante leitura do stream no job {} (SIA_PA): {} - Tipo: {}", jobId, e.getMessage(), e.getClass().getSimpleName());
            throw new ImportJobRecoverableException("Falha de rede durante leitura do arquivo (pode ser temporário): " + e.getMessage(), e);
        } catch (Exception e) {

            Throwable cause = e.getCause();
            if (cause instanceof IOException) {
                log.warn("Erro de rede (via causa) durante leitura do stream no job {} (SIA_PA): {} - Tipo: {}", jobId, e.getMessage(), cause.getClass().getSimpleName());
                throw new ImportJobRecoverableException("Falha de rede durante leitura do arquivo (pode ser temporário): " + e.getMessage(), e);
            }
            log.error("Erro fatal no job {} (SIA_PA): {}", jobId, e.getMessage(), e);
            throw new ImportJobFatalException("Erro fatal no SIA-PA: " + e.getMessage(), e);
        }
    }

    private int clamp(int value, int min, int max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    private void validarJob(ImportJob job) {
        if (!StringUtils.hasText(job.getStorageBucket()) || !StringUtils.hasText(job.getStoragePath()) || "pending".equals(job.getStoragePath())) {
            throw new IllegalArgumentException("Job sem referência válida de Storage (bucket/path)");
        }
    }

    private String[] parseHeaders(String headerLine) {

        return headerLine.replace("\"", "").split(",", -1);
    }

    private void atualizarHeartbeat(TransactionTemplate txBatchCommit, java.util.UUID jobId) {
        txBatchCommit.executeWithoutResult(status -> {
            ImportJob j = importJobJobRepository.findById(jobId).orElse(null);
            if (j != null && j.getStatus() == ImportJobStatusEnum.PROCESSANDO) {
                j.setHeartbeatAt(OffsetDateTime.now());
                importJobJobRepository.save(j);
            }
        });
    }

    private void persistirBatchEAtualizarCheckpoint(TransactionTemplate txBatchCommit,
                                                   java.util.UUID jobId,
                                                   List<SiaPa> batch,
                                                   long lidas,
                                                   long processadas,
                                                   long inseridas,
                                                   long erros,
                                                   long checkpointLinha) {
        txBatchCommit.executeWithoutResult(status -> {

            jdbcEntityBatchWriter.insertBatch(batch);

            ImportJob j = importJobJobRepository.findById(jobId).orElse(null);
            if (j == null) return;
            j.setLinhasLidas(lidas);
            j.setLinhasProcessadas(processadas);
            j.setLinhasInseridas(inseridas);
            j.setLinhasErro(erros);
            j.setCheckpointLinha(checkpointLinha);
            j.setHeartbeatAt(OffsetDateTime.now());
            importJobJobRepository.save(j);
        });
    }

    private void finalizar(TransactionTemplate txBatchCommit,
                           java.util.UUID jobId,
                           long lidas,
                           long processadas,
                           long inseridas,
                           long erros,
                           long checkpointLinha,
                           long duracaoMs) {
        txBatchCommit.executeWithoutResult(status -> {
            ImportJob j = importJobJobRepository.findById(jobId).orElse(null);
            if (j == null) return;
            j.setLinhasLidas(lidas);
            j.setLinhasProcessadas(processadas);
            j.setLinhasInseridas(inseridas);
            j.setLinhasErro(erros);
            j.setCheckpointLinha(checkpointLinha);
            j.setHeartbeatAt(OffsetDateTime.now());
            j.setFinishedAt(OffsetDateTime.now());
            j.setDurationMs(duracaoMs);
            j.setStatus(ImportJobStatusEnum.CONCLUIDO);
            if (erros > 0) {
                j.setErrorSummary("Concluído com " + erros + " erros");
            } else {
                j.setErrorSummary(null);
            }
            importJobJobRepository.save(j);
        });
    }

    private void salvarErroSeCouber(TransactionTemplate txBatchCommit,
                                   java.util.UUID jobId,
                                   java.util.UUID tenantId,
                                   long linhaDataIndex,
                                   String codigo,
                                   String mensagem,
                                   String rawLine) {
        if (linhaDataIndex <= 0) return;
        if (tenantId == null) return;

        txBatchCommit.executeWithoutResult(status -> {
            ImportJob j = importJobJobRepository.findById(jobId).orElse(null);
            if (j == null) return;
            if (j.getLinhasErro() != null && j.getLinhasErro() >= ERROR_SAMPLE_LIMIT) return;

            ImportJobError err = new ImportJobError();
            Tenant tenantRef = entityManager.getReference(Tenant.class, tenantId);
            err.setTenant(tenantRef);
            err.setJob(j);
            err.setLinha(linhaDataIndex);
            err.setCodigoErro(codigo);
            err.setMensagem(StringUtils.hasText(mensagem) ? mensagem : "Erro ao processar linha");
            err.setRawLinePreview(preview(rawLine));
            err.setRawLineHash(hashSha256(rawLine));
            importJobErrorRepository.save(err);
        });
    }

    private String preview(String rawLine) {
        if (rawLine == null) return null;
        String s = rawLine.trim();
        return s.length() <= 500 ? s : s.substring(0, 500);
    }

    private String hashSha256(String raw) {
        if (raw == null) return null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(raw.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(digest);
        } catch (Exception e) {
            return null;
        }
    }
}
