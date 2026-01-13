package com.upsaude.controller.api.sia;

import com.upsaude.api.response.sia.financeiro.SiaPaFinanceiroIntegracaoResponse;
import com.upsaude.api.response.sia.financeiro.SiaPaFinanceiroReceitaPorCompetenciaResponse;
import com.upsaude.service.api.sia.SiaPaFinanceiroIntegrationService;
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
@RequestMapping("/v1/sia/financeiro")
@RequiredArgsConstructor
@Tag(name = "SIA-SUS x Financeiro/Faturamento", description = "Conciliação entre produção SIA-PA e faturamento interno")
public class SiaPaFinanceiroController {

    private final SiaPaFinanceiroIntegrationService service;

    @GetMapping("/conciliacao")
    @Operation(summary = "Conciliação SIA-PA x Faturamento por competência (tenant)")
    public ResponseEntity<SiaPaFinanceiroIntegracaoResponse> conciliacao(
            @Parameter(description = "Competência AAAAMM", required = true)
            @RequestParam("competencia") String competencia,
            @Parameter(description = "UF (2 letras)", required = true)
            @RequestParam("uf") String uf,
            @Parameter(description = "Limite de procedimentos não faturados retornados (default 50)", required = false)
            @RequestParam(value = "limitNaoFaturados", required = false) Integer limitNaoFaturados
    ) {
        return ResponseEntity.ok(service.conciliacao(competencia, uf, limitNaoFaturados));
    }

    @GetMapping("/receita-por-competencia")
    @Operation(summary = "Série temporal de receita (faturamento) vs valor aprovado SIA-PA por competência")
    public ResponseEntity<SiaPaFinanceiroReceitaPorCompetenciaResponse> receitaPorCompetencia(
            @Parameter(description = "UF (2 letras)", required = true)
            @RequestParam("uf") String uf,
            @Parameter(description = "Competência inicial AAAAMM", required = true)
            @RequestParam("competenciaInicio") String competenciaInicio,
            @Parameter(description = "Competência final AAAAMM", required = true)
            @RequestParam("competenciaFim") String competenciaFim
    ) {
        return ResponseEntity.ok(service.receitaPorCompetencia(uf, competenciaInicio, competenciaFim));
    }

    @GetMapping("/procedimentos-nao-faturados")
    @Operation(summary = "Lista procedimentos com produção no SIA-PA mas sem faturamento interno")
    public ResponseEntity<Object> procedimentosNaoFaturados(
            @Parameter(description = "Competência AAAAMM", required = true)
            @RequestParam("competencia") String competencia,
            @Parameter(description = "UF (2 letras)", required = true)
            @RequestParam("uf") String uf,
            @Parameter(description = "Limite (default 50, máximo 200)", required = false)
            @RequestParam(value = "limit", required = false) Integer limit
    ) {
        // Reutiliza conciliação e retorna apenas o recorte de não faturados
        int lim = limit != null ? limit : 50;
        SiaPaFinanceiroIntegracaoResponse resp = service.conciliacao(competencia, uf, lim);
        return ResponseEntity.ok(resp.getProcedimentosNaoFaturados());
    }
}

