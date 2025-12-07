package com.upsaude.controller;

import com.upsaude.api.request.AlergiasPacienteRequest;
import com.upsaude.api.request.AlergiasPacienteSimplificadoRequest;
import com.upsaude.api.response.AlergiasPacienteResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.AlergiasPacienteService;
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
 * Controlador REST para operações relacionadas a Alergias de Paciente.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/alergias-paciente")
@Tag(name = "Alergias de Paciente", description = "API para gerenciamento de Alergias de Paciente")
@RequiredArgsConstructor
@Slf4j
public class AlergiasPacienteController {

    private final AlergiasPacienteService alergiasPacienteService;

    @PostMapping
    @Operation(summary = "Criar nova alergia de paciente", description = "Cria uma nova alergia de paciente no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alergia de paciente criada com sucesso",
                    content = @Content(schema = @Schema(implementation = AlergiasPacienteResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AlergiasPacienteResponse> criar(@Valid @RequestBody AlergiasPacienteRequest request) {
        log.debug("REQUEST POST /v1/alergias-paciente - payload: {}", request);
        try {
            AlergiasPacienteResponse response = alergiasPacienteService.criar(request);
            log.info("Alergia de paciente criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar alergia de paciente — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar alergia de paciente — payload: {}", request, ex);
            throw ex;
        }
    }

    @PostMapping("/simplificado")
    @Operation(summary = "Criar alergia de paciente simplificada", description = "Cria uma nova alergia de paciente informando apenas paciente, tenant e alergia. Os demais campos são criados com valores padrão")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alergia de paciente criada com sucesso",
                    content = @Content(schema = @Schema(implementation = AlergiasPacienteResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Paciente, tenant ou alergia não encontrados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AlergiasPacienteResponse> criarSimplificado(@Valid @RequestBody AlergiasPacienteSimplificadoRequest request) {
        log.debug("REQUEST POST /v1/alergias-paciente/simplificado - payload: {}", request);
        try {
            AlergiasPacienteResponse response = alergiasPacienteService.criarSimplificado(request);
            log.info("Alergia de paciente criada com sucesso (simplificado). ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao criar alergia de paciente simplificada — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar alergia de paciente simplificada — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar alergias de paciente", description = "Retorna uma lista paginada de alergias de paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de alergias de paciente retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<AlergiasPacienteResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/alergias-paciente - pageable: {}", pageable);
        try {
            Page<AlergiasPacienteResponse> response = alergiasPacienteService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar alergias de paciente — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter alergia de paciente por ID", description = "Retorna uma alergia de paciente específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alergia de paciente encontrada",
                    content = @Content(schema = @Schema(implementation = AlergiasPacienteResponse.class))),
            @ApiResponse(responseCode = "404", description = "Alergia de paciente não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AlergiasPacienteResponse> obterPorId(
            @Parameter(description = "ID da alergia de paciente", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/alergias-paciente/{}", id);
        try {
            AlergiasPacienteResponse response = alergiasPacienteService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Alergia de paciente não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter alergia de paciente por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar alergia de paciente", description = "Atualiza uma alergia de paciente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alergia de paciente atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = AlergiasPacienteResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Alergia de paciente não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AlergiasPacienteResponse> atualizar(
            @Parameter(description = "ID da alergia de paciente", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody AlergiasPacienteRequest request) {
        log.debug("REQUEST PUT /v1/alergias-paciente/{} - payload: {}", id, request);
        try {
            AlergiasPacienteResponse response = alergiasPacienteService.atualizar(id, request);
            log.info("Alergia de paciente atualizada com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar alergia de paciente — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar alergia de paciente — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir alergia de paciente", description = "Exclui (desativa) uma alergia de paciente do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Alergia de paciente excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Alergia de paciente não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da alergia de paciente", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/alergias-paciente/{}", id);
        try {
            alergiasPacienteService.excluir(id);
            log.info("Alergia de paciente excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Alergia de paciente não encontrada para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir alergia de paciente — ID: {}", id, ex);
            throw ex;
        }
    }
}
