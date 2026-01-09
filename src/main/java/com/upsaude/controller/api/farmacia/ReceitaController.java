package com.upsaude.controller.api.farmacia;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upsaude.api.request.farmacia.ReceitaRequest;
import com.upsaude.api.response.farmacia.ReceitaResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.service.api.farmacia.ReceitaService;

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
@RequestMapping("/v1/receitas")
@Tag(name = "Receitas", description = "API para gerenciamento de Receitas Médicas")
@RequiredArgsConstructor
@Slf4j
public class ReceitaController {

    private final ReceitaService receitaService;

    @PostMapping
    @Operation(summary = "Criar nova receita", description = "Cria uma nova receita médica com seus itens")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Receita criada com sucesso",
                    content = @Content(schema = @Schema(implementation = ReceitaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ReceitaResponse> criar(@Valid @RequestBody ReceitaRequest request) {
        log.debug("REQUEST POST /v1/receitas - payload: {}", request);
        try {
            ReceitaResponse response = receitaService.criar(request);
            log.info("Receita criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar receita — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar receita — Path: /v1/receitas, Method: POST, payload: {}, Exception: {}",
                request, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter receita por ID", description = "Retorna uma receita específica pelo seu ID com todos os itens")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receita encontrada",
                    content = @Content(schema = @Schema(implementation = ReceitaResponse.class))),
            @ApiResponse(responseCode = "404", description = "Receita não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ReceitaResponse> obterPorId(
            @Parameter(description = "ID da receita", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/receitas/{}", id);
        try {
            ReceitaResponse response = receitaService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter receita — Path: /v1/receitas/{}, Method: GET, Exception: {}",
                id, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @GetMapping("/pacientes/{pacienteId}")
    @Operation(summary = "Listar receitas do paciente", description = "Retorna uma lista paginada de receitas de um paciente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de receitas retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ReceitaResponse>> listarPorPaciente(
            @Parameter(description = "ID do paciente", required = true)
            @PathVariable UUID pacienteId,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/receitas/pacientes/{} - pageable: {}", pacienteId, pageable);
        try {
            Page<ReceitaResponse> response = receitaService.listarPorPaciente(pacienteId, pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar receitas do paciente — Path: /v1/receitas/pacientes/{}, Method: GET, pageable: {}, Exception: {}",
                pacienteId, pageable, ex.getClass().getName(), ex);
            throw ex;
        }
    }
}

