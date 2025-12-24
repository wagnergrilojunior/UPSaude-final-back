package com.upsaude.service.impl.job.workers;

import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.entity.sistema.importacao.ImportJob;
import com.upsaude.entity.sistema.importacao.ImportJobError;
import com.upsaude.enums.ImportJobStatusEnum;
import com.upsaude.enums.ImportJobTipoEnum;
import com.upsaude.exception.ImportJobFatalException;
import com.upsaude.exception.ImportJobRecoverableException;
import com.upsaude.importacao.sigtap.file.SigtapFileParser;
import com.upsaude.importacao.sigtap.file.SigtapLayoutDefinition;
import com.upsaude.integration.supabase.SupabaseStorageService;
import com.upsaude.mapper.sigtap.SigtapEntityMapper;
import com.upsaude.service.job.ImportJobWorker;
import com.upsaude.service.impl.job.jdbc.JdbcEntityBatchWriter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.function.Function;

/**
 * Worker de importação SIGTAP: processa 1 arquivo TXT + 1 arquivo de layout (CSV) por job.
 *
 * Convenção:
 * - O arquivo principal vem em storagePath.
 * - O layout vem em payloadJson (layoutPath).
 * - Competência vem de competenciaAno+competenciaMes.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SigtapImportJobWorker implements ImportJobWorker {

    private static final int ERROR_SAMPLE_LIMIT = 200;

    // USO EXCLUSIVO JOB - Worker usa pool JOB
    private final com.upsaude.repository.sistema.importacao.ImportJobJobRepository importJobJobRepository;
    private final com.upsaude.repository.sistema.importacao.ImportJobErrorRepository importJobErrorRepository;
    private final SupabaseStorageService supabaseStorageService;

    private final SigtapFileParser fileParser;
    private final SigtapEntityMapper entityMapper;
    private final JdbcEntityBatchWriter jdbcEntityBatchWriter;

    // USO EXCLUSIVO JOB - TransactionManager do JOB
    @Qualifier("jobTransactionManager")
    private final PlatformTransactionManager jobTransactionManager;

    // USO EXCLUSIVO JOB - EntityManager do JOB (persistence unit "job")
    @PersistenceContext(unitName = "job")
    private EntityManager entityManager;

    @Value("${import.job.batch-size.sigtap:${import.job.batch-size:1000}}")
    private int batchSize;

    @Value("${import.job.progress-log-interval-seconds:15}")
    private int progressLogIntervalSeconds;

    @Value("${import.job.tx.timeout-seconds.sigtap:90}")
    private int batchTxTimeoutSeconds;

    @Value("${sigtap.import.encoding:ISO-8859-1}")
    private String sigtapEncoding;

    @Override
    public ImportJobTipoEnum getTipo() {
        return ImportJobTipoEnum.SIGTAP;
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
            log.error("Job {} não encontrado para processamento SIGTAP", jobId);
            return;
        }

        if (job.getStatus() != ImportJobStatusEnum.PROCESSANDO) {
            log.warn("Job {} (SIGTAP) não está em PROCESSANDO. Status atual: {}. Ignorando processamento.", jobId, job.getStatus());
            return;
        }

        validarJob(job);

        UUID tenantId = job.getTenant() != null ? job.getTenant().getId() : null;
        String competencia = (job.getCompetenciaAno() != null && job.getCompetenciaMes() != null)
                ? job.getCompetenciaAno() + job.getCompetenciaMes()
                : null;

        String payloadJson = job.getPayloadJson();
        String layoutPath = extrairLayoutPath(payloadJson);
        if (!StringUtils.hasText(layoutPath)) {
            throw new ImportJobFatalException("SIGTAP requer layoutPath em payloadJson (arquivo *_layout.txt).");
        }

        Charset charset = Charset.forName(StringUtils.hasText(sigtapEncoding) ? sigtapEncoding : "ISO-8859-1");

        long checkpoint = job.getCheckpointLinha() != null ? job.getCheckpointLinha() : 0L;
        long lastCommittedLineIndex = checkpoint; // última linha efetivamente commitada no banco
        long linhasLidasTotal = job.getLinhasLidas() != null ? job.getLinhasLidas() : 0L;
        long linhasProcessadasTotal = job.getLinhasProcessadas() != null ? job.getLinhasProcessadas() : 0L;
        long linhasInseridasTotal = job.getLinhasInseridas() != null ? job.getLinhasInseridas() : 0L;
        long linhasErroTotal = job.getLinhasErro() != null ? job.getLinhasErro() : 0L;

        long inicio = System.currentTimeMillis();
        long lastProgressLogAtMs = System.currentTimeMillis();

        String nomeArquivo = nomeArquivo(job);
        ImportStrategy<?> strategy = strategyPorNomeArquivo(nomeArquivo, competencia);
        if (strategy == null) {
            throw new ImportJobFatalException("Arquivo SIGTAP não suportado: " + nomeArquivo);
        }

        try (InputStream layoutIs = supabaseStorageService.downloadStream(job.getStorageBucket(), layoutPath)) {
            SigtapLayoutDefinition layout = readLayoutFromStream(layoutIs);

            List<Object> batch = new ArrayList<>(Math.max(100, effectiveBatchSize));

            try (InputStream dataIs = supabaseStorageService.downloadStream(job.getStorageBucket(), job.getStoragePath());
                 BufferedReader reader = new BufferedReader(new InputStreamReader(dataIs, charset))) {

                // Skip checkpoint
                for (long i = 0; i < checkpoint; i++) {
                    if (reader.readLine() == null) break;
                }

                String line;
                long lineIndex = checkpoint;

                while ((line = reader.readLine()) != null) {
                    lineIndex++;
                    linhasLidasTotal++;

                    if (!StringUtils.hasText(line)) continue;

                    try {
                        Map<String, String> fields = fileParser.parseLine(line, layout);
                        if (!validarCamposBasicos(fields)) {
                            linhasErroTotal++;
                            salvarErroSeCouber(txBatchCommit, jobId, tenantId, lineIndex, "VALIDACAO", "Linha sem campos válidos", line);
                            continue;
                        }

                        Object entity = strategy.mapper.apply(fields);
                        if (entity == null) {
                            linhasErroTotal++;
                            salvarErroSeCouber(txBatchCommit, jobId, tenantId, lineIndex, "MAPPER", "Entidade nula após mapeamento", line);
                            continue;
                        }

                        batch.add(entity);
                        linhasProcessadasTotal++;

                        if (batch.size() >= effectiveBatchSize) {
                            long commitLineIndex = lineIndex;
                            int batchSizeAtual = batch.size();
                            persistirBatchEAtualizarCheckpoint(
                                    txBatchCommit, jobId, strategy, batch,
                                    linhasLidasTotal, linhasProcessadasTotal, linhasInseridasTotal + batchSizeAtual, linhasErroTotal,
                                    commitLineIndex
                            );
                            linhasInseridasTotal += batchSizeAtual;
                            lastCommittedLineIndex = commitLineIndex;
                            batch.clear();

                            long nowMs = System.currentTimeMillis();
                            if ((nowMs - lastProgressLogAtMs) >= progressLogIntervalMs) {
                                log.info("Job {} (SIGTAP:{}) progresso: lidas={}, processadas={}, inseridas={}, erros={}, checkpoint={}",
                                        jobId, nomeArquivo, linhasLidasTotal, linhasProcessadasTotal, linhasInseridasTotal, linhasErroTotal, lastCommittedLineIndex);
                                lastProgressLogAtMs = nowMs;
                            }
                        }
                    } catch (Exception e) {
                        linhasErroTotal++;
                        salvarErroSeCouber(txBatchCommit, jobId, tenantId, lineIndex, "EXCEPTION", e.getMessage(), line);
                    }
                }

                if (!batch.isEmpty()) {
                    long commitLineIndex = lineIndex;
                    int batchSizeAtual = batch.size();
                    persistirBatchEAtualizarCheckpoint(
                            txBatchCommit, jobId, strategy, batch,
                            linhasLidasTotal, linhasProcessadasTotal, linhasInseridasTotal + batchSizeAtual, linhasErroTotal,
                            commitLineIndex
                    );
                    linhasInseridasTotal += batchSizeAtual;
                    lastCommittedLineIndex = commitLineIndex;
                    batch.clear();
                }
            }

            long duracao = System.currentTimeMillis() - inicio;
            finalizar(txBatchCommit, jobId, linhasLidasTotal, linhasProcessadasTotal, linhasInseridasTotal, linhasErroTotal, lastCommittedLineIndex, duracao);
        } catch (Exception e) {
            log.error("Erro fatal no job {} (SIGTAP): {}", jobId, e.getMessage(), e);
            if (e instanceof ImportJobRecoverableException re) throw re;
            if (e instanceof ImportJobFatalException fe) throw fe;
            throw new ImportJobFatalException("Erro fatal no SIGTAP: " + e.getMessage(), e);
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
        if (StringUtils.hasText(job.getOriginalFilename())) return job.getOriginalFilename().toLowerCase();
        String path = job.getStoragePath();
        if (!StringUtils.hasText(path)) return "unknown";
        int idx = path.lastIndexOf('/');
        return (idx >= 0 ? path.substring(idx + 1) : path).toLowerCase();
    }

    private SigtapLayoutDefinition readLayoutFromStream(InputStream is) throws Exception {
        // LayoutReader atual só lê Path; aqui fazemos leitura equivalente por stream
        SigtapLayoutDefinition definition = new SigtapLayoutDefinition();
        definition.setCampos(new ArrayList<>());
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, java.nio.charset.StandardCharsets.UTF_8))) {
            String linha;
            boolean primeira = true;
            while ((linha = reader.readLine()) != null) {
                if (primeira) { primeira = false; continue; }
                String[] partes = linha.split(",", -1);
                if (partes.length >= 5) {
                    try {
                        com.upsaude.importacao.sigtap.file.SigtapLayoutField field =
                                new com.upsaude.importacao.sigtap.file.SigtapLayoutField();
                        field.setNome(partes[0].replace("\"", "").trim());
                        field.setTamanho(Integer.parseInt(partes[1].replace("\"", "").trim()));
                        field.setInicio(Integer.parseInt(partes[2].replace("\"", "").trim()));
                        field.setFim(Integer.parseInt(partes[3].replace("\"", "").trim()));
                        field.setTipo(partes[4].replace("\"", "").trim());
                        definition.getCampos().add(field);
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
        }
        return definition;
    }

    private boolean validarCamposBasicos(Map<String, String> fields) {
        if (fields == null || fields.isEmpty()) return false;
        return fields.values().stream().anyMatch(v -> v != null && !v.trim().isEmpty());
    }

    /**
     * Commit atômico por batch: persiste o batch + atualiza checkpoint/heartbeat no MESMO commit.
     * Isso garante que checkpoint nunca avance além do que foi efetivamente persistido.
     */
    private void persistirBatchEAtualizarCheckpoint(TransactionTemplate txBatchCommit,
                                                   UUID jobId,
                                                   ImportStrategy<?> strategy,
                                                   List<Object> batch,
                                                   long lidas,
                                                   long processadas,
                                                   long inseridas,
                                                   long erros,
                                                   long checkpointLinha) {
        txBatchCommit.executeWithoutResult(status -> {
            // UPSERT em JDBC baseado em @UniqueConstraint (evita overhead do Hibernate)
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
            return HexFormat.of().formatHex(md.digest(raw.getBytes(java.nio.charset.StandardCharsets.UTF_8)));
        } catch (Exception e) {
            return null;
        }
    }

    private String extrairLayoutPath(String payloadJson) {
        if (!StringUtils.hasText(payloadJson)) return null;
        try {
            // parsing simples sem depender de lib: procura "layoutPath":"..."
            String key = "\"layoutPath\"";
            int k = payloadJson.indexOf(key);
            if (k < 0) return null;
            int colon = payloadJson.indexOf(':', k);
            if (colon < 0) return null;
            int firstQuote = payloadJson.indexOf('"', colon + 1);
            if (firstQuote < 0) return null;
            int secondQuote = payloadJson.indexOf('"', firstQuote + 1);
            if (secondQuote < 0) return null;
            return payloadJson.substring(firstQuote + 1, secondQuote);
        } catch (Exception e) {
            return null;
        }
    }

    private ImportStrategy<?> strategyPorNomeArquivo(String nomeArquivo, String competencia) {
        if (!StringUtils.hasText(nomeArquivo)) return null;

        return switch (nomeArquivo) {
            case "tb_grupo.txt" -> new ImportStrategy<>(fields -> entityMapper.mapToGrupo(fields, competencia));
            case "tb_sub_grupo.txt" -> simpleMapper(entityMapper::mapToSubgrupo, competencia);
            case "tb_forma_organizacao.txt" -> simpleMapper(entityMapper::mapToFormaOrganizacao, competencia);
            case "tb_procedimento.txt" -> simpleMapper(entityMapper::mapToProcedimento, competencia);
            case "tb_financiamento.txt" -> simpleMapper(entityMapper::mapToFinanciamento, competencia);
            case "tb_rubrica.txt" -> simpleMapper(entityMapper::mapToRubrica, competencia);
            case "tb_modalidade.txt" -> simpleMapper(entityMapper::mapToModalidade, competencia);
            case "tb_registro.txt" -> simpleMapper(entityMapper::mapToRegistro, competencia);
            case "tb_tipo_leito.txt" -> new ImportStrategy<>(entityMapper::mapToTipoLeito);
            case "tb_servico.txt" -> new ImportStrategy<>(entityMapper::mapToServico);
            case "tb_servico_classificacao.txt" -> simpleMapper(entityMapper::mapToServicoClassificacao, competencia);
            case "tb_ocupacao.txt" -> new ImportStrategy<>(entityMapper::mapToOcupacao);
            case "tb_habilitacao.txt" -> simpleMapper(entityMapper::mapToHabilitacao, competencia);
            case "tb_grupo_habilitacao.txt" -> new ImportStrategy<>(entityMapper::mapToGrupoHabilitacao);
            case "tb_regra_condicionada.txt" -> new ImportStrategy<>(entityMapper::mapToRegraCondicionada);
            case "tb_renases.txt" -> new ImportStrategy<>(entityMapper::mapToRenases);
            case "tb_tuss.txt" -> new ImportStrategy<>(entityMapper::mapToTuss);
            case "tb_componente_rede.txt" -> new ImportStrategy<>(entityMapper::mapToComponenteRede);
            case "tb_rede_atencao.txt" -> new ImportStrategy<>(entityMapper::mapToRedeAtencao);
            case "tb_sia_sih.txt" -> new ImportStrategy<>(entityMapper::mapToSiaSih);
            case "tb_detalhe.txt" -> simpleMapper(entityMapper::mapToDetalhe, competencia);
            case "tb_descricao.txt" -> simpleMapper(entityMapper::mapToDescricao, competencia);
            case "tb_descricao_detalhe.txt" -> simpleMapper(entityMapper::mapToDescricaoDetalhe, competencia);
            case "rl_procedimento_cid.txt" -> simpleMapper(entityMapper::mapToProcedimentoCid, competencia);
            case "rl_procedimento_ocupacao.txt" -> simpleMapper(entityMapper::mapToProcedimentoOcupacao, competencia);
            case "rl_procedimento_habilitacao.txt" -> simpleMapper(entityMapper::mapToProcedimentoHabilitacao, competencia);
            case "rl_procedimento_leito.txt" -> simpleMapper(entityMapper::mapToProcedimentoLeito, competencia);
            case "rl_procedimento_servico.txt" -> simpleMapper(entityMapper::mapToProcedimentoServico, competencia);
            case "rl_procedimento_incremento.txt" -> simpleMapper(entityMapper::mapToProcedimentoIncremento, competencia);
            case "rl_procedimento_comp_rede.txt" -> new ImportStrategy<>(entityMapper::mapToProcedimentoComponenteRede);
            case "rl_procedimento_origem.txt" -> simpleMapper(entityMapper::mapToProcedimentoOrigem, competencia);
            case "rl_procedimento_sia_sih.txt" -> simpleMapper(entityMapper::mapToProcedimentoSiaSih, competencia);
            case "rl_procedimento_regra_cond.txt" -> new ImportStrategy<>(entityMapper::mapToProcedimentoRegraCondicionada);
            case "rl_procedimento_renases.txt" -> new ImportStrategy<>(entityMapper::mapToProcedimentoRenases);
            case "rl_procedimento_tuss.txt" -> new ImportStrategy<>(entityMapper::mapToProcedimentoTuss);
            case "rl_procedimento_modalidade.txt" -> simpleMapper(entityMapper::mapToProcedimentoModalidade, competencia);
            case "rl_procedimento_registro.txt" -> simpleMapper(entityMapper::mapToProcedimentoRegistro, competencia);
            case "rl_procedimento_detalhe.txt" -> new ImportStrategy<>(entityMapper::mapToProcedimentoDetalheItem);
            case "rl_excecao_compatibilidade.txt" -> simpleMapper(entityMapper::mapToExcecaoCompatibilidade, competencia);
            default -> null;
        };
    }

    private <T> ImportStrategy<T> simpleMapper(BiMapper<T> mapper, String competencia) {
        return new ImportStrategy<>(fields -> mapper.apply(fields, competencia));
    }

    @FunctionalInterface
    private interface BiMapper<T> {
        T apply(Map<String, String> fields, String competencia);
    }

    private static class ImportStrategy<T> {
        final Function<Map<String, String>, T> mapper;
        ImportStrategy(Function<Map<String, String>, T> mapper) {
            this.mapper = mapper;
        }
    }
}


