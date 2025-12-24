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

    @PostMapping("/import/upload-zip")
    @Operation(
            summary = "Upload de arquivo ZIP SIGTAP (assíncrono)",
            description = "Recebe um arquivo ZIP contendo todos os arquivos TXT do SIGTAP de uma competência. " +
                    "Extrai automaticamente os arquivos, salva no Storage e cria jobs ordenados por prioridade " +
                    "baseada em dependências. Retorna 202 Accepted imediatamente.",
            responses = {
                    @ApiResponse(responseCode = "202", description = "Upload aceito e jobs criados"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado"),
                    @ApiResponse(responseCode = "500", description = "Erro ao processar ZIP")
            }
    )
    public ResponseEntity<Map<String, Object>> uploadZipSigtap(
            @Parameter(description = "Arquivo ZIP contendo arquivos TXT do SIGTAP", required = true)
            @RequestParam("zipFile") MultipartFile zipFile,
            @Parameter(description = "Competência no formato AAAAMM (ex: 202512)", required = true)
            @RequestParam("competencia") String competencia
    ) {
        log.debug("REQUEST POST /v1/sigtap/import/upload-zip - competencia={}, filename={}",
                competencia, zipFile != null ? zipFile.getOriginalFilename() : null);

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

        ImportJobUploadService.CriarJobsZipResultado resultado = importJobUploadService.criarJobsFromZipSigtap(
                zipFile,
                ano,
                mes,
                tenant,
                userId
        );

        Map<String, Object> response = new HashMap<>();
        response.put("totalJobsCriados", resultado.getJobsCriados().size());
        response.put("totalArquivosProcessados", resultado.getTotalArquivosProcessados());
        response.put("totalErros", resultado.getErros().size());
        response.put("jobs", resultado.getJobsCriados().stream()
                .map(job -> {
                    Map<String, Object> jobInfo = new HashMap<>();
                    jobInfo.put("id", job.getId());
                    jobInfo.put("status", job.getStatus() != null ? job.getStatus().name() : null);
                    jobInfo.put("originalFilename", job.getOriginalFilename());
                    jobInfo.put("priority", job.getPriority());
                    jobInfo.put("statusUrl", "/api/v1/import-jobs/" + job.getId() + "/status");
                    return jobInfo;
                })
                .toList());
        if (!resultado.getErros().isEmpty()) {
            response.put("erros", resultado.getErros());
        }
        response.put("mensagem", "Upload do ZIP concluído. " + resultado.getJobsCriados().size() + 
                " jobs criados e enfileirados para processamento em background.");
        return ResponseEntity.accepted().body(response);
    }

    @PostMapping("/import/{competencia}")
    @Operation(
            summary = "[DEPRECATED] Importar arquivos SIGTAP (legado/síncrono)",
            description = "DEPRECATED: este endpoint não executa mais processamento pesado no ciclo HTTP. Use POST /v1/sigtap/import/upload-zip (envie arquivo ZIP completo) e acompanhe os jobs em /v1/import-jobs.",
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
        response.put("erro", "Endpoint legado desativado. Use /v1/sigtap/import/upload-zip (envie arquivo ZIP completo) e acompanhe os jobs em /v1/import-jobs.");
        response.put("sucesso", false);
        response.put("novoEndpoint", "/api/v1/sigtap/import/upload-zip");
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
