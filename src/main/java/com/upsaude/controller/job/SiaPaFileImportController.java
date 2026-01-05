package com.upsaude.controller.job;

import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.enums.ImportJobTipoEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.job.ImportJobUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

@RestController
@RequestMapping("/v1/sia")
@Tag(name = "SIA-SUS Importação", description = "Endpoints para importação de arquivos CSV do SIA-SUS (Sistema de Informações Ambulatoriais do SUS)")
@Slf4j
public class SiaPaFileImportController {

    private final ImportJobUploadService importJobUploadService;
    private final TenantService tenantService;
    
    @Value("${sia.import.base-path:data_import/sia}")
    private String basePath;

    public SiaPaFileImportController(
            ImportJobUploadService importJobUploadService,
            TenantService tenantService) {
        this.importJobUploadService = importJobUploadService;
        this.tenantService = tenantService;
    }

    @PostMapping(value = "/import/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Upload de arquivo SIA-SUS PA (assíncrono)",
            description = "Recebe um arquivo CSV do SIA-SUS e cria um job para processamento em background. O progresso pode ser acompanhado em /v1/import-jobs/{jobId}/status.",
            responses = {
                    @ApiResponse(responseCode = "202", description = "Upload aceito e job criado"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado"),
                    @ApiResponse(responseCode = "500", description = "Erro ao criar job")
            }
    )
    public ResponseEntity<Map<String, Object>> uploadSiaPa(
            @Parameter(description = "Arquivo CSV do SIA-SUS PA", required = true)
            @RequestParam("file") MultipartFile file,
            @Parameter(description = "Ano da competência (YYYY)", required = true)
            @RequestParam("competenciaAno") String competenciaAno,
            @Parameter(description = "Mês da competência (MM)", required = true)
            @RequestParam("competenciaMes") String competenciaMes,
            @Parameter(description = "UF (2 letras maiúsculas)", required = true)
            @RequestParam("uf") String uf
    ) {
        log.debug("REQUEST POST /v1/sia/import/upload - ano={}, mes={}, uf={}, filename={}",
                competenciaAno, competenciaMes, uf, file != null ? file.getOriginalFilename() : null);

        // Validação do arquivo
        if (file == null || file.isEmpty()) {
            log.warn("Upload rejeitado: arquivo não fornecido ou vazio");
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("erro", "Arquivo é obrigatório. Envie o arquivo usando o campo 'file' no multipart/form-data.");
            errorResponse.put("detalhes", "Certifique-se de que o Content-Type seja 'multipart/form-data' e que o campo do arquivo seja nomeado como 'file'.");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null || tenant.getId() == null) {
            return ResponseEntity.status(401).build();
        }

        UUID userId = obterUserIdAutenticado();

        var job = importJobUploadService.criarJobUpload(
                file,
                ImportJobTipoEnum.SIA_PA,
                competenciaAno,
                competenciaMes,
                uf,
                tenant,
                userId
        );

        Map<String, Object> response = new HashMap<>();
        response.put("jobId", job.getId());
        response.put("status", job.getStatus() != null ? job.getStatus().name() : null);
        response.put("mensagem", "Upload concluído. Processamento será executado em background.");
        response.put("originalFilename", job.getOriginalFilename());
        response.put("sizeBytes", job.getSizeBytes());
        response.put("statusUrl", "/api/v1/import-jobs/" + job.getId() + "/status");
        response.put("errorsUrl", "/api/v1/import-jobs/" + job.getId() + "/errors");
        return ResponseEntity.accepted().body(response);
    }


    @GetMapping("/import/arquivos/{ano}/{uf}/{mes}")
    @Operation(
            summary = "Listar arquivos SIA-SUS disponíveis",
            description = "Lista arquivos CSV do SIA-SUS disponíveis para importação",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de arquivos encontrados"),
                    @ApiResponse(responseCode = "404", description = "Pasta não encontrada")
            }
    )
    public ResponseEntity<Map<String, Object>> listarArquivos(
            @Parameter(description = "Ano no formato YYYY", required = true)
            @PathVariable String ano,
            @Parameter(description = "UF no formato de 2 letras", required = true)
            @PathVariable String uf,
            @Parameter(description = "Mês no formato MM", required = true)
            @PathVariable String mes) {
        log.debug("REQUEST GET /v1/sia/import/arquivos/{}/{}/{}", ano, uf, mes);
        Path mesPath = Paths.get(basePath, ano, uf, mes);

        if (!Files.exists(mesPath) || !Files.isDirectory(mesPath)) {
            log.warn("Pasta do mês não encontrada: {}", mesPath);
            Map<String, Object> response = new HashMap<>();
            response.put("erro", "Pasta do mês não encontrada: " + mesPath);
            return ResponseEntity.notFound().build();
        }

        try (Stream<Path> paths = Files.list(mesPath)) {
            Map<String, Object> response = new HashMap<>();
            response.put("ano", ano);
            response.put("uf", uf);
            response.put("mes", mes);
            response.put("arquivos", paths
                    .filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().toLowerCase().endsWith(".csv"))
                    .map(p -> p.getFileName().toString())
                    .sorted()
                    .toList());

            log.debug("Arquivos listados com sucesso para {}/{}/{}. Total: {}", 
                    ano, uf, mes, ((java.util.List<?>) response.get("arquivos")).size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erro ao listar arquivos de {}/{}/{} - mensagem: {}", ano, uf, mes, e.getMessage(), e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("erro", e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    @GetMapping("/import/meses/{ano}/{uf}")
    @Operation(
            summary = "Listar meses SIA-SUS disponíveis",
            description = "Lista meses com dados SIA-SUS disponíveis para importação",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de meses encontrados"),
                    @ApiResponse(responseCode = "404", description = "Pasta não encontrada")
            }
    )
    public ResponseEntity<Map<String, Object>> listarMeses(
            @Parameter(description = "Ano no formato YYYY", required = true)
            @PathVariable String ano,
            @Parameter(description = "UF no formato de 2 letras", required = true)
            @PathVariable String uf) {
        log.debug("REQUEST GET /v1/sia/import/meses/{}/{}", ano, uf);
        Path ufPath = Paths.get(basePath, ano, uf);

        if (!Files.exists(ufPath) || !Files.isDirectory(ufPath)) {
            log.warn("Pasta do estado não encontrada: {}", ufPath);
            Map<String, Object> response = new HashMap<>();
            response.put("erro", "Pasta do estado não encontrada: " + ufPath);
            return ResponseEntity.notFound().build();
        }

        try (Stream<Path> paths = Files.list(ufPath)) {
            Map<String, Object> response = new HashMap<>();
            response.put("ano", ano);
            response.put("uf", uf);
            response.put("meses", paths
                    .filter(Files::isDirectory)
                    .map(p -> p.getFileName().toString())
                    .sorted()
                    .toList());

            log.debug("Meses listados com sucesso para {}/{}. Total: {}", 
                    ano, uf, ((java.util.List<?>) response.get("meses")).size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erro ao listar meses de {}/{} - mensagem: {}", ano, uf, e.getMessage(), e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("erro", e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    private UUID obterUserIdAutenticado() {
        try {
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
        } catch (Exception e) {
            throw new BadRequestException("Não foi possível obter usuário autenticado");
        }
    }
}

