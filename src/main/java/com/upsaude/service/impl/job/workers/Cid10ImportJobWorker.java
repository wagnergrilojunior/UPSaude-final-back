package com.upsaude.service.impl.job.workers;

import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.entity.sistema.importacao.ImportJob;
import com.upsaude.entity.sistema.importacao.ImportJobError;
import com.upsaude.enums.ImportJobStatusEnum;
import com.upsaude.enums.ImportJobTipoEnum;
import com.upsaude.exception.ImportJobFatalException;
import com.upsaude.exception.ImportJobRecoverableException;
import com.upsaude.exception.InvalidArgumentException;
import com.upsaude.importacao.cid10.file.Cid10CsvParser;
import com.upsaude.integration.supabase.SupabaseStorageService;
import com.upsaude.mapper.referencia.cid.Cid10EntityMapper;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class Cid10ImportJobWorker implements ImportJobWorker {

    private static final int ERROR_SAMPLE_LIMIT = 200;

    // USO EXCLUSIVO JOB - Worker usa pool JOB
    private final com.upsaude.repository.sistema.importacao.ImportJobJobRepository importJobJobRepository;
    private final com.upsaude.repository.sistema.importacao.ImportJobErrorRepository importJobErrorRepository;
    private final SupabaseStorageService supabaseStorageService;

    private final Cid10CsvParser csvParser;
    private final Cid10EntityMapper entityMapper;
    private final JdbcEntityBatchWriter jdbcEntityBatchWriter;

    // USO EXCLUSIVO JOB - TransactionManager do JOB
    @Qualifier("jobTransactionManager")
    private final PlatformTransactionManager jobTransactionManager;

    // USO EXCLUSIVO JOB - EntityManager do JOB (persistence unit "job")
    @PersistenceContext(unitName = "job")
    private EntityManager entityManager;

    @Value("${import.job.batch-size.cid10:${import.job.batch-size:1000}}")
    private int batchSize;

    @Value("${import.job.progress-log-interval-seconds:15}")
    private int progressLogIntervalSeconds;

    @Value("${import.job.tx.timeout-seconds.cid10:60}")
    private int batchTxTimeoutSeconds;

    @Override
    public ImportJobTipoEnum getTipo() {
        return ImportJobTipoEnum.CID10;
    }

    @Override
    public void processar(UUID jobId) {
        if (jobId == null) return;

        int effectiveBatchSize = clamp(batchSize, 100, 5000);
        long progressLogIntervalMs = Math.max(1, progressLogIntervalSeconds) * 1000L;

        TransactionTemplate txReadSnapshot = new TransactionTemplate(jobTransactionManager);
        txReadSnapshot.setReadOnly(true);
        txReadSnapshot.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionTemplate txBatchCommit = new TransactionTemplate(jobTransactionManager);
        txBatchCommit.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        // PostgreSQL usa READ_COMMITTED por padrão, que é o nível desejado
        // Não configuramos isolamento customizado para evitar problemas com Hibernate 6.x
        txBatchCommit.setTimeout(Math.max(5, batchTxTimeoutSeconds));

        ImportJob job = txReadSnapshot.execute(status -> importJobJobRepository.findById(jobId).orElse(null));
        if (job == null) {
            log.error("Job {} não encontrado para processamento CID10", jobId);
            return;
        }

        if (job.getStatus() != ImportJobStatusEnum.PROCESSANDO) {
            log.warn("Job {} (CID10) não está em PROCESSANDO. Status atual: {}. Ignorando processamento.", jobId, job.getStatus());
            return;
        }

        validarJob(job);

        UUID tenantId = job.getTenant() != null ? job.getTenant().getId() : null;
        String competencia = (job.getCompetenciaAno() != null && job.getCompetenciaMes() != null)
                ? job.getCompetenciaAno() + job.getCompetenciaMes()
                : null;

        String nomeArquivo = nomeArquivo(job);
        ImportStrategy<?> strategy = strategyPorNomeArquivo(nomeArquivo, competencia);
        if (strategy == null) {
            throw new ImportJobFatalException("Arquivo CID10 não suportado: " + nomeArquivo);
        }

        long checkpoint = job.getCheckpointLinha() != null ? job.getCheckpointLinha() : 0L; // data lines (sem header)
        long lastCommittedLineIndex = checkpoint; // última linha (data line) efetivamente commitada no banco
        long linhasLidasTotal = job.getLinhasLidas() != null ? job.getLinhasLidas() : 0L;
        long linhasProcessadasTotal = job.getLinhasProcessadas() != null ? job.getLinhasProcessadas() : 0L;
        long linhasInseridasTotal = job.getLinhasInseridas() != null ? job.getLinhasInseridas() : 0L;
        long linhasErroTotal = job.getLinhasErro() != null ? job.getLinhasErro() : 0L;

        long inicio = System.currentTimeMillis();
        long lastProgressLogAtMs = System.currentTimeMillis();

        List<Object> batch = new ArrayList<>(Math.max(100, effectiveBatchSize));

        try (InputStream is = supabaseStorageService.downloadStream(job.getStorageBucket(), job.getStoragePath());
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

            String headerLine = reader.readLine();
            if (!StringUtils.hasText(headerLine)) {
                throw new IllegalArgumentException("Arquivo CSV vazio ou sem cabeçalho");
            }

            String[] headers = parseHeaders(headerLine);

            // Skip checkpoint (data lines)
            for (long i = 0; i < checkpoint; i++) {
                if (reader.readLine() == null) break;
            }

            String linha;
            long linhaDataIndex = checkpoint;

            while ((linha = reader.readLine()) != null) {
                linhaDataIndex++;
                linhasLidasTotal++;

                if (!StringUtils.hasText(linha)) continue;

                try {
                    Map<String, String> fields = csvParser.parseLine(linha, headers);
                    if (!validarCamposBasicos(fields)) {
                        linhasErroTotal++;
                        salvarErroSeCouber(txBatchCommit, jobId, tenantId, linhaDataIndex, "VALIDACAO", "Linha sem campos válidos", linha);
                        continue;
                    }

                    Object entity = strategy.mapper.apply(fields);
                    if (entity == null) {
                        linhasErroTotal++;
                        salvarErroSeCouber(txBatchCommit, jobId, tenantId, linhaDataIndex, "MAPPER", "Entidade nula após mapeamento", linha);
                        continue;
                    }

                    batch.add(entity);
                    linhasProcessadasTotal++;

                    if (batch.size() >= effectiveBatchSize) {
                        long commitLineIndex = linhaDataIndex;
                        int sizeAtual = batch.size();
                        persistirBatchLookupEAtualizarCheckpoint(
                                txBatchCommit, jobId, strategy, batch,
                                linhasLidasTotal, linhasProcessadasTotal, linhasInseridasTotal + sizeAtual, linhasErroTotal,
                                commitLineIndex
                        );
                        linhasInseridasTotal += sizeAtual;
                        lastCommittedLineIndex = commitLineIndex;
                        batch.clear();

                        long nowMs = System.currentTimeMillis();
                        if ((nowMs - lastProgressLogAtMs) >= progressLogIntervalMs) {
                            log.info("Job {} (CID10:{}) progresso: lidas={}, processadas={}, inseridas={}, erros={}, checkpoint={}",
                                    jobId, nomeArquivo, linhasLidasTotal, linhasProcessadasTotal, linhasInseridasTotal, linhasErroTotal, lastCommittedLineIndex);
                            lastProgressLogAtMs = nowMs;
                        }
                    }
                } catch (InvalidArgumentException | IllegalArgumentException e) {
                    linhasErroTotal++;
                    salvarErroSeCouber(txBatchCommit, jobId, tenantId, linhaDataIndex, "VALIDACAO", e.getMessage(), linha);
                } catch (Exception e) {
                    linhasErroTotal++;
                    salvarErroSeCouber(txBatchCommit, jobId, tenantId, linhaDataIndex, "EXCEPTION", e.getMessage(), linha);
                }
            }

            if (!batch.isEmpty()) {
                long commitLineIndex = linhaDataIndex;
                int sizeAtual = batch.size();
                persistirBatchLookupEAtualizarCheckpoint(
                        txBatchCommit, jobId, strategy, batch,
                        linhasLidasTotal, linhasProcessadasTotal, linhasInseridasTotal + sizeAtual, linhasErroTotal,
                        commitLineIndex
                );
                linhasInseridasTotal += sizeAtual;
                lastCommittedLineIndex = commitLineIndex;
                batch.clear();
            }

            long duracao = System.currentTimeMillis() - inicio;
            finalizar(txBatchCommit, jobId, linhasLidasTotal, linhasProcessadasTotal, linhasInseridasTotal, linhasErroTotal, lastCommittedLineIndex, duracao);
        } catch (Exception e) {
            log.error("Erro fatal no job {} (CID10): {}", jobId, e.getMessage(), e);
            if (e instanceof ImportJobRecoverableException re) throw re;
            if (e instanceof ImportJobFatalException fe) throw fe;
            throw new ImportJobFatalException("Erro fatal no CID10: " + e.getMessage(), e);
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

    private String nomeArquivo(ImportJob job) {
        if (StringUtils.hasText(job.getOriginalFilename())) return job.getOriginalFilename().toUpperCase();
        String path = job.getStoragePath();
        if (!StringUtils.hasText(path)) return "UNKNOWN";
        int idx = path.lastIndexOf('/');
        return (idx >= 0 ? path.substring(idx + 1) : path).toUpperCase();
    }

    private String[] parseHeaders(String linha) {
        if (!StringUtils.hasText(linha)) throw new InvalidArgumentException("Linha de headers vazia");
        return linha.split(";", -1);
    }

    private boolean validarCamposBasicos(Map<String, String> fields) {
        if (fields == null || fields.isEmpty()) return false;
        return fields.values().stream().anyMatch(v -> v != null && !v.trim().isEmpty());
    }

    /**
     * Commit atômico por batch: persiste o batch + atualiza checkpoint/heartbeat no MESMO commit.
     * Isso garante que checkpoint nunca avance além do que foi efetivamente persistido.
     */
    private void persistirBatchLookupEAtualizarCheckpoint(TransactionTemplate txBatchCommit,
                                                         UUID jobId,
                                                         ImportStrategy<?> strategy,
                                                         List<Object> batch,
                                                         long lidas,
                                                         long processadas,
                                                         long inseridas,
                                                         long erros,
                                                         long checkpointLinha) {
        txBatchCommit.executeWithoutResult(status -> {
            // UPSERT em JDBC baseado em @UniqueConstraint (evita lookup/merge via JPA)
            @SuppressWarnings("unchecked")
            List<Object> entities = (List<Object>) (List<?>) batch;
            jdbcEntityBatchWriter.upsertBatch(entities);

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
                           UUID jobId,
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
                                   UUID jobId,
                                   UUID tenantId,
                                   long linhaIndex,
                                   String codigo,
                                   String mensagem,
                                   String rawLine) {
        if (tenantId == null) return;
        txBatchCommit.executeWithoutResult(status -> {
            ImportJob j = importJobJobRepository.findById(jobId).orElse(null);
            if (j == null) return;
            if (j.getLinhasErro() != null && j.getLinhasErro() >= ERROR_SAMPLE_LIMIT) return;

            ImportJobError err = new ImportJobError();
            Tenant tenantRef = entityManager.getReference(Tenant.class, tenantId);
            err.setTenant(tenantRef);
            err.setJob(j);
            err.setLinha(linhaIndex);
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
            return java.util.HexFormat.of().formatHex(md.digest(raw.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            return null;
        }
    }

    private ImportStrategy<?> strategyPorNomeArquivo(String nomeArquivo, String competencia) {
        return switch (nomeArquivo) {
            case "CID-10-CAPITULOS.CSV" -> new ImportStrategy<>(fields -> entityMapper.mapToCapitulo(fields, competencia));
            case "CID-10-GRUPOS.CSV" -> new ImportStrategy<>(fields -> entityMapper.mapToGrupo(fields, competencia));
            case "CID-10-CATEGORIAS.CSV" -> new ImportStrategy<>(fields -> entityMapper.mapToCategoria(fields, competencia));
            case "CID-10-SUBCATEGORIAS.CSV" -> new ImportStrategy<>(fields -> entityMapper.mapToSubcategoria(fields, competencia));
            case "CID-O-GRUPOS.CSV" -> new ImportStrategy<>(fields -> entityMapper.mapToCidOGrupo(fields, competencia));
            case "CID-O-CATEGORIAS.CSV" -> new ImportStrategy<>(fields -> entityMapper.mapToCidOCategoria(fields, competencia));
            default -> null;
        };
    }

    private static class ImportStrategy<T> {
        final Function<Map<String, String>, T> mapper;
        ImportStrategy(Function<Map<String, String>, T> mapper) {
            this.mapper = mapper;
        }
    }
}


