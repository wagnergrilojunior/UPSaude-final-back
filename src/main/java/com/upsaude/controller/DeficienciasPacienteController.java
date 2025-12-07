package com.upsaude.controller;

import com.upsaude.api.request.DeficienciasPacienteRequest;
import com.upsaude.api.request.DeficienciasPacienteSimplificadoRequest;
import com.upsaude.api.response.DeficienciasPacienteResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.DeficienciasPacienteService;
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
 * Controlador REST para operações relacionadas a Deficiências de Paciente.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/deficiencias-paciente")
@Tag(name = "Deficiências de Paciente", description = "API para gerenciamento de Deficiências de Paciente")
@RequiredArgsConstructor
@Slf4j
public class DeficienciasPacienteController {

    private final DeficienciasPacienteService deficienciasPacienteService;

    @PostMapping
    @Operation(summary = "Criar nova ligação paciente-deficiência", description = "Cria uma nova ligação entre paciente e deficiência no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ligação paciente-deficiência criada com sucesso",
                    content = @Content(schema = @Schema(implementation = DeficienciasPacienteResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DeficienciasPacienteResponse> criar(@Valid @RequestBody DeficienciasPacienteRequest request) {
        log.debug("REQUEST POST /v1/deficiencias-paciente - payload: {}", request);
        try {
            DeficienciasPacienteResponse response = deficienciasPacienteService.criar(request);
            log.info("Ligação paciente-deficiência criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar ligação paciente-deficiência — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar ligação paciente-deficiência — payload: {}", request, ex);
            throw ex;
        }
    }

    @PostMapping("/simplificado")
    @Operation(summary = "Criar ligação paciente-deficiência simplificada", description = "Cria uma nova ligação entre paciente e deficiência informando apenas paciente, tenant e deficiência. Os demais campos são criados com valores padrão")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ligação paciente-deficiência criada com sucesso",
                    content = @Content(schema = @Schema(implementation = DeficienciasPacienteResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Paciente, tenant ou deficiência não encontrados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DeficienciasPacienteResponse> criarSimplificado(@Valid @RequestBody DeficienciasPacienteSimplificadoRequest request) {
        log.debug("REQUEST POST /v1/deficiencias-paciente/simplificado - payload: {}", request);
        try {
            DeficienciasPacienteResponse response = deficienciasPacienteService.criarSimplificado(request);
            log.info("Ligação paciente-deficiência criada com sucesso (simplificado). ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao criar ligação paciente-deficiência simplificada — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar ligação paciente-deficiência simplificada — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar ligações paciente-deficiência", description = "Retorna uma lista paginada de ligações paciente-deficiência")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ligações paciente-deficiência retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<DeficienciasPacienteResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/deficiencias-paciente - pageable: {}", pageable);
        try {
            Page<DeficienciasPacienteResponse> response = deficienciasPacienteService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar ligações paciente-deficiência — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter ligação paciente-deficiência por ID", description = "Retorna uma ligação paciente-deficiência específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ligação paciente-deficiência encontrada",
                    content = @Content(schema = @Schema(implementation = DeficienciasPacienteResponse.class))),
            @ApiResponse(responseCode = "404", description = "Ligação paciente-deficiência não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DeficienciasPacienteResponse> obterPorId(
            @Parameter(description = "ID da ligação paciente-deficiência", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/deficiencias-paciente/{}", id);
        try {
            DeficienciasPacienteResponse response = deficienciasPacienteService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Ligação paciente-deficiência não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter ligação paciente-deficiência por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar ligação paciente-deficiência", description = "Atualiza uma ligação paciente-deficiência existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ligação paciente-deficiência atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = DeficienciasPacienteResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Ligação paciente-deficiência não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DeficienciasPacienteResponse> atualizar(
            @Parameter(description = "ID da ligação paciente-deficiência", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody DeficienciasPacienteRequest request) {
        log.debug("REQUEST PUT /v1/deficiencias-paciente/{} - payload: {}", id, request);
        try {
            DeficienciasPacienteResponse response = deficienciasPacienteService.atualizar(id, request);
            log.info("Ligação paciente-deficiência atualizada com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar ligação paciente-deficiência — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar ligação paciente-deficiência — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir ligação paciente-deficiência", description = "Exclui (desativa) uma ligação paciente-deficiência do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ligação paciente-deficiência excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ligação paciente-deficiência não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da ligação paciente-deficiência", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/deficiencias-paciente/{}", id);
        try {
            deficienciasPacienteService.excluir(id);
            log.info("Ligação paciente-deficiência excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Ligação paciente-deficiência não encontrada para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir ligação paciente-deficiência — ID: {}", id, ex);
            throw ex;
        }
    }
}
