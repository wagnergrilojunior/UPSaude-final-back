package com.upsaude.controller.job;

import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.enums.ImportJobTipoEnum;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.job.ImportJobUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

@RestController
@RequestMapping("/v1/cid10")
@Tag(name = "CID10 Importação", description = "Endpoints para importação de arquivos CSV do CID10")
@RequiredArgsConstructor
@Slf4j
public class Cid10FileImportController {

    private final ImportJobUploadService importJobUploadService;
    private final TenantService tenantService;
    
    @Value("${cid10.import.base-path:data_import/cid10}")
    private String basePath;

    @PostMapping("/import/upload")
    @Operation(
            summary = "Upload de arquivo CID10 (assíncrono)",
            description = "Recebe um arquivo CSV do CID10 e cria um job ENFILEIRADO para processamento em background. Retorna 202 Accepted imediatamente.",
            responses = {
                    @ApiResponse(responseCode = "202", description = "Upload aceito e job criado"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado"),
                    @ApiResponse(responseCode = "500", description = "Erro ao criar job")
            }
    )
    public ResponseEntity<Map<String, Object>> uploadCid10(
            @Parameter(description = "Arquivo CSV (CID10)", required = true)
            @RequestParam("file") MultipartFile file,
            @Parameter(description = "Competência no formato AAAAMM (ex: 202512)", required = true)
            @RequestParam("competencia") String competencia
    ) {
        log.debug("REQUEST POST /v1/cid10/import/upload - competencia={}, filename={}",
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

        var job = importJobUploadService.criarJobUpload(
                file,
                ImportJobTipoEnum.CID10,
                ano,
                mes,
                null,
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
            summary = "[DEPRECATED] Importar arquivos CID10 (legado/síncrono)",
            description = "DEPRECATED: este endpoint não executa mais processamento pesado no ciclo HTTP. Use POST /v1/cid10/import/upload (202 Accepted) e acompanhe o job em /v1/import-jobs/{jobId}/status.",
            deprecated = true,
            responses = {
                    @ApiResponse(responseCode = "410", description = "Endpoint legado desativado"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "500", description = "Erro na importação")
            }
    )
    @Deprecated
    public ResponseEntity<Map<String, Object>> importarCompetencia(
            @Parameter(description = "Competência no formato AAAAMM (ex: 202512)", required = true)
            @PathVariable String competencia) {
        log.debug("REQUEST POST /v1/cid10/import/{}", competencia);
        Map<String, Object> response = new HashMap<>();
        response.put("erro", "Endpoint legado desativado. Use /v1/cid10/import/upload (202 Accepted) e acompanhe o job em /v1/import-jobs/{jobId}/status.");
        response.put("sucesso", false);
        response.put("novoEndpoint", "/api/v1/cid10/import/upload");
        return ResponseEntity.status(410).body(response);
    }

    @GetMapping("/import/arquivos/{competencia}")
    @Operation(
            summary = "Listar arquivos disponíveis",
            description = "Lista todos os arquivos CSV disponíveis para importação de uma competência",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de arquivos"),
                    @ApiResponse(responseCode = "404", description = "Pasta da competência não encontrada")
            }
    )
    public ResponseEntity<Map<String, Object>> listarArquivos(
            @Parameter(description = "Competência no formato AAAAMM", required = true)
            @PathVariable String competencia) {
        log.debug("REQUEST GET /v1/cid10/import/arquivos/{}", competencia);
        Path competenciaPath = Paths.get(basePath, competencia);

        if (!Files.exists(competenciaPath) || !Files.isDirectory(competenciaPath)) {
            throw new NotFoundException("Pasta da competência não encontrada: " + competenciaPath);
        }

        try (Stream<Path> paths = Files.list(competenciaPath)) {
            Map<String, Object> response = new HashMap<>();
            response.put("competencia", competencia);
            response.put("arquivos", paths
                    .filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().endsWith(".CSV"))
                    .map(p -> p.getFileName().toString())
                    .sorted()
                    .toList());

            log.debug("Arquivos listados com sucesso para competência: {}. Total: {}", 
                    competencia, ((java.util.List<?>) response.get("arquivos")).size());
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            log.error("Erro ao listar arquivos da competência {} - mensagem: {}", competencia, e.getMessage(), e);
            throw new NotFoundException("Erro ao acessar pasta da competência: " + competenciaPath, e);
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
