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
import java.util.HexFormat;
import java.util.List;
import java.util.UUID;

/**
 * Implementação do Upload Service: salva arquivo no Supabase Storage (streaming)
 * e cria job ENFILEIRADO no banco.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ImportJobUploadServiceImpl implements ImportJobUploadService {

    private static final String IMPORTS_BUCKET_DEFAULT = "imports";

    // USO EXCLUSIVO API - Upload é operação HTTP, usa pool API
    private final ImportJobApiRepository importJobApiRepository;
    private final SupabaseStorageService supabaseStorageService;

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

        // 1) Cria job inicialmente PAUSADO, com storage_path placeholder (colunas são NOT NULL no banco)
        ImportJob job = criarJobPlaceholder(file, tipo, competenciaAno, competenciaMes, uf, tenant, createdByUserId);

        // 2) Faz upload streaming para Supabase Storage usando path que inclui jobId
        String bucket = StringUtils.hasText(importsBucket) ? importsBucket : IMPORTS_BUCKET_DEFAULT;
        String objectPath = montarObjectPath(job, file.getOriginalFilename());

        try (InputStream raw = file.getInputStream()) {
            // Hash SHA-256 em streaming (não lê duas vezes)
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

            // 3) Atualiza job: storage info + checksum + ENFILEIRADO
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
    public ImportJob criarJobUploadComLayoutSigtap(MultipartFile fileDados,
                                                  MultipartFile fileLayout,
                                                  String competenciaAno,
                                                  String competenciaMes,
                                                  Tenant tenant,
                                                  UUID createdByUserId) {
        validarEntrada(fileDados, ImportJobTipoEnum.SIGTAP, tenant);
        validarLimiteJobsPendentes(tenant);
        validarDuplicata(fileDados, ImportJobTipoEnum.SIGTAP, competenciaAno, competenciaMes, null, tenant);
        if (fileLayout == null || fileLayout.isEmpty() || !StringUtils.hasText(fileLayout.getOriginalFilename())) {
            throw new BadRequestException("Arquivo de layout do SIGTAP é obrigatório");
        }

        // 1) Cria job placeholder
        ImportJob job = criarJobPlaceholder(fileDados, ImportJobTipoEnum.SIGTAP, competenciaAno, competenciaMes, null, tenant, createdByUserId);

        String bucket = StringUtils.hasText(importsBucket) ? importsBucket : IMPORTS_BUCKET_DEFAULT;
        String dataPath = montarObjectPath(job, fileDados.getOriginalFilename());
        String layoutPath = montarObjectPath(job, fileLayout.getOriginalFilename());

        try (InputStream raw = fileDados.getInputStream()) {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            try (DigestInputStream dis = new DigestInputStream(raw, digest)) {
                supabaseStorageService.uploadStream(bucket, dataPath, dis, fileDados.getSize(), fileDados.getContentType());
            }
            String checksum = HexFormat.of().formatHex(digest.digest());

            try (InputStream layoutIs = fileLayout.getInputStream()) {
                supabaseStorageService.uploadStream(bucket, layoutPath, layoutIs, fileLayout.getSize(), fileLayout.getContentType());
            }

            String payloadJson = "{\"layoutPath\":\"" + layoutPath.replace("\"", "") + "\"}";
            finalizarJobEnfileiradoComPayload(job.getId(), bucket, dataPath, fileDados.getSize(), fileDados.getContentType(), checksum, payloadJson);
            return importJobApiRepository.findById(job.getId()).orElseThrow();
        } catch (BadRequestException e) {
            marcarErro(job.getId(), "Erro de validação no upload: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            marcarErro(job.getId(), "Falha ao fazer upload no Storage: " + e.getMessage());
            throw new InternalServerErrorException("Erro ao salvar arquivos SIGTAP no Storage: " + e.getMessage(), e);
        }
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

    /**
     * Valida se já existe um job duplicado com as mesmas características.
     * Bloqueia upload se encontrar um job com status ENFILEIRADO, PROCESSANDO ou CONCLUIDO
     * com mesmo nome, tamanho, tipo, competência, UF e tenant.
     */
    @Transactional(readOnly = true)
    private void validarDuplicata(MultipartFile file,
                                 ImportJobTipoEnum tipo,
                                 String competenciaAno,
                                 String competenciaMes,
                                 String uf,
                                 Tenant tenant) {
        if (tenant == null || tenant.getId() == null) {
            return; // Validação de tenant já feita em validarEntrada
        }

        String originalFilename = file.getOriginalFilename();
        Long sizeBytes = file.getSize();
        String ufNormalizado = StringUtils.hasText(uf) ? uf.toUpperCase() : null;

        // Busca jobs duplicados com status que indicam que o arquivo já foi ou está sendo processado
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
            ImportJob duplicado = duplicados.get(0); // Pega o mais recente
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

        // placeholder obrigatório
        job.setStorageBucket(StringUtils.hasText(importsBucket) ? importsBucket : IMPORTS_BUCKET_DEFAULT);
        job.setStoragePath("pending");

        job.setStatus(ImportJobStatusEnum.PAUSADO); // enquanto faz upload
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
        // remove caminhos enviados por browsers antigos e normaliza
        int lastSlash = Math.max(name.lastIndexOf('/'), name.lastIndexOf('\\'));
        if (lastSlash >= 0) {
            name = name.substring(lastSlash + 1);
        }
        // mantém apenas caracteres seguros para path
        name = name.replaceAll("[^a-zA-Z0-9._-]", "_");
        if (name.length() > 200) {
            name = name.substring(0, 200);
        }
        return name;
    }
}


