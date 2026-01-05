package com.upsaude.controller.api.farmacia;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upsaude.api.request.farmacia.DispensacaoRequest;
import com.upsaude.api.response.farmacia.DispensacaoResponse;
import com.upsaude.api.response.farmacia.ReceitaResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.service.api.farmacia.DispensacaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/farmacias")
@Tag(name = "Farmácias", description = "API para operações de farmácia (dispensação de medicamentos)")
@RequiredArgsConstructor
@Slf4j
public class FarmaciaController {

    private final DispensacaoService dispensacaoService;

    @GetMapping("/{farmaciaId}/receitas-pendentes")
    @Operation(summary = "Listar receitas pendentes", description = "Retorna uma lista paginada de receitas pendentes de dispensação para a farmácia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de receitas pendentes retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Farmácia não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ReceitaResponse>> listarReceitasPendentes(
            @Parameter(description = "ID da farmácia", required = true)
            @PathVariable UUID farmaciaId,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/farmacias/{}/receitas-pendentes - pageable: {}", farmaciaId, pageable);
        try {
            Page<ReceitaResponse> response = dispensacaoService.listarReceitasPendentes(farmaciaId, pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar receitas pendentes — Path: /v1/farmacias/{}/receitas-pendentes, Method: GET, pageable: {}, Exception: {}",
                farmaciaId, pageable, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @PostMapping("/{farmaciaId}/dispensacoes")
    @Operation(summary = "Registrar dispensação", description = "Registra uma nova dispensação de medicamentos na farmácia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dispensação registrada com sucesso",
                    content = @Content(schema = @Schema(implementation = DispensacaoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Farmácia, paciente ou receita não encontrados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DispensacaoResponse> registrarDispensacao(
            @Parameter(description = "ID da farmácia", required = true)
            @PathVariable UUID farmaciaId,
            @Valid @RequestBody DispensacaoRequest request) {
        log.debug("REQUEST POST /v1/farmacias/{}/dispensacoes - payload: {}", farmaciaId, request);
        try {
            DispensacaoResponse response = dispensacaoService.registrarDispensacao(farmaciaId, request);
            log.info("Dispensação registrada com sucesso. ID: {}, Farmácia: {}", response.getId(), farmaciaId);
            return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao registrar dispensação — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao registrar dispensação — Path: /v1/farmacias/{}/dispensacoes, Method: POST, payload: {}, Exception: {}",
                farmaciaId, request, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @GetMapping("/{farmaciaId}/dispensacoes")
    @Operation(summary = "Listar dispensações", description = "Retorna uma lista paginada de dispensações realizadas na farmácia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de dispensações retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Farmácia não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<DispensacaoResponse>> listarDispensacoes(
            @Parameter(description = "ID da farmácia", required = true)
            @PathVariable UUID farmaciaId,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/farmacias/{}/dispensacoes - pageable: {}", farmaciaId, pageable);
        try {
            Page<DispensacaoResponse> response = dispensacaoService.listarDispensacoes(farmaciaId, pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar dispensações — Path: /v1/farmacias/{}/dispensacoes, Method: GET, pageable: {}, Exception: {}",
                farmaciaId, pageable, ex.getClass().getName(), ex);
            throw ex;
        }
    }
}

