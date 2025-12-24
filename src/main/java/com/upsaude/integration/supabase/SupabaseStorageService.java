package com.upsaude.integration.supabase;

import com.upsaude.config.SupabaseConfig;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ImportJobFatalException;
import com.upsaude.exception.ImportJobRecoverableException;
import com.upsaude.exception.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.FilterInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SupabaseStorageService {

    private final SupabaseConfig supabaseConfig;
    private final RestTemplate restTemplate;
    private final CloseableHttpClient supabaseHttpClient;

    private static final String BUCKET_NAME = "usuarios-fotos";
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final String[] ALLOWED_CONTENT_TYPES = {
            "image/jpeg", "image/jpg", "image/png", "image/webp", "image/gif"
    };

    /**
     * Garante que o bucket existe no Supabase Storage. Se não existir, cria automaticamente.
     * 
     * @param bucket Nome do bucket
     */
    public void ensureBucketExists(String bucket) {
        if (!StringUtils.hasText(bucket)) {
            throw new BadRequestException("Bucket é obrigatório");
        }

        String baseUrl = normalizarBaseUrl(supabaseConfig.getUrl());
        String bucketUrl = baseUrl + "/storage/v1/bucket/" + bucket;

        try {
            // Verifica se o bucket existe
            HttpHeaders headers = new HttpHeaders();
            headers.set("apikey", supabaseConfig.getServiceRoleKey());
            headers.set("Authorization", "Bearer " + supabaseConfig.getServiceRoleKey());

            HttpEntity<Void> request = new HttpEntity<>(headers);
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    bucketUrl,
                    HttpMethod.GET,
                    request,
                    new org.springframework.core.ParameterizedTypeReference<Map<String, Object>>() {}
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                log.debug("Bucket '{}' já existe no Supabase Storage", bucket);
                return;
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                // Bucket não existe, vamos criá-lo
                log.info("Bucket '{}' não encontrado. Criando automaticamente...", bucket);
            } else {
                log.warn("Erro ao verificar bucket '{}': {}", bucket, e.getStatusCode());
                // Continua tentando criar mesmo assim
            }
        } catch (Exception e) {
            log.warn("Erro ao verificar bucket '{}': {}", bucket, e.getMessage());
            // Continua tentando criar mesmo assim
        }

        // Cria o bucket
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("apikey", supabaseConfig.getServiceRoleKey());
            headers.set("Authorization", "Bearer " + supabaseConfig.getServiceRoleKey());

            Map<String, Object> bucketConfig = new HashMap<>();
            bucketConfig.put("name", bucket);
            bucketConfig.put("public", false); // Bucket privado para arquivos de importação
            bucketConfig.put("file_size_limit", null); // Sem limite de tamanho
            bucketConfig.put("allowed_mime_types", null); // Aceita qualquer tipo de arquivo

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(bucketConfig, headers);
            String createBucketUrl = baseUrl + "/storage/v1/bucket";

            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    createBucketUrl,
                    HttpMethod.POST,
                    request,
                    new org.springframework.core.ParameterizedTypeReference<Map<String, Object>>() {}
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Bucket '{}' criado com sucesso no Supabase Storage", bucket);
            } else {
                log.warn("Falha ao criar bucket '{}': {}", bucket, response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.CONFLICT) {
                // Bucket já existe (race condition)
                log.debug("Bucket '{}' já existe (criado por outro processo)", bucket);
            } else {
                log.error("Erro ao criar bucket '{}': Status={}, Body={}", bucket, e.getStatusCode(), e.getResponseBodyAsString());
                throw new InternalServerErrorException("Falha ao criar bucket no Supabase Storage: " + e.getMessage(), e);
            }
        } catch (Exception e) {
            log.error("Erro inesperado ao criar bucket '{}'", bucket, e);
            throw new InternalServerErrorException("Erro inesperado ao criar bucket no Supabase Storage: " + e.getMessage(), e);
        }
    }

    /**
     * Upload streaming genérico para Supabase Storage (não carrega o arquivo inteiro em memória).
     * Endpoint: POST {supabaseUrl}/storage/v1/object/{bucket}/{objectPath}
     *
     * @param bucket Nome do bucket
     * @param objectPath Caminho do objeto dentro do bucket (sem bucket no prefixo)
     * @param inputStream stream do arquivo (será consumido)
     * @param sizeBytes tamanho do arquivo em bytes (pode ser null se desconhecido)
     * @param contentType content-type (fallback: application/octet-stream)
     */
    public void uploadStream(String bucket, String objectPath, InputStream inputStream, Long sizeBytes, String contentType) {
        if (!StringUtils.hasText(bucket)) {
            throw new BadRequestException("Bucket é obrigatório");
        }
        if (!StringUtils.hasText(objectPath)) {
            throw new BadRequestException("Caminho do objeto é obrigatório");
        }
        if (inputStream == null) {
            throw new BadRequestException("InputStream é obrigatório");
        }

        // Garante que o bucket existe antes de fazer upload
        ensureBucketExists(bucket);

        String baseUrl = normalizarBaseUrl(supabaseConfig.getUrl());
        String filePath = bucket + "/" + objectPath;
        String uploadUrl = baseUrl + "/storage/v1/object/" + filePath;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(StringUtils.hasText(contentType) ? contentType : MediaType.APPLICATION_OCTET_STREAM_VALUE));
            headers.set("apikey", supabaseConfig.getServiceRoleKey());
            headers.set("Authorization", "Bearer " + supabaseConfig.getServiceRoleKey());
            if (sizeBytes != null && sizeBytes >= 0) {
                headers.setContentLength(sizeBytes);
            }

            // Usa RestTemplate.execute para fazer streaming do InputStream para o body da requisição
            restTemplate.execute(
                    URI.create(uploadUrl),
                    HttpMethod.POST,
                    request -> {
                        request.getHeaders().putAll(headers);
                        StreamUtils.copy(inputStream, request.getBody());
                    },
                    response -> {
                        // Consumir response para liberar conexão
                        if (response.getBody() != null) {
                            StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);
                        }
                        if (!response.getStatusCode().is2xxSuccessful()) {
                            HttpStatusCode sc = response.getStatusCode();
                            int code = sc.value();
                            if (code == 404) {
                                // Tenta criar o bucket novamente e retry (pode ter sido criado por outro processo)
                                try {
                                    ensureBucketExists(bucket);
                                    throw new ImportJobRecoverableException("Bucket não encontrado, tentando criar novamente. bucket=" + bucket);
                                } catch (Exception ex) {
                                    throw new ImportJobFatalException("Arquivo/bucket não encontrado no Supabase Storage (404). bucket=" + bucket + ", path=" + objectPath);
                                }
                            }
                            if (code == 429 || (code >= 500 && code <= 599)) {
                                throw new ImportJobRecoverableException("Erro temporário no Supabase Storage (upload). Status=" + code);
                            }
                            throw new ImportJobFatalException("Erro no Supabase Storage (upload). Status=" + code);
                        }
                        return null;
                    }
            );
        } catch (HttpClientErrorException e) {
            log.error("Erro HTTP ao fazer upload streaming - Status: {}, Body: {}", e.getStatusCode(), e.getResponseBodyAsString());
            int code = e.getStatusCode().value();
            if (code == 404) {
                // Tenta criar o bucket novamente e retry (pode ter sido criado por outro processo)
                try {
                    ensureBucketExists(bucket);
                    throw new ImportJobRecoverableException("Bucket não encontrado, tentando criar novamente. bucket=" + bucket, e);
                } catch (Exception ex) {
                    throw new ImportJobFatalException("Arquivo/bucket não encontrado no Supabase Storage (404). bucket=" + bucket + ", path=" + objectPath, e);
                }
            }
            if (code == 429 || (code >= 500 && code <= 599)) {
                throw new ImportJobRecoverableException("Erro temporário no Supabase Storage (upload). Status=" + code, e);
            }
            throw new ImportJobFatalException("Erro no Supabase Storage (upload). Status=" + code + " - " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Erro inesperado ao fazer upload streaming para Supabase Storage", e);
            if (e instanceof ImportJobRecoverableException re) throw re;
            if (e instanceof ImportJobFatalException fe) throw fe;
            if (e instanceof IOException) {
                throw new ImportJobRecoverableException("Falha de rede ao fazer upload no Supabase Storage: " + e.getMessage(), e);
            }
            throw new ImportJobFatalException("Erro inesperado ao fazer upload no Supabase Storage: " + e.getMessage(), e);
        }
    }

    /**
     * Download streaming de arquivo do Supabase Storage.
     * Endpoint: GET {supabaseUrl}/storage/v1/object/{bucket}/{objectPath}
     */
    public InputStream downloadStream(String bucket, String objectPath) {
        if (!StringUtils.hasText(bucket)) {
            throw new BadRequestException("Bucket é obrigatório");
        }
        if (!StringUtils.hasText(objectPath)) {
            throw new BadRequestException("Caminho do objeto é obrigatório");
        }

        String baseUrl = normalizarBaseUrl(supabaseConfig.getUrl());
        String filePath = bucket + "/" + objectPath;
        String downloadUrl = baseUrl + "/storage/v1/object/" + filePath;

        try {
            HttpGet get = new HttpGet(downloadUrl);
            get.addHeader("apikey", supabaseConfig.getServiceRoleKey());
            get.addHeader("Authorization", "Bearer " + supabaseConfig.getServiceRoleKey());

            CloseableHttpResponse response = supabaseHttpClient.execute(get);
            int code = response.getCode();
            if (code >= 200 && code < 300) {
                org.apache.hc.core5.http.HttpEntity entity = response.getEntity();
                if (entity == null) {
                    response.close();
                    throw new InternalServerErrorException("Erro ao baixar arquivo do Supabase Storage: resposta sem body");
                }
                InputStream raw = entity.getContent();
                // Fecha a resposta junto com o stream (evita vazamento de conexões)
                return new FilterInputStream(raw) {
                    @Override
                    public void close() throws IOException {
                        try {
                            super.close();
                        } finally {
                            response.close();
                        }
                    }
                };
            }

            String body = null;
            try {
                body = response.getEntity() != null ? EntityUtils.toString(response.getEntity()) : null;
            } finally {
                response.close();
            }

            if (code == 404) {
                throw new ImportJobFatalException("Arquivo não encontrado no Supabase Storage (404). bucket=" + bucket + ", path=" + objectPath);
            }
            if (code == 429 || (code >= 500 && code <= 599)) {
                throw new ImportJobRecoverableException("Erro temporário no Supabase Storage (download). Status=" + code + (body != null ? (". Body: " + body) : ""));
            }
            throw new ImportJobFatalException("Erro ao baixar arquivo do Supabase Storage. Status=" + code + (body != null ? (". Body: " + body) : ""));
        } catch (BadRequestException e) {
            throw e;
        } catch (ImportJobRecoverableException | ImportJobFatalException e) {
            throw e;
        } catch (IOException e) {
            log.error("Erro de IO ao baixar arquivo do Supabase Storage", e);
            throw new ImportJobRecoverableException("Falha de rede ao baixar arquivo do Supabase Storage: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Erro inesperado ao baixar arquivo do Supabase Storage", e);
            throw new ImportJobFatalException("Erro inesperado ao baixar arquivo do Supabase Storage: " + e.getMessage(), e);
        }
    }

    public String uploadFotoUsuario(MultipartFile file, UUID userId) {
        log.debug("Iniciando upload de foto para usuário: {}", userId);

        validarArquivo(file);

        String extension = getFileExtension(file.getOriginalFilename());
        String fileName = userId.toString() + "_" + UUID.randomUUID() + "." + extension;
        String filePath = BUCKET_NAME + "/" + fileName;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(file.getContentType()));
            headers.set("apikey", supabaseConfig.getServiceRoleKey());
            headers.set("Authorization", "Bearer " + supabaseConfig.getServiceRoleKey());

            byte[] fileBytes = file.getBytes();
            HttpEntity<byte[]> request = new HttpEntity<>(fileBytes, headers);

            String baseUrl = supabaseConfig.getUrl();
            if (baseUrl.endsWith("/")) {
                baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
            }
            String uploadUrl = baseUrl + "/storage/v1/object/" + filePath;

            log.debug("Fazendo upload para: {}", uploadUrl);

            ResponseEntity<String> response = restTemplate.exchange(
                    uploadUrl,
                    HttpMethod.POST,
                    request,
                    String.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
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

    public void deletarFotoUsuario(String fotoUrl) {
        if (!StringUtils.hasText(fotoUrl)) {
            return;
        }

        log.debug("Deletando foto: {}", fotoUrl);

        try {
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

    private boolean isAllowedContentType(String contentType) {
        for (String allowed : ALLOWED_CONTENT_TYPES) {
            if (allowed.equalsIgnoreCase(contentType)) {
                return true;
            }
        }
        return false;
    }

    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "jpg";
        }
        return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    }

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

    private String normalizarBaseUrl(String baseUrl) {
        if (!StringUtils.hasText(baseUrl)) {
            throw new BadRequestException("Supabase URL não configurada");
        }
        return baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
    }
}

