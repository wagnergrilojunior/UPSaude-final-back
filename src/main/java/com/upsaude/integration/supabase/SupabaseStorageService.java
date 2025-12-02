package com.upsaude.integration.supabase;

import com.upsaude.config.SupabaseConfig;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * Serviço para interagir com o Supabase Storage.
 * Permite upload e download de arquivos (fotos de usuários).
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SupabaseStorageService {

    private final SupabaseConfig supabaseConfig;
    private final RestTemplate restTemplate;

    private static final String BUCKET_NAME = "usuarios-fotos";
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final String[] ALLOWED_CONTENT_TYPES = {
            "image/jpeg", "image/jpg", "image/png", "image/webp", "image/gif"
    };

    /**
     * Faz upload de uma foto de usuário para o Supabase Storage.
     *
     * @param file Arquivo de imagem
     * @param userId ID do usuário
     * @return URL pública da imagem no Supabase Storage
     */
    public String uploadFotoUsuario(MultipartFile file, UUID userId) {
        log.debug("Iniciando upload de foto para usuário: {}", userId);

        // Validar arquivo
        validarArquivo(file);

        // Gerar nome único para o arquivo
        String extension = getFileExtension(file.getOriginalFilename());
        String fileName = userId.toString() + "_" + UUID.randomUUID() + "." + extension;
        String filePath = BUCKET_NAME + "/" + fileName;

        try {
            // Preparar headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(file.getContentType()));
            headers.set("apikey", supabaseConfig.getServiceRoleKey());
            headers.set("Authorization", "Bearer " + supabaseConfig.getServiceRoleKey());

            // Preparar body (bytes do arquivo)
            byte[] fileBytes = file.getBytes();
            HttpEntity<byte[]> request = new HttpEntity<>(fileBytes, headers);

            // URL do Supabase Storage
            String baseUrl = supabaseConfig.getUrl();
            if (baseUrl.endsWith("/")) {
                baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
            }
            String uploadUrl = baseUrl + "/storage/v1/object/" + filePath;

            log.debug("Fazendo upload para: {}", uploadUrl);

            // Fazer upload
            ResponseEntity<String> response = restTemplate.exchange(
                    uploadUrl,
                    HttpMethod.POST,
                    request,
                    String.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                // Construir URL pública da imagem
                String publicUrl = baseUrl + "/storage/v1/object/public/" + filePath;
                log.info("Foto enviada com sucesso para usuário {}. URL: {}", userId, publicUrl);
                return publicUrl;
            } else {
                log.error("Erro ao fazer upload. Status: {}", response.getStatusCode());
                throw new InternalServerErrorException("Erro ao fazer upload da foto");
            }

        } catch (HttpClientErrorException e) {
            log.error("Erro HTTP ao fazer upload - Status: {}, Body: {}",
                    e.getStatusCode(), e.getResponseBodyAsString());
            throw new InternalServerErrorException("Erro ao fazer upload da foto: " + e.getMessage());
        } catch (IOException e) {
            log.error("Erro ao ler arquivo", e);
            throw new InternalServerErrorException("Erro ao processar arquivo: " + e.getMessage());
        } catch (Exception e) {
            log.error("Erro inesperado ao fazer upload", e);
            throw new InternalServerErrorException("Erro inesperado ao fazer upload: " + e.getMessage());
        }
    }

    /**
     * Deleta uma foto de usuário do Supabase Storage.
     *
     * @param fotoUrl URL da foto a ser deletada
     */
    public void deletarFotoUsuario(String fotoUrl) {
        if (!StringUtils.hasText(fotoUrl)) {
            return;
        }

        log.debug("Deletando foto: {}", fotoUrl);

        try {
            // Extrair caminho do arquivo da URL
            String filePath = extrairCaminhoArquivo(fotoUrl);
            if (filePath == null) {
                log.warn("Não foi possível extrair caminho do arquivo da URL: {}", fotoUrl);
                return;
            }

            // Preparar headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("apikey", supabaseConfig.getServiceRoleKey());
            headers.set("Authorization", "Bearer " + supabaseConfig.getServiceRoleKey());

            HttpEntity<Void> request = new HttpEntity<>(headers);

            // URL do Supabase Storage
            String baseUrl = supabaseConfig.getUrl();
            if (baseUrl.endsWith("/")) {
                baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
            }
            String deleteUrl = baseUrl + "/storage/v1/object/" + filePath;

            log.debug("Deletando arquivo em: {}", deleteUrl);

            // Deletar arquivo
            restTemplate.exchange(
                    deleteUrl,
                    HttpMethod.DELETE,
                    request,
                    Void.class
            );

            log.info("Foto deletada com sucesso: {}", fotoUrl);

        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.warn("Arquivo não encontrado para deletar: {}", fotoUrl);
            } else {
                log.error("Erro HTTP ao deletar foto - Status: {}, Body: {}",
                        e.getStatusCode(), e.getResponseBodyAsString());
            }
        } catch (Exception e) {
            log.error("Erro ao deletar foto: {}", fotoUrl, e);
            // Não lançar exceção para não interromper o fluxo
        }
    }

    /**
     * Valida o arquivo antes do upload.
     */
    private void validarArquivo(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BadRequestException("Arquivo não pode ser vazio");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new BadRequestException("Arquivo muito grande. Tamanho máximo: 5MB");
        }

        String contentType = file.getContentType();
        if (contentType == null || !isAllowedContentType(contentType)) {
            throw new BadRequestException("Tipo de arquivo não permitido. Use: JPEG, PNG, WEBP ou GIF");
        }
    }

    /**
     * Verifica se o tipo de conteúdo é permitido.
     */
    private boolean isAllowedContentType(String contentType) {
        for (String allowed : ALLOWED_CONTENT_TYPES) {
            if (allowed.equalsIgnoreCase(contentType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtém a extensão do arquivo.
     */
    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "jpg";
        }
        return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    }

    /**
     * Extrai o caminho do arquivo da URL pública.
     */
    private String extrairCaminhoArquivo(String fotoUrl) {
        if (!StringUtils.hasText(fotoUrl)) {
            return null;
        }

        try {
            // URL format: https://xxx.supabase.co/storage/v1/object/public/bucket/file.jpg
            String baseUrl = supabaseConfig.getUrl();
            if (baseUrl.endsWith("/")) {
                baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
            }

            String prefix = baseUrl + "/storage/v1/object/public/";
            if (fotoUrl.startsWith(prefix)) {
                return fotoUrl.substring(prefix.length());
            }

            return null;
        } catch (Exception e) {
            log.warn("Erro ao extrair caminho do arquivo", e);
            return null;
        }
    }
}

