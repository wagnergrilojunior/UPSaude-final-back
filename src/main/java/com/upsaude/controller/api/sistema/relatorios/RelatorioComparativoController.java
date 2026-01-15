package com.upsaude.controller.api.sistema.relatorios;

import com.upsaude.api.request.sistema.relatorios.RelatorioComparativoRequest;
import com.upsaude.api.response.sistema.relatorios.RelatorioComparativoResponse;
import com.upsaude.service.api.sistema.relatorios.RelatorioComparativoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/v1/relatorios/comparativo")
@RequiredArgsConstructor
@Tag(name = "Relatórios Comparativos", description = "Relatórios comparativos entre períodos, estabelecimentos, médicos e especialidades")
public class RelatorioComparativoController {

    private final RelatorioComparativoService relatorioComparativoService;

    @PostMapping
    @Operation(summary = "Gera relatório comparativo entre dois períodos")
    public ResponseEntity<RelatorioComparativoResponse> gerarRelatorioComparativo(
            @Parameter(description = "Dados do relatório comparativo", required = true)
            @Valid @RequestBody RelatorioComparativoRequest request
    ) {
        log.debug("REQUEST POST /v1/relatorios/comparativo");
        return ResponseEntity.ok(relatorioComparativoService.gerarRelatorioComparativo(request));
    }
}
