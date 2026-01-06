package com.upsaude.service.impl.job;

import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.entity.sistema.importacao.ImportJob;
import com.upsaude.enums.ImportJobStatusEnum;
import com.upsaude.enums.ImportJobTipoEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.integration.supabase.SupabaseStorageService;
import com.upsaude.repository.sistema.importacao.ImportJobApiRepository;
import com.upsaude.service.job.ImportJobUploadService;
import com.upsaude.service.job.SigtapJobOrchestrator;
import com.upsaude.service.job.SigtapZipExtractionService;
import com.upsaude.service.job.Cid10ZipExtractionService;
import com.upsaude.service.job.Cid10JobOrchestrator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImportJobUploadServiceImpl implements ImportJobUploadService {

    private static final String IMPORTS_BUCKET_DEFAULT = "imports";

    private final ImportJobApiRepository importJobApiRepository;
    private final SupabaseStorageService supabaseStorageService;
    private final SigtapZipExtractionService sigtapZipExtractionService;
    private final SigtapJobOrchestrator sigtapJobOrchestrator;
    private final Cid10ZipExtractionService cid10ZipExtractionService;
    private final Cid10JobOrchestrator cid10JobOrchestrator;

    @Value("${import.job.storage.bucket:imports}")
    private String importsBucket;

    @Value("${import.job.upload.max-pending-per-tenant:5}")
    private int maxPendingJobsPerTenant;

    @Value("${import.job.priority.sia-pa:100}")
    private int prioritySiaPa;

    @Value("${import.job.priority.sigtap:50}")
    private int prioritySigtap;

    @Value("${import.job.priority.cid10:10}")
    private int priorityCid10;

    @Value("${sigtap.import.zip.max-size-bytes:1073741824}")
    private long maxZipSizeBytes;

    @Value("${sigtap.import.zip.extracted-path-prefix:extracted}")
    private String extractedPathPrefix;

    @Value("${cid10.import.zip.max-size-bytes:1073741824}")
    private long maxCid10ZipSizeBytes;

    @Value("${cid10.import.zip.extracted-path-prefix:extracted}")
    private String cid10ExtractedPathPrefix;

    @Override
    public ImportJob criarJobUpload(MultipartFile file,
                                   ImportJobTipoEnum tipo,
                                   String competenciaAno,
                                   String competenciaMes,
                                   String uf,
                                   Tenant tenant,
                                   UUID createdByUserId) {

        validarEntrada(file, tipo, tenant);
        validarLimiteJobsPendentes(tenant);
        validarDuplicata(file, tipo, competenciaAno, competenciaMes, uf, tenant);

        ImportJob job = criarJobPlaceholder(file, tipo, competenciaAno, competenciaMes, uf, tenant, createdByUserId);

        String bucket = StringUtils.hasText(importsBucket) ? importsBucket : IMPORTS_BUCKET_DEFAULT;
        String objectPath = montarObjectPath(job, file.getOriginalFilename());

        try (InputStream raw = file.getInputStream()) {

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            try (DigestInputStream dis = new DigestInputStream(raw, digest)) {
                supabaseStorageService.uploadStream(
                        bucket,
                        objectPath,
                        dis,
                        file.getSize(),
                        file.getContentType()
                );
            }

            String checksum = HexFormat.of().formatHex(digest.digest());

            finalizarJobEnfileirado(job.getId(), bucket, objectPath, file.getSize(), file.getContentType(), checksum);

            return importJobApiRepository.findById(job.getId()).orElseThrow();
        } catch (BadRequestException e) {
            marcarErro(job.getId(), "Erro de validação no upload: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            marcarErro(job.getId(), "Falha ao fazer upload no Storage: " + e.getMessage());
            throw new InternalServerErrorException("Erro ao salvar arquivo no Storage: " + e.getMessage(), e);
        }
    }

    @Override
    public ImportJobUploadService.CriarJobsZipResultado criarJobsFromZipSigtap(
            MultipartFile zipFile,
            String competenciaAno,
            String competenciaMes,
            Tenant tenant,
            UUID createdByUserId) {

        if (tenant == null || tenant.getId() == null) {
            throw new BadRequestException("Tenant é obrigatório");
        }
        if (zipFile == null || zipFile.isEmpty()) {
            throw new BadRequestException("Arquivo ZIP é obrigatório e não pode ser vazio");
        }
        if (zipFile.getSize() > maxZipSizeBytes) {
            throw new BadRequestException("Arquivo ZIP excede o tamanho máximo permitido: " + maxZipSizeBytes + " bytes");
        }
        if (!StringUtils.hasText(competenciaAno) || !StringUtils.hasText(competenciaMes)) {
            throw new BadRequestException("Competência (ano e mês) é obrigatória");
        }

        validarLimiteJobsPendentes(tenant);

        List<ImportJob> jobsCriados = new ArrayList<>();
        List<String> erros = new ArrayList<>();

        try {

            log.info("Iniciando extração do ZIP SIGTAP para competência {}/{}", competenciaAno, competenciaMes);
            SigtapZipExtractionService.ExtrairResultado resultadoExtracao;
            try (InputStream zipInputStream = zipFile.getInputStream()) {
                resultadoExtracao = sigtapZipExtractionService.extrairZip(zipInputStream);
            }

            if (resultadoExtracao.getArquivos().isEmpty()) {
                throw new BadRequestException("ZIP não contém arquivos válidos");
            }

            sigtapZipExtractionService.validarEstruturaZip(resultadoExtracao.getArquivos());

            List<SigtapZipExtractionService.ArquivoPar> pares = sigtapZipExtractionService.identificarPares(resultadoExtracao.getArquivos());
            if (pares.isEmpty()) {
                throw new BadRequestException("Nenhum par de arquivos (dados + layout) foi identificado no ZIP");
            }

            List<SigtapJobOrchestrator.ArquivoComPrioridade> arquivosOrdenados = sigtapJobOrchestrator.ordenarArquivos(pares);

            String bucket = StringUtils.hasText(importsBucket) ? importsBucket : IMPORTS_BUCKET_DEFAULT;
            String tenantId = tenant.getId().toString();

            for (SigtapJobOrchestrator.ArquivoComPrioridade acp : arquivosOrdenados) {
                SigtapZipExtractionService.ArquivoPar par = acp.getPar();
                String nomeArquivoDados = par.getArquivoDados().getNome();
                int prioridade = acp.getPrioridade();

                try {

                    String dataPath = montarObjectPathExtracted(tenantId, competenciaAno, competenciaMes, nomeArquivoDados);
                    String layoutPath = montarObjectPathExtracted(tenantId, competenciaAno, competenciaMes, par.getArquivoLayout().getNome());

                    String checksum;
                    try (InputStream dataIs = par.getArquivoDados().getConteudoAsInputStream()) {
                        MessageDigest digest = MessageDigest.getInstance("SHA-256");
                        try (DigestInputStream dis = new DigestInputStream(dataIs, digest)) {
                            supabaseStorageService.uploadStream(bucket, dataPath, dis, par.getArquivoDados().getTamanho(), "text/plain");
                        }
                        checksum = HexFormat.of().formatHex(digest.digest());
                    }

                    try (InputStream layoutIs = par.getArquivoLayout().getConteudoAsInputStream()) {
                        supabaseStorageService.uploadStream(bucket, layoutPath, layoutIs, par.getArquivoLayout().getTamanho(), "text/plain");
                    }

                    String payloadJson = "{\"layoutPath\":\"" + layoutPath.replace("\"", "\\\"") + "\"}";

                    ImportJob job = criarJobCompletoZipPausado(
                            nomeArquivoDados,
                            par.getArquivoDados().getTamanho(),
                            ImportJobTipoEnum.SIGTAP,
                            competenciaAno,
                            competenciaMes,
                            tenant,
                            createdByUserId,
                            prioridade,
                            bucket,
                            dataPath,
                            checksum,
                            payloadJson
                    );

                    jobsCriados.add(job);
                    log.info("Job criado (pausado) com sucesso: {} - {} (prioridade: {})", job.getId(), nomeArquivoDados, prioridade);
                } catch (Exception e) {
                    String erro = String.format("Erro ao processar arquivo %s: %s", nomeArquivoDados, e.getMessage());
                    log.error(erro, e);
                    erros.add(erro);
                }
            }

            if (!jobsCriados.isEmpty()) {
                log.info("Enfileirando {} jobs após conclusão do upload ZIP", jobsCriados.size());
                enfileirarJobsEmLote(jobsCriados);
            }

            log.info("Processamento do ZIP concluído. Jobs criados: {}, Erros: {}", jobsCriados.size(), erros.size());
            return new ImportJobUploadService.CriarJobsZipResultado(jobsCriados, arquivosOrdenados.size(), erros);

        } catch (BadRequestException e) {
            log.error("Erro de validação no upload ZIP: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Erro ao processar ZIP SIGTAP: {}", e.getMessage(), e);
            throw new InternalServerErrorException("Erro ao processar arquivo ZIP SIGTAP: " + e.getMessage(), e);
        }
    }

    @Override
    public ImportJobUploadService.CriarJobsZipResultado criarJobsFromZipCid10(
            MultipartFile zipFile,
            String competenciaAno,
            String competenciaMes,
            Tenant tenant,
            UUID createdByUserId) {

        if (zipFile == null || zipFile.isEmpty()) {
            throw new BadRequestException("Arquivo ZIP é obrigatório e não pode ser vazio");
        }
        if (zipFile.getSize() > maxCid10ZipSizeBytes) {
            throw new BadRequestException("Arquivo ZIP excede o tamanho máximo permitido: " + maxCid10ZipSizeBytes + " bytes");
        }
        if (!StringUtils.hasText(competenciaAno) || !StringUtils.hasText(competenciaMes)) {
            throw new BadRequestException("Competência (ano e mês) é obrigatória");
        }

        validarLimiteJobsPendentes(tenant);

        List<ImportJob> jobsCriados = new ArrayList<>();
        List<String> erros = new ArrayList<>();

        try {

            log.info("Iniciando extração do ZIP CID-10 para competência {}/{}", competenciaAno, competenciaMes);
            Cid10ZipExtractionService.ExtrairResultado resultadoExtracao;
            try (InputStream zipInputStream = zipFile.getInputStream()) {
                resultadoExtracao = cid10ZipExtractionService.extrairZip(zipInputStream);
            }

            if (resultadoExtracao.getArquivos().isEmpty()) {
                throw new BadRequestException("ZIP não contém arquivos CSV válidos");
            }

            cid10ZipExtractionService.validarEstruturaZip(resultadoExtracao.getArquivos());

            List<Cid10JobOrchestrator.ArquivoComPrioridade> arquivosOrdenados = cid10JobOrchestrator.ordenarArquivos(resultadoExtracao.getArquivos());

            if (arquivosOrdenados.isEmpty()) {
                throw new BadRequestException("Nenhum arquivo válido foi identificado no ZIP");
            }

            String bucket = StringUtils.hasText(importsBucket) ? importsBucket : IMPORTS_BUCKET_DEFAULT;
            String tenantId = tenant.getId().toString();

            for (Cid10JobOrchestrator.ArquivoComPrioridade acp : arquivosOrdenados) {
                Cid10ZipExtractionService.ArquivoExtraido arquivo = acp.getArquivo();
                String nomeArquivo = arquivo.getNome();
                int prioridade = acp.getPrioridade();

                try {

                    String storagePath = montarObjectPathExtractedCid10(tenantId, competenciaAno, competenciaMes, nomeArquivo);

                    String checksum;
                    try (InputStream arquivoIs = arquivo.getConteudoAsInputStream()) {
                        MessageDigest digest = MessageDigest.getInstance("SHA-256");
                        try (DigestInputStream dis = new DigestInputStream(arquivoIs, digest)) {
                            supabaseStorageService.uploadStream(bucket, storagePath, dis, arquivo.getTamanho(), "text/csv");
                        }
                        checksum = HexFormat.of().formatHex(digest.digest());
                    }

                    ImportJob job = criarJobCompletoCid10Pausado(
                            nomeArquivo,
                            arquivo.getTamanho(),
                            ImportJobTipoEnum.CID10,
                            competenciaAno,
                            competenciaMes,
                            tenant,
                            createdByUserId,
                            prioridade,
                            bucket,
                            storagePath,
                            checksum
                    );

                    jobsCriados.add(job);
                    log.info("Job criado (pausado) com sucesso: {} - {} (prioridade: {})", job.getId(), nomeArquivo, prioridade);
                } catch (Exception e) {
                    String erro = String.format("Erro ao processar arquivo %s: %s", nomeArquivo, e.getMessage());
                    log.error(erro, e);
                    erros.add(erro);
                }
            }

            if (!jobsCriados.isEmpty()) {
                log.info("Enfileirando {} jobs após conclusão do upload ZIP CID-10", jobsCriados.size());
                enfileirarJobsEmLote(jobsCriados);
            }

            log.info("Processamento do ZIP CID-10 concluído. Jobs criados: {}, Erros: {}", jobsCriados.size(), erros.size());
            return new ImportJobUploadService.CriarJobsZipResultado(jobsCriados, arquivosOrdenados.size(), erros);

        } catch (BadRequestException e) {
            log.error("Erro de validação no upload ZIP CID-10: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Erro ao processar ZIP CID-10: {}", e.getMessage(), e);
            throw new InternalServerErrorException("Erro ao processar arquivo ZIP CID-10: " + e.getMessage(), e);
        }
    }

    @Transactional
    protected ImportJob criarJobCompletoZipPausado(String nomeArquivo,
                                                  Long tamanho,
                                                  ImportJobTipoEnum tipo,
                                                  String competenciaAno,
                                                  String competenciaMes,
                                                  Tenant tenant,
                                                  UUID createdByUserId,
                                                  int prioridade,
                                                  String storageBucket,
                                                  String storagePath,
                                                  String checksum,
                                                  String payloadJson) {
        ImportJob job = new ImportJob();
        job.setTenant(tenant);
        job.setTipo(tipo);
        job.setCompetenciaAno(competenciaAno);
        job.setCompetenciaMes(competenciaMes);
        job.setUf(null); 
        job.setOriginalFilename(nomeArquivo);
        job.setSizeBytes(tamanho);
        job.setContentType("text/plain");
        job.setStatus(ImportJobStatusEnum.PAUSADO); 
        job.setPriority(prioridade);
        job.setCreatedByUserId(createdByUserId);
        job.setCreatedAt(OffsetDateTime.now());
        job.setUpdatedAt(OffsetDateTime.now());

        job.setStorageBucket(storageBucket);
        job.setStoragePath(storagePath);
        job.setChecksum(checksum);
        job.setPayloadJson(payloadJson);

        return importJobApiRepository.save(job);
    }

    @Transactional
    protected ImportJob criarJobCompletoCid10Pausado(String nomeArquivo,
                                                     Long tamanho,
                                                     ImportJobTipoEnum tipo,
                                                     String competenciaAno,
                                                     String competenciaMes,
                                                     Tenant tenant,
                                                     UUID createdByUserId,
                                                     int prioridade,
                                                     String storageBucket,
                                                     String storagePath,
                                                     String checksum) {
        ImportJob job = new ImportJob();
        job.setTenant(tenant);
        job.setTipo(tipo);
        job.setCompetenciaAno(competenciaAno);
        job.setCompetenciaMes(competenciaMes);
        job.setUf(null); 
        job.setOriginalFilename(nomeArquivo);
        job.setSizeBytes(tamanho);
        job.setContentType("text/csv");
        job.setStatus(ImportJobStatusEnum.PAUSADO); 
        job.setPriority(prioridade);
        job.setCreatedByUserId(createdByUserId);
        job.setCreatedAt(OffsetDateTime.now());
        job.setUpdatedAt(OffsetDateTime.now());

        job.setStorageBucket(storageBucket);
        job.setStoragePath(storagePath);
        job.setChecksum(checksum);
        job.setPayloadJson(null); 

        return importJobApiRepository.save(job);
    }

    @Transactional
    protected void enfileirarJobsEmLote(List<ImportJob> jobs) {
        if (jobs == null || jobs.isEmpty()) {
            return;
        }

        OffsetDateTime now = OffsetDateTime.now();

        OffsetDateTime nextRunAt = now.plusSeconds(10);

        for (ImportJob job : jobs) {
            if (job.getStatus() == ImportJobStatusEnum.PAUSADO) {
                job.setStatus(ImportJobStatusEnum.ENFILEIRADO);
                job.setNextRunAt(nextRunAt);
                job.setErrorSummary(null);
                importJobApiRepository.save(job);
            }
        }
        log.info("{} jobs enfileirados com sucesso. Próxima execução agendada para: {} (scheduler rápido roda a cada 2 minutos)", 
                jobs.size(), nextRunAt);
    }

    private String montarObjectPathExtracted(String tenantId, String ano, String mes, String nomeArquivo) {
        String safeName = sanitizarNomeArquivo(nomeArquivo);
        String prefix = StringUtils.hasText(extractedPathPrefix) ? extractedPathPrefix : "extracted";

        return "tenant/" + tenantId +
                "/tipo/sigtap" +
                "/competencia/" + ano + "/" + mes +
                "/" + prefix +
                "/" + safeName;
    }

    private String montarObjectPathExtractedCid10(String tenantId, String ano, String mes, String nomeArquivo) {
        String safeName = sanitizarNomeArquivo(nomeArquivo);
        String prefix = StringUtils.hasText(cid10ExtractedPathPrefix) ? cid10ExtractedPathPrefix : "extracted";

        return "tenant/" + tenantId +
                "/tipo/cid10" +
                "/competencia/" + ano + "/" + mes +
                "/" + prefix +
                "/" + safeName;
    }

    private void validarEntrada(MultipartFile file, ImportJobTipoEnum tipo, Tenant tenant) {
        if (tenant == null || tenant.getId() == null) {
            throw new BadRequestException("Tenant é obrigatório");
        }
        if (tipo == null) {
            throw new BadRequestException("Tipo de importação é obrigatório");
        }
        if (file == null || file.isEmpty()) {
            throw new BadRequestException("Arquivo é obrigatório e não pode ser vazio");
        }
        if (!StringUtils.hasText(file.getOriginalFilename())) {
            throw new BadRequestException("Nome do arquivo é obrigatório");
        }
    }

    private void validarLimiteJobsPendentes(Tenant tenant) {
        if (tenant == null || tenant.getId() == null) return;
        int limit = Math.max(1, maxPendingJobsPerTenant);
        long count = importJobApiRepository.countByTenant_IdAndStatusIn(
                tenant.getId(),
                java.util.List.of(ImportJobStatusEnum.ENFILEIRADO, ImportJobStatusEnum.PROCESSANDO, ImportJobStatusEnum.PAUSADO)
        );
        if (count >= limit) {
            throw new BadRequestException("Limite de importações simultâneas excedido. Existem " + count + " jobs pendentes/ativos (limite=" + limit + ").");
        }
    }

    @Transactional(readOnly = true)
    private void validarDuplicata(MultipartFile file,
                                 ImportJobTipoEnum tipo,
                                 String competenciaAno,
                                 String competenciaMes,
                                 String uf,
                                 Tenant tenant) {
        if (tenant == null || tenant.getId() == null) {
            return; 
        }

        String originalFilename = file.getOriginalFilename();
        Long sizeBytes = file.getSize();
        String ufNormalizado = StringUtils.hasText(uf) ? uf.toUpperCase() : null;

        List<ImportJob> duplicados = importJobApiRepository.findDuplicados(
                tenant.getId(),
                tipo,
                originalFilename,
                sizeBytes,
                competenciaAno,
                competenciaMes,
                ufNormalizado,
                java.util.List.of(
                        ImportJobStatusEnum.ENFILEIRADO,
                        ImportJobStatusEnum.PROCESSANDO,
                        ImportJobStatusEnum.CONCLUIDO,
                        ImportJobStatusEnum.PAUSADO
                )
        );

        if (!duplicados.isEmpty()) {
            ImportJob duplicado = duplicados.get(0); 
            String mensagem = String.format(
                    "Arquivo duplicado detectado. Já existe um job com as mesmas características: " +
                    "ID=%s, Status=%s, Criado em=%s. " +
                    "Nome: %s, Tamanho: %d bytes, Tipo: %s, Competência: %s/%s, UF: %s",
                    duplicado.getId(),
                    duplicado.getStatus(),
                    duplicado.getCreatedAt(),
                    originalFilename,
                    sizeBytes,
                    tipo,
                    competenciaAno,
                    competenciaMes,
                    ufNormalizado != null ? ufNormalizado : "N/A"
            );
            log.warn("Upload bloqueado - {}", mensagem);
            throw new BadRequestException(mensagem);
        }
    }

    @Transactional
    protected ImportJob criarJobPlaceholder(MultipartFile file,
                                           ImportJobTipoEnum tipo,
                                           String competenciaAno,
                                           String competenciaMes,
                                           String uf,
                                           Tenant tenant,
                                           UUID createdByUserId) {

        ImportJob job = new ImportJob();
        job.setTenant(tenant);
        job.setTipo(tipo);
        job.setCompetenciaAno(competenciaAno);
        job.setCompetenciaMes(competenciaMes);
        job.setUf(StringUtils.hasText(uf) ? uf.toUpperCase() : null);

        job.setOriginalFilename(file.getOriginalFilename());
        job.setContentType(file.getContentType());
        job.setSizeBytes(file.getSize());

        job.setStorageBucket(StringUtils.hasText(importsBucket) ? importsBucket : IMPORTS_BUCKET_DEFAULT);
        job.setStoragePath("pending");

        job.setStatus(ImportJobStatusEnum.PAUSADO); 
        job.setPriority(resolverPrioridade(tipo));
        job.setAttempts(0);
        job.setMaxAttempts(3);
        job.setCreatedByUserId(createdByUserId);

        ImportJob saved = importJobApiRepository.save(job);
        log.info("Job criado (placeholder) - id={}, tipo={}, tenantId={}, filename={}",
                saved.getId(), saved.getTipo(), tenant.getId(), saved.getOriginalFilename());
        return saved;
    }

    @Transactional
    protected void finalizarJobEnfileirado(UUID jobId,
                                           String bucket,
                                           String objectPath,
                                           long sizeBytes,
                                           String contentType,
                                           String checksum) {
        ImportJob job = importJobApiRepository.findById(jobId).orElseThrow();
        job.setStorageBucket(bucket);
        job.setStoragePath(objectPath);
        job.setSizeBytes(sizeBytes);
        job.setContentType(contentType);
        job.setChecksum(checksum);
        job.setPayloadJson(null);

        job.setStatus(ImportJobStatusEnum.ENFILEIRADO);
        job.setNextRunAt(OffsetDateTime.now());
        job.setErrorSummary(null);
        importJobApiRepository.save(job);
        log.info("Job enfileirado - id={}, bucket={}, path={}", jobId, bucket, objectPath);
    }

    @Transactional
    protected void finalizarJobEnfileiradoComPayload(UUID jobId,
                                                    String bucket,
                                                    String objectPath,
                                                    long sizeBytes,
                                                    String contentType,
                                                    String checksum,
                                                    String payloadJson) {
        ImportJob job = importJobApiRepository.findById(jobId).orElseThrow();
        job.setStorageBucket(bucket);
        job.setStoragePath(objectPath);
        job.setSizeBytes(sizeBytes);
        job.setContentType(contentType);
        job.setChecksum(checksum);
        job.setPayloadJson(payloadJson);

        job.setStatus(ImportJobStatusEnum.ENFILEIRADO);
        job.setNextRunAt(OffsetDateTime.now());
        job.setErrorSummary(null);
        importJobApiRepository.save(job);
        log.info("Job enfileirado (payload) - id={}, bucket={}, path={}", jobId, bucket, objectPath);
    }

    @Transactional
    protected void marcarErro(UUID jobId, String resumo) {
        ImportJob job = importJobApiRepository.findById(jobId).orElse(null);
        if (job == null) return;
        job.setStatus(ImportJobStatusEnum.ERRO);
        job.setErrorSummary(resumo);
        job.setFinishedAt(OffsetDateTime.now());
        importJobApiRepository.save(job);
        log.warn("Job marcado como ERRO - id={}, motivo={}", jobId, resumo);
    }

    private int resolverPrioridade(ImportJobTipoEnum tipo) {
        return switch (tipo) {
            case SIA_PA -> prioritySiaPa;
            case SIGTAP -> prioritySigtap;
            case CID10 -> priorityCid10;
        };
    }

    private String montarObjectPath(ImportJob job, String originalFilename) {
        String safeName = sanitizarNomeArquivo(originalFilename);
        String tipo = job.getTipo() != null ? job.getTipo().name().toLowerCase() : "desconhecido";
        String ano = StringUtils.hasText(job.getCompetenciaAno()) ? job.getCompetenciaAno() : "sem-ano";
        String mes = StringUtils.hasText(job.getCompetenciaMes()) ? job.getCompetenciaMes() : "sem-mes";
        String uf = StringUtils.hasText(job.getUf()) ? job.getUf() : "sem-uf";
        String tenantId = job.getTenant() != null && job.getTenant().getId() != null ? job.getTenant().getId().toString() : "sem-tenant";

        return "tenant/" + tenantId +
                "/tipo/" + tipo +
                "/competencia/" + ano + "/" + mes +
                "/uf/" + uf +
                "/job/" + job.getId() +
                "/" + safeName;
    }

    private String sanitizarNomeArquivo(String filename) {
        String name = filename.trim();

        int lastSlash = Math.max(name.lastIndexOf('/'), name.lastIndexOf('\\'));
        if (lastSlash >= 0) {
            name = name.substring(lastSlash + 1);
        }
        name = name.replaceAll("[^a-zA-Z0-9._-]", "_");
        if (name.length() > 200) {
            name = name.substring(0, 200);
        }
        return name;
    }
}
