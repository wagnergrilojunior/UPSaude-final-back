package com.upsaude.controller.api.sia;

import com.upsaude.api.response.sia.analytics.SiaPaComparacaoResponse;
import com.upsaude.api.response.sia.analytics.SiaPaRankingResponse;
import com.upsaude.api.response.sia.analytics.SiaPaSazonalidadeResponse;
import com.upsaude.api.response.sia.analytics.SiaPaTendenciaResponse;
import com.upsaude.service.api.sia.SiaPaAnalyticsService;
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
@RequestMapping("/v1/sia/analytics")
@RequiredArgsConstructor
@Tag(name = "SIA-SUS Analytics", description = "Análises temporais e comparativas do SIA-PA")
public class SiaPaAnalyticsController {

    private final SiaPaAnalyticsService service;

    @GetMapping("/tendencia")
    @Operation(summary = "Tendência temporal por UF e período (competências)")
    public ResponseEntity<SiaPaTendenciaResponse> tendencia(
            @Parameter(description = "UF (2 letras). Se omitido, tenta inferir pelo tenant", required = false)
            @RequestParam(value = "uf", required = false) String uf,
            @Parameter(description = "Competência inicial (AAAAMM)", required = true)
            @RequestParam("competenciaInicio") String competenciaInicio,
            @Parameter(description = "Competência final (AAAAMM)", required = true)
            @RequestParam("competenciaFim") String competenciaFim
    ) {
        return ResponseEntity.ok(service.calcularTendenciaTemporal(uf, competenciaInicio, competenciaFim));
    }

    @GetMapping("/comparacao")
    @Operation(summary = "Comparação entre duas competências (base vs comparação)")
    public ResponseEntity<SiaPaComparacaoResponse> comparacao(
            @Parameter(description = "UF (2 letras). Se omitido, tenta inferir pelo tenant", required = false)
            @RequestParam(value = "uf", required = false) String uf,
            @Parameter(description = "Competência base (AAAAMM)", required = true)
            @RequestParam("competenciaBase") String competenciaBase,
            @Parameter(description = "Competência de comparação (AAAAMM)", required = true)
            @RequestParam("competenciaComparacao") String competenciaComparacao
    ) {
        return ResponseEntity.ok(service.compararPeriodos(uf, competenciaBase, competenciaComparacao));
    }

    @GetMapping("/sazonalidade")
    @Operation(summary = "Sazonalidade por mês (média) em um período de competências")
    public ResponseEntity<SiaPaSazonalidadeResponse> sazonalidade(
            @Parameter(description = "UF (2 letras). Se omitido, tenta inferir pelo tenant", required = false)
            @RequestParam(value = "uf", required = false) String uf,
            @Parameter(description = "Competência inicial (AAAAMM)", required = true)
            @RequestParam("competenciaInicio") String competenciaInicio,
            @Parameter(description = "Competência final (AAAAMM)", required = true)
            @RequestParam("competenciaFim") String competenciaFim
    ) {
        return ResponseEntity.ok(service.calcularSazonalidade(uf, competenciaInicio, competenciaFim));
    }

    @GetMapping("/ranking")
    @Operation(summary = "Ranking de estabelecimentos por valor aprovado")
    public ResponseEntity<SiaPaRankingResponse> ranking(
            @Parameter(description = "UF (2 letras). Se omitido, tenta inferir pelo tenant", required = false)
            @RequestParam(value = "uf", required = false) String uf,
            @Parameter(description = "Competência (AAAAMM)", required = true)
            @RequestParam("competencia") String competencia,
            @Parameter(description = "Limite (default 20, máximo 200)", required = false)
            @RequestParam(value = "limit", required = false) Integer limit
    ) {
        return ResponseEntity.ok(service.rankingEstabelecimentos(uf, competencia, limit));
    }
}

