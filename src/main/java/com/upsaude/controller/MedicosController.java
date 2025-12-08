package com.upsaude.controller;

import com.upsaude.api.request.MedicosRequest;
import com.upsaude.api.response.MedicosResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.MedicosService;
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
 * Controlador REST para operações relacionadas a Médicos.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/medicos")
@Tag(name = "Médicos", description = "API para gerenciamento de Médicos")
@RequiredArgsConstructor
@Slf4j
public class MedicosController {

    private final MedicosService medicosService;

    @PostMapping
    @Operation(summary = "Criar novo médico", description = "Cria um novo médico no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Médico criado com sucesso",
                    content = @Content(schema = @Schema(implementation = MedicosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MedicosResponse> criar(@Valid @RequestBody MedicosRequest request) {
        log.debug("REQUEST POST /v1/medicos - payload: {}", request);
        try {
            MedicosResponse response = medicosService.criar(request);
            log.info("Médico criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar médico — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar médico — Path: /v1/medicos, Method: POST, payload: {}, Exception: {}", 
                request, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar médicos", description = "Retorna uma lista paginada de médicos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de médicos retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<MedicosResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/medicos - pageable: {}", pageable);
        try {
            Page<MedicosResponse> response = medicosService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar médicos — Path: /v1/medicos, Method: GET, pageable: {}, Exception: {}", 
                pageable, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter médico por ID", description = "Retorna um médico específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Médico encontrado",
                    content = @Content(schema = @Schema(implementation = MedicosResponse.class))),
            @ApiResponse(responseCode = "404", description = "Médico não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MedicosResponse> obterPorId(
            @Parameter(description = "ID do médico", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/medicos/{}", id);
        try {
            MedicosResponse response = medicosService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Médico não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter médico por ID — Path: /v1/medicos/{}, Method: GET, ID: {}, Exception: {}", 
                id, id, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar médico", description = "Atualiza um médico existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Médico atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = MedicosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Médico não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MedicosResponse> atualizar(
            @Parameter(description = "ID do médico", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody MedicosRequest request) {
        log.debug("REQUEST PUT /v1/medicos/{} - payload: {}", id, request);
        try {
            MedicosResponse response = medicosService.atualizar(id, request);
            log.info("Médico atualizado com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar médico — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar médico — Path: /v1/medicos/{}, Method: PUT, ID: {}, payload: {}, Exception: {}", 
                id, id, request, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir médico", description = "Exclui (desativa) um médico do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Médico excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Médico não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do médico", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/medicos/{}", id);
        try {
            medicosService.excluir(id);
            log.info("Médico excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Médico não encontrado para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir médico — Path: /v1/medicos/{}, Method: DELETE, ID: {}, Exception: {}", 
                id, id, ex.getClass().getName(), ex);
            throw ex;
        }
    }
}
