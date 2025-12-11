package com.upsaude.controller;

import com.upsaude.api.request.RelatorioEstatisticasRequest;
import com.upsaude.api.response.RelatorioEstatisticasResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.service.RelatoriosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
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
        log.debug("REQUEST POST /v1/relatorios/estatisticas - payload: {}", request);
        try {
            RelatorioEstatisticasResponse response = relatoriosService.gerarEstatisticas(request);
            log.info("Relatório de estatísticas gerado com sucesso");
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao gerar relatório de estatísticas — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao gerar relatório de estatísticas — payload: {}", request, ex);
            throw ex;
        }
    }
}
