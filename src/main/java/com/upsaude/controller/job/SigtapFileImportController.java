package com.upsaude.controller.job;

import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.job.ImportJobUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping("/v1/sigtap")
@Tag(name = "SIGTAP Importação", description = "Endpoints para importação de arquivos TXT do SIGTAP")
@Slf4j
public class SigtapFileImportController {

    private final ImportJobUploadService importJobUploadService;
    private final TenantService tenantService;
    
    @Value("${sigtap.import.base-path:data_import/sigtap}")
    private String basePath;

    public SigtapFileImportController(
            ImportJobUploadService importJobUploadService,
            TenantService tenantService) {
        this.importJobUploadService = importJobUploadService;
        this.tenantService = tenantService;
    }

    @PostMapping("/import/upload")
    @Operation(
            summary = "Upload de arquivo SIGTAP (assíncrono)",
            description = "Recebe um arquivo TXT do SIGTAP e cria um job ENFILEIRADO para processamento em background. Retorna 202 Accepted imediatamente.",
            responses = {
                    @ApiResponse(responseCode = "202", description = "Upload aceito e job criado"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado"),
                    @ApiResponse(responseCode = "500", description = "Erro ao criar job")
            }
    )
    public ResponseEntity<Map<String, Object>> uploadSigtap(
            @Parameter(description = "Arquivo TXT (SIGTAP)", required = true)
            @RequestParam("file") MultipartFile file,
            @Parameter(description = "Arquivo de layout TXT/CSV (SIGTAP *_layout.txt)", required = true)
            @RequestParam("layoutFile") MultipartFile layoutFile,
            @Parameter(description = "Competência no formato AAAAMM (ex: 202512)", required = true)
            @RequestParam("competencia") String competencia
    ) {
        log.debug("REQUEST POST /v1/sigtap/import/upload - competencia={}, filename={}",
                competencia, file != null ? file.getOriginalFilename() : null);

        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null || tenant.getId() == null) {
            return ResponseEntity.status(401).build();
        }

        if (competencia == null || !competencia.matches("\\d{6}")) {
            Map<String, Object> error = new HashMap<>();
            error.put("erro", "Competência inválida. Deve estar no formato AAAAMM");
            return ResponseEntity.badRequest().body(error);
        }

        String ano = competencia.substring(0, 4);
        String mes = competencia.substring(4, 6);
        UUID userId = obterUserIdAutenticado();

        var job = importJobUploadService.criarJobUploadComLayoutSigtap(
                file,
                layoutFile,
                ano,
                mes,
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

    @PostMapping("/import/{competencia}")
    @Operation(
            summary = "[DEPRECATED] Importar arquivos SIGTAP (legado/síncrono)",
            description = "DEPRECATED: este endpoint não executa mais processamento pesado no ciclo HTTP. Use POST /v1/sigtap/import/upload (envie tb_*.txt + tb_*_layout.txt) e acompanhe o job em /v1/import-jobs/{jobId}/status.",
            deprecated = true,
            responses = {
                    @ApiResponse(responseCode = "410", description = "Endpoint legado desativado"),
                    @ApiResponse(responseCode = "500", description = "Erro na importação")
            }
    )
    @Deprecated
    public ResponseEntity<Map<String, Object>> importarCompetencia(
            @Parameter(description = "Competência no formato AAAAMM (ex: 202512)", required = true)
            @PathVariable String competencia) {
        log.debug("REQUEST POST /v1/sigtap/import/{}", competencia);
        Map<String, Object> response = new HashMap<>();
        response.put("erro", "Endpoint legado desativado. Use /v1/sigtap/import/upload (file + layoutFile) e acompanhe o job em /v1/import-jobs/{jobId}/status.");
        response.put("sucesso", false);
        response.put("novoEndpoint", "/api/v1/sigtap/import/upload");
        return ResponseEntity.status(410).body(response);
    }

    @GetMapping("/import/arquivos/{competencia}")
    @Operation(
            summary = "Listar arquivos disponíveis",
            description = "Lista todos os arquivos TXT disponíveis para importação de uma competência",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de arquivos"),
                    @ApiResponse(responseCode = "404", description = "Pasta da competência não encontrada")
            }
    )
    public ResponseEntity<Map<String, Object>> listarArquivos(
            @Parameter(description = "Competência no formato AAAAMM", required = true)
            @PathVariable String competencia) {
        log.debug("REQUEST GET /v1/sigtap/import/arquivos/{}", competencia);
        Path competenciaPath = Paths.get(basePath, competencia);

        if (!Files.exists(competenciaPath) || !Files.isDirectory(competenciaPath)) {
            log.warn("Pasta da competência não encontrada: {}", competenciaPath);
            Map<String, Object> response = new HashMap<>();
            response.put("erro", "Pasta da competência não encontrada: " + competenciaPath);
            return ResponseEntity.notFound().build();
        }

        try (Stream<Path> paths = Files.list(competenciaPath)) {
            Map<String, Object> response = new HashMap<>();
            response.put("competencia", competencia);
            response.put("arquivos", paths
                    .filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().endsWith(".txt"))
                    .filter(p -> !p.getFileName().toString().endsWith("_layout.txt"))
                    .map(p -> p.getFileName().toString())
                    .sorted()
                    .toList());

            log.debug("Arquivos listados com sucesso para competência: {}. Total: {}", 
                    competencia, ((java.util.List<?>) response.get("arquivos")).size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erro ao listar arquivos da competência {} - mensagem: {}", competencia, e.getMessage(), e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("erro", e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
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
}
