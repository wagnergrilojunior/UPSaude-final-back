package com.upsaude.controller.api.sia;

import com.upsaude.api.response.sia.dashboard.SiaPaDashboardResponse;
import com.upsaude.service.api.sia.SiaPaDashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/sia/dashboard")
@RequiredArgsConstructor
@Tag(name = "SIA-SUS Dashboard", description = "Endpoint consolidado para dashboard de produção (SIA-PA)")
public class SiaPaDashboardController {

    private final SiaPaDashboardService service;

    @GetMapping
    @Operation(summary = "Dados consolidados para dashboard (KPIs, rankings, tendência, anomalias)")
    public ResponseEntity<SiaPaDashboardResponse> dashboard(
            @Parameter(description = "UF (2 letras)", required = true)
            @RequestParam("uf") String uf,
            @Parameter(description = "Competência AAAAMM", required = true)
            @RequestParam("competencia") String competencia,
            @Parameter(description = "Competência inicial AAAAMM para tendência (default: competência-11)", required = false)
            @RequestParam(value = "competenciaInicio", required = false) String competenciaInicio,
            @Parameter(description = "Competência final AAAAMM para tendência (default: competência)", required = false)
            @RequestParam(value = "competenciaFim", required = false) String competenciaFim,
            @Parameter(description = "Incluir conciliação financeira (default false)", required = false)
            @RequestParam(value = "incluirFinanceiro", required = false) Boolean incluirFinanceiro
    ) {
        return ResponseEntity.ok(service.dashboard(uf, competencia, competenciaInicio, competenciaFim, incluirFinanceiro));
    }
}

