package com.upsaude.controller.api.sia;

import com.upsaude.api.response.sia.relatorios.SiaPaRelatorioProducaoResponse;
import com.upsaude.api.response.sia.relatorios.SiaPaRelatorioTopCidResponse;
import com.upsaude.api.response.sia.relatorios.SiaPaRelatorioTopProcedimentosResponse;
import com.upsaude.service.api.sia.SiaPaRelatorioService;
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
@RequestMapping("/v1/sia/relatorios")
@RequiredArgsConstructor
@Tag(name = "SIA-SUS Relatórios", description = "Relatórios gerenciais baseados em SIA-PA (produção, top rankings, tendência)")
public class SiaPaRelatorioController {

    private final SiaPaRelatorioService service;

    @GetMapping("/producao-mensal")
    @Operation(summary = "Relatório de produção mensal (tendência) por UF e período")
    public ResponseEntity<SiaPaRelatorioProducaoResponse> producaoMensal(
            @Parameter(description = "UF (2 letras). Se omitido, tenta inferir pelo tenant", required = false)
            @RequestParam(value = "uf", required = false) String uf,
            @Parameter(description = "Competência inicial (AAAAMM)", required = true)
            @RequestParam("competenciaInicio") String competenciaInicio,
            @Parameter(description = "Competência final (AAAAMM)", required = true)
            @RequestParam("competenciaFim") String competenciaFim
    ) {
        log.debug("REQUEST GET /v1/sia/relatorios/producao-mensal?uf={}&competenciaInicio={}&competenciaFim={}",
                uf, competenciaInicio, competenciaFim);
        return ResponseEntity.ok(service.gerarRelatorioProducaoMensal(uf, competenciaInicio, competenciaFim));
    }

    @GetMapping("/top-procedimentos")
    @Operation(summary = "Top procedimentos por UF e competência")
    public ResponseEntity<SiaPaRelatorioTopProcedimentosResponse> topProcedimentos(
            @Parameter(description = "UF (2 letras). Se omitido, tenta inferir pelo tenant", required = false)
            @RequestParam(value = "uf", required = false) String uf,
            @Parameter(description = "Competência (AAAAMM)", required = true)
            @RequestParam("competencia") String competencia,
            @Parameter(description = "Limite (default 10, máximo 100)", required = false)
            @RequestParam(value = "limit", required = false) Integer limit
    ) {
        log.debug("REQUEST GET /v1/sia/relatorios/top-procedimentos?uf={}&competencia={}&limit={}", uf, competencia, limit);
        return ResponseEntity.ok(service.gerarTopProcedimentos(uf, competencia, limit));
    }

    @GetMapping("/top-cid")
    @Operation(summary = "Top CID por UF e competência")
    public ResponseEntity<SiaPaRelatorioTopCidResponse> topCid(
            @Parameter(description = "UF (2 letras). Se omitido, tenta inferir pelo tenant", required = false)
            @RequestParam(value = "uf", required = false) String uf,
            @Parameter(description = "Competência (AAAAMM)", required = true)
            @RequestParam("competencia") String competencia,
            @Parameter(description = "Limite (default 10, máximo 100)", required = false)
            @RequestParam(value = "limit", required = false) Integer limit
    ) {
        log.debug("REQUEST GET /v1/sia/relatorios/top-cid?uf={}&competencia={}&limit={}", uf, competencia, limit);
        return ResponseEntity.ok(service.gerarTopCid(uf, competencia, limit));
    }
}

