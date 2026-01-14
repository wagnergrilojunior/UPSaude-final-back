package com.upsaude.controller.api.sistema.dashboard;

import com.upsaude.api.response.sistema.dashboard.DashboardEstabelecimentoResponse;
import com.upsaude.api.response.sistema.dashboard.DashboardMedicoResponse;
import com.upsaude.api.response.sistema.dashboard.DashboardTenantResponse;
import com.upsaude.service.api.sistema.dashboard.DashboardService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/v1/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboards", description = "Dashboards contextuais por tenant, estabelecimento e médico")
public class DashboardController {

    private final DashboardService dashboardService;
    private final TenantService tenantService;

    @GetMapping("/tenant")
    @Operation(summary = "Dashboard consolidado do tenant (prefeitura)")
    public ResponseEntity<DashboardTenantResponse> dashboardTenant(
            @Parameter(description = "Data de início do período (formato: yyyy-MM-dd). Padrão: 30 dias atrás")
            @RequestParam(value = "dataInicio", required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @Parameter(description = "Data de fim do período (formato: yyyy-MM-dd). Padrão: hoje")
            @RequestParam(value = "dataFim", required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim
    ) {
        UUID tenantId = tenantService.validarTenantAtual();
        
        if (dataInicio == null) {
            dataInicio = LocalDate.now().minusDays(30);
        }
        if (dataFim == null) {
            dataFim = LocalDate.now();
        }
        
        log.debug("REQUEST GET /v1/dashboard/tenant?dataInicio={}&dataFim={}", dataInicio, dataFim);
        return ResponseEntity.ok(dashboardService.dashboardTenant(tenantId, dataInicio, dataFim));
    }

    @GetMapping("/estabelecimento/{estabelecimentoId}")
    @Operation(summary = "Dashboard de uma unidade de saúde (estabelecimento)")
    public ResponseEntity<DashboardEstabelecimentoResponse> dashboardEstabelecimento(
            @Parameter(description = "ID do estabelecimento", required = true)
            @PathVariable("estabelecimentoId") UUID estabelecimentoId,
            @Parameter(description = "Data de início do período (formato: yyyy-MM-dd). Padrão: 30 dias atrás")
            @RequestParam(value = "dataInicio", required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @Parameter(description = "Data de fim do período (formato: yyyy-MM-dd). Padrão: hoje")
            @RequestParam(value = "dataFim", required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim
    ) {
        if (dataInicio == null) {
            dataInicio = LocalDate.now().minusDays(30);
        }
        if (dataFim == null) {
            dataFim = LocalDate.now();
        }
        
        log.debug("REQUEST GET /v1/dashboard/estabelecimento/{}?dataInicio={}&dataFim={}", estabelecimentoId, dataInicio, dataFim);
        return ResponseEntity.ok(dashboardService.dashboardEstabelecimento(estabelecimentoId, dataInicio, dataFim));
    }

    @GetMapping("/medico/{medicoId}")
    @Operation(summary = "Dashboard de um médico/profissional")
    public ResponseEntity<DashboardMedicoResponse> dashboardMedico(
            @Parameter(description = "ID do médico", required = true)
            @PathVariable("medicoId") UUID medicoId,
            @Parameter(description = "Data de início do período (formato: yyyy-MM-dd). Padrão: 30 dias atrás")
            @RequestParam(value = "dataInicio", required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @Parameter(description = "Data de fim do período (formato: yyyy-MM-dd). Padrão: hoje")
            @RequestParam(value = "dataFim", required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim
    ) {
        if (dataInicio == null) {
            dataInicio = LocalDate.now().minusDays(30);
        }
        if (dataFim == null) {
            dataFim = LocalDate.now();
        }
        
        log.debug("REQUEST GET /v1/dashboard/medico/{}?dataInicio={}&dataFim={}", medicoId, dataInicio, dataFim);
        return ResponseEntity.ok(dashboardService.dashboardMedico(medicoId, dataInicio, dataFim));
    }
}
