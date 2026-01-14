package com.upsaude.service.impl.api.anexo;

import com.upsaude.api.request.anexo.AnexoUpdateRequest;
import com.upsaude.api.response.anexo.AnexoDownloadUrlResponse;
import com.upsaude.api.response.anexo.AnexoGestaoResponse;
import com.upsaude.api.response.anexo.AnexoResponse;
import com.upsaude.entity.sistema.usuario.UsuariosSistema;
import com.upsaude.repository.sistema.usuario.UsuariosSistemaRepository;
import com.upsaude.entity.anexo.Anexo;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.enums.CategoriaAnexoEnum;
import com.upsaude.enums.StatusAnexoEnum;
import com.upsaude.enums.TargetTypeAnexoEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ForbiddenException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.integration.supabase.SupabaseStorageService;
import com.upsaude.repository.anexo.AnexoRepository;
import com.upsaude.service.api.anexo.AnexoService;
import com.upsaude.service.api.anexo.AnexoTargetPermissionValidator;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnexoServiceImpl implements AnexoService {

    private static final String BUCKET_NAME = "anexos";
    private static final long MAX_FILE_SIZE = 50 * 1024 * 1024; // 50MB
    private static final int DEFAULT_URL_EXPIRES_IN_SECONDS = 300; // 5 minutos

    private final AnexoRepository anexoRepository;
    private final SupabaseStorageService supabaseStorageService;
    private final TenantService tenantService;
    private final AnexoTargetPermissionValidator permissionValidator;
    private final UsuariosSistemaRepository usuariosSistemaRepository;

    @Override
    @Transactional
    public AnexoResponse upload(MultipartFile file, TargetTypeAnexoEnum targetType, UUID targetId,
                                 CategoriaAnexoEnum categoria, Boolean visivelParaPaciente,
                                 String descricao, String tags) {
        log.debug("Iniciando upload de anexo - targetType: {}, targetId: {}, fileName: {}", 
                  targetType, targetId, file != null ? file.getOriginalFilename() : null);

        // Validações básicas
        validarArquivo(file);
        
        // Validar tenant
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null || tenant.getId() == null) {
            throw new ForbiddenException("Tenant não encontrado ou não autenticado");
        }

        // Validar permissão de acesso ao target
        permissionValidator.validarAcesso(targetType, targetId, tenant.getId());

        // Obter userId autenticado
        UUID userId = obterUserIdAutenticado();

        // Garantir que file não é null após validação
        if (file == null || file.isEmpty()) {
            throw new BadRequestException("Arquivo não pode ser vazio");
        }

        try {
            // Criar registro de anexo com status PENDENTE
            Anexo anexo = new Anexo();
            anexo.setTenant(tenant);
            anexo.setTargetType(targetType);
            anexo.setTargetId(targetId);
            anexo.setStorageBucket(BUCKET_NAME);
            anexo.setFileNameOriginal(file.getOriginalFilename() != null ? file.getOriginalFilename() : "arquivo");
            anexo.setMimeType(file.getContentType() != null ? file.getContentType() : "application/octet-stream");
            anexo.setSizeBytes(file.getSize());
            anexo.setCategoria(categoria != null ? categoria : CategoriaAnexoEnum.OUTROS);
            anexo.setVisivelParaPaciente(visivelParaPaciente != null ? visivelParaPaciente : false);
            anexo.setStatus(StatusAnexoEnum.PENDENTE);
            anexo.setDescricao(descricao);
            anexo.setTags(tags);
            anexo.setCriadoPor(userId);
            anexo.setActive(true);

            // Gerar caminho único no storage
            String objectPath = gerarObjectPath(tenant.getId(), targetType, targetId, anexo.getId(), 
                                                 sanitizeFileName(file.getOriginalFilename()));
            anexo.setStorageObjectPath(objectPath);

            // Salvar anexo no banco (status PENDENTE) para obter o ID
            anexo = anexoRepository.save(anexo);

            // Atualizar objectPath com o ID real do anexo
            objectPath = gerarObjectPath(tenant.getId(), targetType, targetId, anexo.getId(), 
                                         sanitizeFileName(file.getOriginalFilename()));
            anexo.setStorageObjectPath(objectPath);
            anexo = anexoRepository.save(anexo);

            // Calcular checksum e fazer upload no Supabase Storage
            try (InputStream inputStream = file.getInputStream();
                 BufferedInputStream bufferedStream = new BufferedInputStream(inputStream)) {
                
                // Marcar posição para poder resetar depois
                bufferedStream.mark((int) file.getSize() + 1);
                
                // Calcular checksum antes do upload
                try {
                    String checksum = calcularChecksum(bufferedStream);
                    anexo.setChecksum(checksum);
                    // Resetar stream para fazer upload
                    bufferedStream.reset();
                } catch (Exception e) {
                    log.warn("Erro ao calcular checksum do arquivo, continuando sem checksum", e);
                    // Se não conseguir resetar, criar novo stream
                    bufferedStream.reset();
                }
                
                // Fazer upload no Supabase Storage
                supabaseStorageService.uploadStream(BUCKET_NAME, objectPath, bufferedStream, 
                                                   file.getSize(), file.getContentType());
            }

            // Atualizar status para ATIVO
            anexo.setStatus(StatusAnexoEnum.ATIVO);
            anexo = anexoRepository.save(anexo);

            log.info("Anexo criado com sucesso - id: {}, targetType: {}, targetId: {}", 
                     anexo.getId(), targetType, targetId);

            return toResponse(anexo);

        } catch (Exception e) {
            log.error("Erro ao fazer upload de anexo", e);
            throw new BadRequestException("Erro ao fazer upload de anexo: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnexoResponse> listar(TargetTypeAnexoEnum targetType, UUID targetId,
                                       CategoriaAnexoEnum categoria, StatusAnexoEnum status,
                                       Boolean visivelParaPaciente, Pageable pageable) {
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null || tenant.getId() == null) {
            throw new ForbiddenException("Tenant não encontrado ou não autenticado");
        }

        // Validar permissão de acesso ao target
        if (targetType != null && targetId != null) {
            permissionValidator.validarAcesso(targetType, targetId, tenant.getId());
        }

        Page<Anexo> anexos;
        StatusAnexoEnum excluido = StatusAnexoEnum.EXCLUIDO;

        if (targetType != null && targetId != null) {
            if (categoria != null) {
                anexos = anexoRepository.findByTargetAndCategoria(tenant.getId(), targetType, targetId, 
                                                                   categoria, excluido, pageable);
            } else if (status != null) {
                anexos = anexoRepository.findByTargetAndStatus(tenant.getId(), targetType, targetId, 
                                                                status, pageable);
            } else if (visivelParaPaciente != null && visivelParaPaciente) {
                anexos = anexoRepository.findByTargetVisivelParaPaciente(tenant.getId(), targetType, 
                                                                          targetId, excluido, pageable);
            } else {
                anexos = anexoRepository.findByTarget(tenant.getId(), targetType, targetId, excluido, pageable);
            }
        } else if (status != null) {
            anexos = anexoRepository.findByTenantAndStatus(tenant.getId(), status, pageable);
        } else {
            throw new BadRequestException("targetType e targetId são obrigatórios para listar anexos");
        }

        return anexos.map(this::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public AnexoResponse obterPorId(UUID id) {
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null || tenant.getId() == null) {
            throw new ForbiddenException("Tenant não encontrado ou não autenticado");
        }

        Anexo anexo = anexoRepository.findByIdAndTenant(tenant.getId(), id)
                .orElseThrow(() -> new NotFoundException("Anexo não encontrado: " + id));

        // Validar permissão de acesso ao target
        permissionValidator.validarAcesso(anexo.getTargetType(), anexo.getTargetId(), tenant.getId());

        return toResponse(anexo);
    }

    @Override
    @Transactional(readOnly = true)
    public InputStream downloadStream(UUID id) {
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null || tenant.getId() == null) {
            throw new ForbiddenException("Tenant não encontrado ou não autenticado");
        }

        Anexo anexo = anexoRepository.findByIdAndTenant(tenant.getId(), id)
                .orElseThrow(() -> new NotFoundException("Anexo não encontrado: " + id));

        // Validar permissão de acesso ao target
        permissionValidator.validarAcesso(anexo.getTargetType(), anexo.getTargetId(), tenant.getId());

        if (anexo.getStatus() == StatusAnexoEnum.EXCLUIDO) {
            throw new BadRequestException("Não é possível fazer download de anexo excluído");
        }

        return supabaseStorageService.downloadStream(anexo.getStorageBucket(), anexo.getStorageObjectPath());
    }

    @Override
    @Transactional(readOnly = true)
    public AnexoDownloadUrlResponse gerarUrlDownload(UUID id, int expiresInSeconds) {
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null || tenant.getId() == null) {
            throw new ForbiddenException("Tenant não encontrado ou não autenticado");
        }

        Anexo anexo = anexoRepository.findByIdAndTenant(tenant.getId(), id)
                .orElseThrow(() -> new NotFoundException("Anexo não encontrado: " + id));

        // Validar permissão de acesso ao target
        permissionValidator.validarAcesso(anexo.getTargetType(), anexo.getTargetId(), tenant.getId());

        if (anexo.getStatus() == StatusAnexoEnum.EXCLUIDO) {
            throw new BadRequestException("Não é possível gerar URL de download para anexo excluído");
        }

        if (expiresInSeconds <= 0) {
            expiresInSeconds = DEFAULT_URL_EXPIRES_IN_SECONDS;
        }

        String signedUrl = supabaseStorageService.generateSignedUrl(
                anexo.getStorageBucket(), 
                anexo.getStorageObjectPath(), 
                expiresInSeconds
        );

        OffsetDateTime expiresAt = OffsetDateTime.now().plusSeconds(expiresInSeconds);

        return AnexoDownloadUrlResponse.builder()
                .signedUrl(signedUrl)
                .expiresAt(expiresAt)
                .fileName(anexo.getFileNameOriginal())
                .mimeType(anexo.getMimeType())
                .sizeBytes(anexo.getSizeBytes())
                .build();
    }

    @Override
    @Transactional
    public AnexoResponse atualizar(UUID id, AnexoUpdateRequest request) {
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null || tenant.getId() == null) {
            throw new ForbiddenException("Tenant não encontrado ou não autenticado");
        }

        Anexo anexo = anexoRepository.findByIdAndTenant(tenant.getId(), id)
                .orElseThrow(() -> new NotFoundException("Anexo não encontrado: " + id));

        // Validar permissão de acesso ao target
        permissionValidator.validarAcesso(anexo.getTargetType(), anexo.getTargetId(), tenant.getId());

        if (request.getCategoria() != null) {
            anexo.setCategoria(request.getCategoria());
        }
        if (request.getVisivelParaPaciente() != null) {
            anexo.setVisivelParaPaciente(request.getVisivelParaPaciente());
        }
        if (request.getStatus() != null) {
            anexo.setStatus(request.getStatus());
        }
        if (request.getDescricao() != null) {
            anexo.setDescricao(request.getDescricao());
        }
        if (request.getTags() != null) {
            anexo.setTags(request.getTags());
        }

        anexo = anexoRepository.save(anexo);

        log.info("Anexo atualizado - id: {}", id);
        return toResponse(anexo);
    }

    @Override
    @Transactional
    public void excluir(UUID id, boolean deleteFromStorage) {
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null || tenant.getId() == null) {
            throw new ForbiddenException("Tenant não encontrado ou não autenticado");
        }

        Anexo anexo = anexoRepository.findByIdAndTenant(tenant.getId(), id)
                .orElseThrow(() -> new NotFoundException("Anexo não encontrado: " + id));

        // Validar permissão de acesso ao target
        permissionValidator.validarAcesso(anexo.getTargetType(), anexo.getTargetId(), tenant.getId());

        UUID userId = obterUserIdAutenticado();

        // Soft delete
        anexo.setStatus(StatusAnexoEnum.EXCLUIDO);
        anexo.setExcluidoPor(userId);
        anexo.setActive(false);
        anexoRepository.save(anexo);

        // Hard delete do storage (se solicitado)
        if (deleteFromStorage) {
            try {
                supabaseStorageService.deleteObject(anexo.getStorageBucket(), anexo.getStorageObjectPath());
                log.info("Arquivo deletado do storage - bucket: {}, path: {}", 
                         anexo.getStorageBucket(), anexo.getStorageObjectPath());
            } catch (Exception e) {
                log.error("Erro ao deletar arquivo do storage, continuando com soft delete", e);
            }
        }

        log.info("Anexo excluído - id: {}, deleteFromStorage: {}", id, deleteFromStorage);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnexoGestaoResponse> listarGestao(TargetTypeAnexoEnum targetType, UUID targetId,
                                                   CategoriaAnexoEnum categoria, StatusAnexoEnum status,
                                                   Boolean visivelParaPaciente, String fileName,
                                                   UUID criadoPor, OffsetDateTime dataInicio, OffsetDateTime dataFim,
                                                   Pageable pageable) {
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null || tenant.getId() == null) {
            throw new ForbiddenException("Tenant não encontrado ou não autenticado");
        }

        Page<Anexo> anexos = anexoRepository.findByGestao(
                tenant.getId(), targetType, targetId, categoria, status,
                visivelParaPaciente, fileName, criadoPor, dataInicio, dataFim, pageable
        );

        return anexos.map(this::toGestaoResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public String gerarThumbnailUrl(UUID id, int width, int height) {
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null || tenant.getId() == null) {
            throw new ForbiddenException("Tenant não encontrado ou não autenticado");
        }

        Anexo anexo = anexoRepository.findByIdAndTenant(tenant.getId(), id)
                .orElseThrow(() -> new NotFoundException("Anexo não encontrado: " + id));

        // Validar permissão de acesso ao target
        permissionValidator.validarAcesso(anexo.getTargetType(), anexo.getTargetId(), tenant.getId());

        if (anexo.getStatus() == StatusAnexoEnum.EXCLUIDO) {
            throw new BadRequestException("Não é possível gerar thumbnail de anexo excluído");
        }

        // Verificar se é imagem
        if (!isImagem(anexo.getMimeType())) {
            throw new BadRequestException("Thumbnail só está disponível para arquivos de imagem");
        }

        // Valores padrão para thumbnail
        if (width <= 0) width = 200;
        if (height <= 0) height = 200;

        // Gerar URL assinada com transformação de imagem (Supabase Storage suporta transformações)
        // Por enquanto, retornamos URL assinada normal. Pode ser melhorado depois com transformações do Supabase
        return supabaseStorageService.generateSignedUrl(
                anexo.getStorageBucket(),
                anexo.getStorageObjectPath(),
                300 // 5 minutos para thumbnail
        );
    }

    private void validarArquivo(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BadRequestException("Arquivo não pode ser vazio");
        }
        if (file.getOriginalFilename() == null) {
            throw new BadRequestException("Nome do arquivo é obrigatório");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new BadRequestException("Arquivo muito grande. Tamanho máximo: " + (MAX_FILE_SIZE / 1024 / 1024) + "MB");
        }

        String contentType = file.getContentType();
        if (contentType == null) {
            throw new BadRequestException("Tipo de arquivo não identificado");
        }
    }

    private String gerarObjectPath(UUID tenantId, TargetTypeAnexoEnum targetType, UUID targetId, 
                                   UUID anexoId, String fileName) {
        return String.format("tenant/%s/%s/%s/%s/%s", 
                            tenantId, targetType.getCodigo(), targetId, anexoId, fileName);
    }

    private String sanitizeFileName(String fileName) {
        if (!StringUtils.hasText(fileName)) {
            return UUID.randomUUID().toString();
        }
        // Remove caracteres perigosos e mantém apenas alfanuméricos, pontos, hífens e underscores
        String sanitized = fileName.replaceAll("[^a-zA-Z0-9._-]", "_");
        // Limita tamanho
        if (sanitized.length() > 200) {
            String ext = "";
            int lastDot = sanitized.lastIndexOf('.');
            if (lastDot > 0) {
                ext = sanitized.substring(lastDot);
                sanitized = sanitized.substring(0, 200 - ext.length()) + ext;
            } else {
                sanitized = sanitized.substring(0, 200);
            }
        }
        return sanitized;
    }

    private String calcularChecksum(InputStream inputStream) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] buffer = new byte[8192];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            digest.update(buffer, 0, bytesRead);
        }
        byte[] hash = digest.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private UUID obterUserIdAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        Object details = authentication.getDetails();
        if (details instanceof com.upsaude.integration.supabase.SupabaseAuthResponse.User) {
            return ((com.upsaude.integration.supabase.SupabaseAuthResponse.User) details).getId();
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof String) {
            try {
                return UUID.fromString((String) principal);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }

    private AnexoResponse toResponse(Anexo anexo) {
        return AnexoResponse.builder()
                .id(anexo.getId())
                .createdAt(anexo.getCreatedAt())
                .updatedAt(anexo.getUpdatedAt())
                .targetType(anexo.getTargetType())
                .targetId(anexo.getTargetId())
                .fileNameOriginal(anexo.getFileNameOriginal())
                .mimeType(anexo.getMimeType())
                .sizeBytes(anexo.getSizeBytes())
                .categoria(anexo.getCategoria())
                .visivelParaPaciente(anexo.getVisivelParaPaciente())
                .status(anexo.getStatus())
                .descricao(anexo.getDescricao())
                .tags(anexo.getTags())
                .criadoPor(anexo.getCriadoPor())
                .build();
    }

    private AnexoGestaoResponse toGestaoResponse(Anexo anexo) {
        String criadoPorNome = null;
        String excluidoPorNome = null;

        if (anexo.getCriadoPor() != null) {
            Optional<UsuariosSistema> usuario = usuariosSistemaRepository.findByUserId(anexo.getCriadoPor());
            if (usuario.isPresent() && usuario.get().getDadosExibicao() != null) {
                criadoPorNome = usuario.get().getDadosExibicao().getNomeExibicao();
                if (criadoPorNome == null && usuario.get().getDadosIdentificacao() != null) {
                    criadoPorNome = usuario.get().getDadosIdentificacao().getUsername();
                }
            }
        }

        if (anexo.getExcluidoPor() != null) {
            Optional<UsuariosSistema> usuario = usuariosSistemaRepository.findByUserId(anexo.getExcluidoPor());
            if (usuario.isPresent() && usuario.get().getDadosExibicao() != null) {
                excluidoPorNome = usuario.get().getDadosExibicao().getNomeExibicao();
                if (excluidoPorNome == null && usuario.get().getDadosIdentificacao() != null) {
                    excluidoPorNome = usuario.get().getDadosIdentificacao().getUsername();
                }
            }
        }

        String extensao = "";
        if (anexo.getFileNameOriginal() != null && anexo.getFileNameOriginal().contains(".")) {
            extensao = anexo.getFileNameOriginal().substring(anexo.getFileNameOriginal().lastIndexOf(".") + 1).toLowerCase();
        }

        boolean isImagem = isImagem(anexo.getMimeType());
        String thumbnailUrl = null;
        if (isImagem) {
            try {
                thumbnailUrl = gerarThumbnailUrlInterno(anexo, 200, 200);
            } catch (Exception e) {
                log.warn("Erro ao gerar thumbnail para anexo {}", anexo.getId(), e);
            }
        }

        return AnexoGestaoResponse.builder()
                .id(anexo.getId())
                .createdAt(anexo.getCreatedAt())
                .updatedAt(anexo.getUpdatedAt())
                .targetType(anexo.getTargetType())
                .targetId(anexo.getTargetId())
                .fileNameOriginal(anexo.getFileNameOriginal())
                .mimeType(anexo.getMimeType())
                .sizeBytes(anexo.getSizeBytes())
                .sizeFormatted(formatFileSize(anexo.getSizeBytes()))
                .categoria(anexo.getCategoria())
                .visivelParaPaciente(anexo.getVisivelParaPaciente())
                .status(anexo.getStatus())
                .descricao(anexo.getDescricao())
                .tags(anexo.getTags())
                .criadoPor(anexo.getCriadoPor())
                .criadoPorNome(criadoPorNome)
                .excluidoPor(anexo.getExcluidoPor())
                .excluidoPorNome(excluidoPorNome)
                .thumbnailUrl(thumbnailUrl)
                .isImagem(isImagem)
                .extensao(extensao)
                .build();
    }

    private boolean isImagem(String mimeType) {
        if (mimeType == null) return false;
        return mimeType.startsWith("image/");
    }

    private String formatFileSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return String.format("%.2f KB", bytes / 1024.0);
        if (bytes < 1024 * 1024 * 1024) return String.format("%.2f MB", bytes / (1024.0 * 1024.0));
        return String.format("%.2f GB", bytes / (1024.0 * 1024.0 * 1024.0));
    }

    private String gerarThumbnailUrlInterno(Anexo anexo, int width, int height) {
        // Valores padrão para thumbnail
        if (width <= 0) width = 200;
        if (height <= 0) height = 200;

        // Gerar URL assinada com transformação de imagem (Supabase Storage suporta transformações)
        // Por enquanto, retornamos URL assinada normal. Pode ser melhorado depois com transformações do Supabase
        return supabaseStorageService.generateSignedUrl(
                anexo.getStorageBucket(),
                anexo.getStorageObjectPath(),
                300 // 5 minutos para thumbnail
        );
    }
}
