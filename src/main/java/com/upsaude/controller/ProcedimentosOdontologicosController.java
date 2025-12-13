package com.upsaude.controller;

import com.upsaude.api.request.ProcedimentosOdontologicosRequest;
import com.upsaude.api.response.ProcedimentosOdontologicosResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.ProcedimentosOdontologicosService;
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

@RestController
@RequestMapping("/v1/procedimentos-odontologicos")
@Tag(name = "Procedimentos Odontológicos", description = "API para gerenciamento de Procedimentos Odontológicos")
@RequiredArgsConstructor
@Slf4j
public class ProcedimentosOdontologicosController {

    private final ProcedimentosOdontologicosService procedimentosOdontologicosService;

    @PostMapping
    @Operation(summary = "Criar novo procedimento odontológico", description = "Cria um novo procedimento odontológico no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Procedimento odontológico criado com sucesso",
                    content = @Content(schema = @Schema(implementation = ProcedimentosOdontologicosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ProcedimentosOdontologicosResponse> criar(@Valid @RequestBody ProcedimentosOdontologicosRequest request) {
        log.debug("REQUEST POST /v1/procedimentos-odontologicos - payload: {}", request);
        try {
            ProcedimentosOdontologicosResponse response = procedimentosOdontologicosService.criar(request);
            log.info("Procedimento odontológico criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar procedimento odontológico — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar procedimento odontológico — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar procedimentos odontológicos", description = "Retorna uma lista paginada de procedimentos odontológicos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de procedimentos odontológicos retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ProcedimentosOdontologicosResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable,
            @RequestParam(required = false) String codigo,
            @RequestParam(required = false) String nome) {
        log.debug("REQUEST GET /v1/procedimentos-odontologicos - pageable: {}, codigo: {}, nome: {}", pageable, codigo, nome);
        try {
            Page<ProcedimentosOdontologicosResponse> response = procedimentosOdontologicosService.listar(pageable, codigo, nome);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar procedimentos odontológicos — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter procedimento odontológico por ID", description = "Retorna um procedimento odontológico específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Procedimento odontológico encontrado",
                    content = @Content(schema = @Schema(implementation = ProcedimentosOdontologicosResponse.class))),
            @ApiResponse(responseCode = "404", description = "Procedimento odontológico não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ProcedimentosOdontologicosResponse> obterPorId(
            @Parameter(description = "ID do procedimento odontológico", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/procedimentos-odontologicos/{}", id);
        try {
            ProcedimentosOdontologicosResponse response = procedimentosOdontologicosService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Falha ao obter procedimento odontológico por ID — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter procedimento odontológico por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar procedimento odontológico", description = "Atualiza um procedimento odontológico existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Procedimento odontológico atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = ProcedimentosOdontologicosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Procedimento odontológico não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ProcedimentosOdontologicosResponse> atualizar(
            @Parameter(description = "ID do procedimento odontológico", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody ProcedimentosOdontologicosRequest request) {
        log.debug("REQUEST PUT /v1/procedimentos-odontologicos/{} - payload: {}", id, request);
        try {
            ProcedimentosOdontologicosResponse response = procedimentosOdontologicosService.atualizar(id, request);
            log.info("Procedimento odontológico atualizado com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar procedimento odontológico — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar procedimento odontológico — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir procedimento odontológico", description = "Exclui (desativa) um procedimento odontológico do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Procedimento odontológico excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Procedimento odontológico não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do procedimento odontológico", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/procedimentos-odontologicos/{}", id);
        try {
            procedimentosOdontologicosService.excluir(id);
            log.info("Procedimento odontológico excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Falha ao excluir procedimento odontológico — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir procedimento odontológico — ID: {}", id, ex);
            throw ex;
        }
    }
}
