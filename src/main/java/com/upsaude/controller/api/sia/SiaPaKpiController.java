package com.upsaude.controller.api.sia;

import com.upsaude.api.response.sia.kpi.SiaPaKpiResponse;
import com.upsaude.service.api.sia.SiaPaKpiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/sia/kpi")
@RequiredArgsConstructor
@Tag(name = "SIA-SUS KPIs", description = "Indicadores de produção (SIA-PA) para gestão e análise")
public class SiaPaKpiController {

    private final SiaPaKpiService service;

    @GetMapping("/geral")
    @Operation(summary = "KPIs gerais do SIA-PA por competência e UF")
    public ResponseEntity<SiaPaKpiResponse> kpiGeral(
            @Parameter(description = "Competência no formato AAAAMM", required = true)
            @RequestParam("competencia") String competencia,
            @Parameter(description = "UF (2 letras). Se omitido, tenta inferir pelo tenant", required = false)
            @RequestParam(value = "uf", required = false) String uf
    ) {
        log.debug("REQUEST GET /v1/sia/kpi/geral?competencia={}&uf={}", competencia, uf);
        return ResponseEntity.ok(service.kpiGeral(competencia, uf));
    }

    @GetMapping("/estabelecimento/{cnes}")
    @Operation(summary = "KPIs do SIA-PA por estabelecimento (CNES)")
    public ResponseEntity<SiaPaKpiResponse> kpiPorEstabelecimento(
            @Parameter(description = "CNES do estabelecimento", required = true)
            @PathVariable("cnes") String cnes,
            @Parameter(description = "Competência no formato AAAAMM", required = true)
            @RequestParam("competencia") String competencia,
            @Parameter(description = "UF (2 letras). Se omitido, tenta inferir pelo tenant", required = false)
            @RequestParam(value = "uf", required = false) String uf
    ) {
        log.debug("REQUEST GET /v1/sia/kpi/estabelecimento/{}?competencia={}&uf={}", cnes, competencia, uf);
        return ResponseEntity.ok(service.kpiPorEstabelecimento(competencia, uf, cnes));
    }

    @GetMapping("/procedimento/{codigo}")
    @Operation(summary = "KPIs do SIA-PA por procedimento")
    public ResponseEntity<SiaPaKpiResponse> kpiPorProcedimento(
            @Parameter(description = "Código do procedimento (10 dígitos)", required = true)
            @PathVariable("codigo") String codigo,
            @Parameter(description = "Competência no formato AAAAMM", required = true)
            @RequestParam("competencia") String competencia,
            @Parameter(description = "UF (2 letras). Se omitido, tenta inferir pelo tenant", required = false)
            @RequestParam(value = "uf", required = false) String uf
    ) {
        log.debug("REQUEST GET /v1/sia/kpi/procedimento/{}?competencia={}&uf={}", codigo, competencia, uf);
        return ResponseEntity.ok(service.kpiPorProcedimento(competencia, uf, codigo));
    }
}

