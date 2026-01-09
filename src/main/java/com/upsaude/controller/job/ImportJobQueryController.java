package com.upsaude.controller.job;

import com.upsaude.api.response.sistema.importacao.ImportJobErrorResponse;
import com.upsaude.api.response.sistema.importacao.ImportJobResponse;
import com.upsaude.api.response.sistema.importacao.ImportJobStatusResponse;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.enums.ImportJobStatusEnum;
import com.upsaude.enums.ImportJobTipoEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.service.job.ImportJobQueryService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/import-jobs")
@Tag(name = "Import Jobs - Consulta", description = "Endpoints para consultar status e detalhes de jobs de importação")
@Slf4j
@RequiredArgsConstructor
public class ImportJobQueryController {

    private final ImportJobQueryService importJobQueryService;
    private final TenantService tenantService;
    private final com.upsaude.service.impl.job.ImportJobScheduler importJobScheduler;

    @GetMapping("/{jobId}")
    @Operation(
            summary = "Obter detalhes de um job de importação",
            description = "Retorna informações completas de um job de importação pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Job encontrado"),
                    @ApiResponse(responseCode = "404", description = "Job não encontrado"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado")
            }
    )
    public ResponseEntity<ImportJobResponse> obterPorId(
            @Parameter(description = "ID do job de importação", required = true)
            @PathVariable java.util.UUID jobId) {
        log.debug("REQUEST GET /v1/import-jobs/{}", jobId);

        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) {
            throw new BadRequestException("Tenant não encontrado para o usuário autenticado");
        }

        ImportJobResponse response = importJobQueryService.obterPorId(jobId, tenant.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(
            summary = "Listar jobs de importação",
            description = "Lista jobs de importação do tenant atual com paginação",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de jobs retornada com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado")
            }
    )
    public ResponseEntity<Page<ImportJobResponse>> listar(
            @Parameter(description = "Número da página (0-indexed)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página", example = "20")
            @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Campo para ordenação", example = "createdAt")
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Direção da ordenação (ASC ou DESC)", example = "DESC")
            @RequestParam(defaultValue = "DESC") String sortDir) {
        log.debug("REQUEST GET /v1/import-jobs?page={}&size={}&sortBy={}&sortDir={}", page, size, sortBy, sortDir);

        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) {
            throw new BadRequestException("Tenant não encontrado para o usuário autenticado");
        }

        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ImportJobResponse> response = importJobQueryService.listarPorTenant(tenant.getId(), pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{status}")
    @Operation(
            summary = "Listar jobs por status",
            description = "Lista jobs de importação filtrados por status",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de jobs retornada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Status inválido"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado")
            }
    )
    public ResponseEntity<Page<ImportJobResponse>> listarPorStatus(
            @Parameter(description = "Status do job (ENFILEIRADO, PROCESSANDO, CONCLUIDO, ERRO, CANCELADO, PAUSADO)", required = true)
            @PathVariable String status,
            @Parameter(description = "Número da página (0-indexed)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página", example = "20")
            @RequestParam(defaultValue = "20") int size) {
        log.debug("REQUEST GET /v1/import-jobs/status/{}?page={}&size={}", status, page, size);

        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) {
            throw new BadRequestException("Tenant não encontrado para o usuário autenticado");
        }

        ImportJobStatusEnum statusEnum;
        try {
            statusEnum = ImportJobStatusEnum.fromCodigo(status.toUpperCase());
        } catch (Exception e) {
            throw new BadRequestException("Status inválido: " + status);
        }

        if (statusEnum == null) {
            throw new BadRequestException("Status inválido: " + status);
        }

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ImportJobResponse> response = importJobQueryService.listarPorTenantEStatus(tenant.getId(), statusEnum, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tipo/{tipo}")
    @Operation(
            summary = "Listar jobs por tipo",
            description = "Lista jobs de importação filtrados por tipo",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de jobs retornada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Tipo inválido"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado")
            }
    )
    public ResponseEntity<Page<ImportJobResponse>> listarPorTipo(
            @Parameter(description = "Tipo do job (SIA_PA, SIGTAP, CID10)", required = true)
            @PathVariable String tipo,
            @Parameter(description = "Número da página (0-indexed)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página", example = "20")
            @RequestParam(defaultValue = "20") int size) {
        log.debug("REQUEST GET /v1/import-jobs/tipo/{}?page={}&size={}", tipo, page, size);

        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) {
            throw new BadRequestException("Tenant não encontrado para o usuário autenticado");
        }

        ImportJobTipoEnum tipoEnum = ImportJobTipoEnum.fromCodigo(tipo.toUpperCase());
        if (tipoEnum == null) {
            throw new BadRequestException("Tipo inválido: " + tipo);
        }

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ImportJobResponse> response = importJobQueryService.listarPorTenantETipo(tenant.getId(), tipoEnum, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{jobId}/status")
    @Operation(
            summary = "Obter status de um job (para polling)",
            description = "Retorna status simplificado de um job de importação, ideal para polling do frontend",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Status do job retornado"),
                    @ApiResponse(responseCode = "404", description = "Job não encontrado"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado")
            }
    )
    public ResponseEntity<ImportJobStatusResponse> obterStatus(
            @Parameter(description = "ID do job de importação", required = true)
            @PathVariable java.util.UUID jobId) {
        log.debug("REQUEST GET /v1/import-jobs/{}/status", jobId);

        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) {
            throw new BadRequestException("Tenant não encontrado para o usuário autenticado");
        }

        ImportJobStatusResponse response = importJobQueryService.obterStatus(jobId, tenant.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{jobId}/erros")
    @Operation(
            summary = "Listar erros de um job",
            description = "Lista erros detalhados de processamento de um job com paginação",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de erros retornada"),
                    @ApiResponse(responseCode = "404", description = "Job não encontrado"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado")
            }
    )
    public ResponseEntity<Page<ImportJobErrorResponse>> listarErros(
            @Parameter(description = "ID do job de importação", required = true)
            @PathVariable java.util.UUID jobId,
            @Parameter(description = "Número da página (0-indexed)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página", example = "50")
            @RequestParam(defaultValue = "50") int size) {
        log.debug("REQUEST GET /v1/import-jobs/{}/erros?page={}&size={}", jobId, page, size);

        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) {
            throw new BadRequestException("Tenant não encontrado para o usuário autenticado");
        }

        Sort sort = Sort.by(Sort.Direction.ASC, "linha");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ImportJobErrorResponse> response = importJobQueryService.listarErrosPorJob(jobId, tenant.getId(), pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{jobId}/reprocessar")
    @Operation(
            summary = "Reprocessar um job com erro",
            description = "Reprocessa um job que está com status ERRO, resetando para ENFILEIRADO para tentar novamente. " +
                         "Apenas jobs com status ERRO podem ser reprocessados. O job será processado novamente pelo scheduler.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Job reprocessado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Job não está em status ERRO ou não possui arquivo válido"),
                    @ApiResponse(responseCode = "404", description = "Job não encontrado"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado")
            }
    )
    public ResponseEntity<ImportJobResponse> reprocessarJob(
            @Parameter(description = "ID do job de importação a ser reprocessado", required = true)
            @PathVariable java.util.UUID jobId) {
        log.info("REQUEST POST /v1/import-jobs/{}/reprocessar", jobId);

        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) {
            throw new BadRequestException("Tenant não encontrado para o usuário autenticado");
        }

        ImportJobResponse response = importJobQueryService.reprocessarJob(jobId, tenant.getId());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reprocessar-por-tipo-competencia")
    @Operation(
            summary = "Reprocessar jobs por tipo e competência",
            description = "Reprocessa todos os jobs com status ERRO de um tipo e competência específicos. " +
                         "Os jobs são reprocessados na ordem correta (por prioridade) seguindo as regras de negócio. " +
                         "Apenas jobs com status ERRO são reprocessados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Jobs reprocessados com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
                    @ApiResponse(responseCode = "401", description = "Não autenticado")
            }
    )
    public ResponseEntity<Map<String, Object>> reprocessarJobsPorTipoECompetencia(
            @Parameter(description = "Tipo do job (SIGTAP, CID10, SIA_PA)", required = true)
            @RequestParam("tipo") String tipo,
            @Parameter(description = "Ano da competência (4 dígitos)", required = true, example = "2025")
            @RequestParam("competenciaAno") String competenciaAno,
            @Parameter(description = "Mês da competência (2 dígitos)", required = true, example = "12")
            @RequestParam("competenciaMes") String competenciaMes) {
        log.info("REQUEST POST /v1/import-jobs/reprocessar-por-tipo-competencia?tipo={}&competenciaAno={}&competenciaMes={}",
                tipo, competenciaAno, competenciaMes);

        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) {
            throw new BadRequestException("Tenant não encontrado para o usuário autenticado");
        }

        ImportJobTipoEnum tipoEnum = ImportJobTipoEnum.fromCodigo(tipo.toUpperCase());
        if (tipoEnum == null) {
            throw new BadRequestException("Tipo inválido: " + tipo);
        }

        if (!competenciaAno.matches("\\d{4}")) {
            throw new BadRequestException("Ano da competência deve ter 4 dígitos");
        }
        if (!competenciaMes.matches("\\d{2}")) {
            throw new BadRequestException("Mês da competência deve ter 2 dígitos");
        }

        List<ImportJobResponse> jobsReprocessados = importJobQueryService.reprocessarJobsPorTipoECompetencia(
                tipoEnum,
                competenciaAno,
                competenciaMes,
                tenant.getId()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("totalReprocessados", jobsReprocessados.size());
        response.put("tipo", tipo);
        response.put("competenciaAno", competenciaAno);
        response.put("competenciaMes", competenciaMes);
        response.put("jobs", jobsReprocessados.stream()
                .map(job -> {
                    Map<String, Object> jobInfo = new HashMap<>();
                    jobInfo.put("id", job.getId());
                    jobInfo.put("status", job.getStatus() != null ? job.getStatus().name() : null);
                    jobInfo.put("originalFilename", job.getOriginalFilename());
                    jobInfo.put("priority", job.getPriority());
                    return jobInfo;
                })
                .toList());
        response.put("mensagem", String.format("%d jobs reprocessados com sucesso para tipo %s e competência %s/%s",
                jobsReprocessados.size(), tipo, competenciaAno, competenciaMes));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/iniciar-processamento")
    @Operation(
            summary = "Iniciar processamento manual de jobs enfileirados",
            description = "Inicia manualmente o processamento de jobs com status ENFILEIRADO, sem esperar o próximo schedule. " +
                         "Processa até o limite de jobs simultâneos configurado. " +
                         "Útil para iniciar processamento imediatamente após upload de arquivos ou para testes. " +
                         "\n\n" +
                         "**IMPORTANTE:** O processamento é executado em BACKGROUND no pool de threads do JOB, " +
                         "não no pool HTTP da API. A API retorna imediatamente após enfileirar os jobs. " +
                         "O processamento acontece em paralelo em threads dedicadas do pool do JOB.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Processamento iniciado com sucesso. Jobs foram enfileirados no pool do JOB para execução em background."),
                    @ApiResponse(responseCode = "401", description = "Não autenticado"),
                    @ApiResponse(responseCode = "500", description = "Erro ao iniciar processamento")
            }
    )
    public ResponseEntity<Map<String, Object>> iniciarProcessamento() {
        log.info("REQUEST POST /v1/import-jobs/iniciar-processamento");

        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) {
            throw new BadRequestException("Tenant não encontrado para o usuário autenticado");
        }

        int jobsIniciados = importJobScheduler.iniciarProcessamentoManual();

        Map<String, Object> response = new HashMap<>();
        response.put("jobsIniciados", jobsIniciados);
        response.put("mensagem", String.format("Processamento manual iniciado. %d jobs foram enfileirados no pool do JOB para processamento em background.", jobsIniciados));
        response.put("tenantId", tenant.getId());
        response.put("observacao", "Os jobs estão sendo processados em background no pool dedicado do JOB. Use GET /v1/import-jobs/{jobId}/status para acompanhar o progresso.");

        return ResponseEntity.ok(response);
    }
}

