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
@Tag(name = "SIA-SUS Importação", description = "Endpoints para importação de arquivos CSV do SIA-SUS (Sistema de Informações Ambulatoriais do SUS). " +
        "O SIA-SUS contém dados de procedimentos ambulatoriais realizados no SUS, utilizados para indicadores de produção, " +
        "análises por município/estado/estabelecimento e relatórios gerenciais.")
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

    @PostMapping("/import/upload")
    @Operation(
            summary = "Upload de arquivo SIA-SUS PA (assíncrono)",
            description = "Recebe um arquivo CSV do SIA-SUS (Sistema de Informações Ambulatoriais do SUS) e cria um job " +
                    "ENFILEIRADO para processamento em background. " +
                    "O SIA-SUS contém informações sobre procedimentos ambulatoriais realizados no SUS. " +
                    "O processamento é executado de forma assíncrona, permitindo que o upload retorne imediatamente " +
                    "com status 202 Accepted. O progresso do processamento pode ser acompanhado através do endpoint " +
                    "/v1/import-jobs/{jobId}/status. " +
                    "Os dados do SIA-SUS são utilizados para indicadores de produção ambulatorial, análises por " +
                    "município/estado/estabelecimento e relatórios gerenciais.",
            responses = {
                    @ApiResponse(responseCode = "202", description = "Upload aceito e job criado. O processamento será executado em background."),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos (arquivo vazio, formato inválido, parâmetros incorretos)"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado"),
                    @ApiResponse(responseCode = "500", description = "Erro ao criar job")
            }
    )
    public ResponseEntity<Map<String, Object>> uploadSiaPa(
            @Parameter(description = "Arquivo CSV do SIA-SUS PA (Sistema de Informações Ambulatoriais - Produção Ambulatorial). " +
                    "O arquivo deve conter dados de procedimentos ambulatoriais do SUS no formato CSV.", required = true)
            @RequestParam("file") MultipartFile file,
            @Parameter(description = "Ano da competência no formato YYYY (ex: 2025)", required = true)
            @RequestParam("competenciaAno") String competenciaAno,
            @Parameter(description = "Mês da competência no formato MM (ex: 01 para janeiro)", required = true)
            @RequestParam("competenciaMes") String competenciaMes,
            @Parameter(description = "Unidade Federativa (UF) no formato de 2 letras maiúsculas (ex: MG, SP, RJ)", required = true)
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

    @PostMapping("/import/{ano}/{uf}/{mes}")
    @Operation(
            summary = "[DEPRECATED] Importar arquivos SIA-SUS PA (legado/síncrono)",
            description = "DEPRECATED: este endpoint não executa mais processamento pesado no ciclo HTTP. Use POST /v1/sia/import/upload (202 Accepted) e acompanhe o job em /v1/import-jobs/{jobId}/status.",
            deprecated = true,
            responses = {
                    @ApiResponse(responseCode = "410", description = "Endpoint legado desativado"),
                    @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
                    @ApiResponse(responseCode = "500", description = "Erro na importação")
            }
    )
    @Deprecated
    public ResponseEntity<Map<String, Object>> importarMes(
            @Parameter(description = "Ano no formato YYYY (ex: 2025)", required = true, example = "2025")
            @PathVariable String ano,
            @Parameter(description = "UF no formato de 2 letras (ex: MG)", required = true, example = "MG")
            @PathVariable String uf,
            @Parameter(description = "Mês no formato MM (ex: 01)", required = true, example = "01")
            @PathVariable String mes) {
        log.debug("REQUEST POST /v1/sia/import/{}/{}/{}", ano, uf, mes);
        
        // Validação básica
        if (!ano.matches("\\d{4}")) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("erro", "Ano inválido. Deve estar no formato YYYY");
            errorResponse.put("sucesso", false);
            return ResponseEntity.badRequest().body(errorResponse);
        }
        
        if (!uf.matches("[A-Z]{2}")) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("erro", "UF inválida. Deve estar no formato de 2 letras maiúsculas");
            errorResponse.put("sucesso", false);
            return ResponseEntity.badRequest().body(errorResponse);
        }
        
        if (!mes.matches("(0[1-9]|1[0-2])")) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("erro", "Mês inválido. Deve estar no formato MM (01-12)");
            errorResponse.put("sucesso", false);
            return ResponseEntity.badRequest().body(errorResponse);
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("erro", "Endpoint legado desativado. Use /v1/sia/import/upload (202 Accepted) e acompanhe o job em /v1/import-jobs/{jobId}/status.");
        response.put("sucesso", false);
        response.put("novoEndpoint", "/api/v1/sia/import/upload");
        return ResponseEntity.status(410).body(response);
    }

    @GetMapping("/import/arquivos/{ano}/{uf}/{mes}")
    @Operation(
            summary = "Listar arquivos SIA-SUS disponíveis",
            description = "Lista todos os arquivos CSV do SIA-SUS disponíveis para importação de um mês específico. " +
                    "Os arquivos são organizados por ano, UF e mês no sistema de arquivos.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de arquivos CSV do SIA-SUS encontrados"),
                    @ApiResponse(responseCode = "404", description = "Pasta do mês não encontrada")
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
            description = "Lista todos os meses com dados SIA-SUS disponíveis para importação de um estado específico. " +
                    "Os meses são organizados em pastas no formato MM (01-12) dentro da pasta do estado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de meses com dados SIA-SUS disponíveis"),
                    @ApiResponse(responseCode = "404", description = "Pasta do estado não encontrada")
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

