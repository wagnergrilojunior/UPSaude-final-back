package com.upsaude.controller;

import com.upsaude.api.request.TratamentosProcedimentosRequest;
import com.upsaude.api.response.TratamentosProcedimentosResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.TratamentosProcedimentosService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controlador REST para operações relacionadas a Tratamentos e Procedimentos.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/tratamentos-procedimentos")
@Tag(name = "Tratamentos e Procedimentos", description = "API para gerenciamento de Tratamentos e Procedimentos")
@RequiredArgsConstructor
@Slf4j
public class TratamentosProcedimentosController {

    private final TratamentosProcedimentosService tratamentosProcedimentosService;

    @PostMapping
    @Operation(summary = "Criar novo tratamento e procedimento", description = "Cria um novo tratamento e procedimento no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tratamento e procedimento criado com sucesso",
                    content = @Content(schema = @Schema(implementation = TratamentosProcedimentosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<TratamentosProcedimentosResponse> criar(@Valid @RequestBody TratamentosProcedimentosRequest request) {
        log.debug("REQUEST POST /v1/tratamentos-procedimentos - payload: {}", request);
        try {
            TratamentosProcedimentosResponse response = tratamentosProcedimentosService.criar(request);
            log.info("Tratamento e procedimento criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar tratamento e procedimento — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar tratamento e procedimento — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar tratamentos e procedimentos", description = "Retorna uma lista paginada de tratamentos e procedimentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tratamentos e procedimentos retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<TratamentosProcedimentosResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/tratamentos-procedimentos - pageable: {}", pageable);
        try {
            Page<TratamentosProcedimentosResponse> response = tratamentosProcedimentosService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar tratamentos e procedimentos — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter tratamento e procedimento por ID", description = "Retorna um tratamento e procedimento específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tratamento e procedimento encontrado",
                    content = @Content(schema = @Schema(implementation = TratamentosProcedimentosResponse.class))),
            @ApiResponse(responseCode = "404", description = "Tratamento e procedimento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<TratamentosProcedimentosResponse> obterPorId(
            @Parameter(description = "ID do tratamento e procedimento", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/tratamentos-procedimentos/{}", id);
        try {
            TratamentosProcedimentosResponse response = tratamentosProcedimentosService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Falha ao obter tratamento e procedimento por ID — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter tratamento e procedimento por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar tratamento e procedimento", description = "Atualiza um tratamento e procedimento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tratamento e procedimento atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = TratamentosProcedimentosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Tratamento e procedimento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<TratamentosProcedimentosResponse> atualizar(
            @Parameter(description = "ID do tratamento e procedimento", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody TratamentosProcedimentosRequest request) {
        log.debug("REQUEST PUT /v1/tratamentos-procedimentos/{} - payload: {}", id, request);
        try {
            TratamentosProcedimentosResponse response = tratamentosProcedimentosService.atualizar(id, request);
            log.info("Tratamento e procedimento atualizado com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar tratamento e procedimento — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar tratamento e procedimento — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir tratamento e procedimento", description = "Exclui (desativa) um tratamento e procedimento do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tratamento e procedimento excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tratamento e procedimento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do tratamento e procedimento", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/tratamentos-procedimentos/{}", id);
        try {
            tratamentosProcedimentosService.excluir(id);
            log.info("Tratamento e procedimento excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Falha ao excluir tratamento e procedimento — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir tratamento e procedimento — ID: {}", id, ex);
            throw ex;
        }
    }
}

