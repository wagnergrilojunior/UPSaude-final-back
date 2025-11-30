package com.upsaude.controller;

import com.upsaude.api.request.RelatorioEstatisticasRequest;
import com.upsaude.api.response.RelatorioEstatisticasResponse;
import com.upsaude.service.RelatoriosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para operações relacionadas a Relatórios e Estatísticas.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/relatorios")
@Tag(name = "Relatórios", description = "API para geração de relatórios e estatísticas")
@RequiredArgsConstructor
public class RelatoriosController {

    private final RelatoriosService relatoriosService;

    @PostMapping("/estatisticas")
    @Operation(summary = "Gerar relatório de estatísticas", description = "Gera um relatório com estatísticas gerais do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso",
                    content = @Content(schema = @Schema(implementation = RelatorioEstatisticasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<RelatorioEstatisticasResponse> gerarEstatisticas(
            @Valid @RequestBody RelatorioEstatisticasRequest request) {
        RelatorioEstatisticasResponse response = relatoriosService.gerarEstatisticas(request);
        return ResponseEntity.ok(response);
    }
}

