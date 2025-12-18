package com.upsaude.controller.paciente;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upsaude.api.request.paciente.PacienteRequest;
import com.upsaude.api.response.paciente.PacienteResponse;
import com.upsaude.api.response.paciente.PacienteSimplificadoResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.paciente.PacienteService;

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
@RequestMapping("/v1/pacientes")
@Tag(name = "Pacientes", description = "API para gerenciamento de Pacientes")
@RequiredArgsConstructor
@Slf4j
public class PacienteController {

    private final PacienteService pacienteService;

    @PostMapping
    @Operation(summary = "Criar novo paciente", description = "Cria um novo paciente no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Paciente criado com sucesso",
                    content = @Content(schema = @Schema(implementation = PacienteResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<PacienteResponse> criar(@Valid @RequestBody PacienteRequest request) {
        log.debug("REQUEST POST /v1/pacientes - payload: {}", request);
        try {
            PacienteResponse response = pacienteService.criar(request);
            log.info("Paciente criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar paciente — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar paciente — Path: /v1/pacientes, Method: POST, payload: {}, Exception: {}",
                request, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @GetMapping({"", "/"})
    @Operation(summary = "Listar pacientes", description = "Retorna uma lista paginada de pacientes com apenas os dados básicos, sem relacionamentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pacientes retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<PacienteSimplificadoResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/pacientes - pageable: {}", pageable);
        try {
            Page<PacienteSimplificadoResponse> response = pacienteService.listarSimplificado(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar pacientes — Path: /v1/pacientes, Method: GET, pageable: {}, Exception: {}",
                pageable, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter paciente por ID", description = "Retorna um paciente específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente encontrado",
                    content = @Content(schema = @Schema(implementation = PacienteResponse.class))),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<PacienteResponse> obterPorId(
            @Parameter(description = "ID do paciente", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/pacientes/{}", id);
        try {
            PacienteResponse response = pacienteService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Paciente não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter paciente por ID — Path: /v1/pacientes/{}, Method: GET, ID: {}, Exception: {}",
                id, id, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @GetMapping("/completo")
    @Operation(summary = "Listar pacientes completos", description = "Retorna uma lista paginada de pacientes com todos os dados e relacionamentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pacientes completos retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<PacienteResponse>> listarCompleto(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/pacientes/completo - pageable: {}", pageable);
        try {
            Page<PacienteResponse> response = pacienteService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar pacientes completos — Path: /v1/pacientes/completo, Method: GET, pageable: {}, Exception: {}",
                pageable, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar paciente", description = "Atualiza um paciente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = PacienteResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<PacienteResponse> atualizar(
            @Parameter(description = "ID do paciente", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody PacienteRequest request) {
        log.debug("REQUEST PUT /v1/pacientes/{} - payload: {}", id, request);
        try {
            PacienteResponse response = pacienteService.atualizar(id, request);
            log.info("Paciente atualizado com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar paciente — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar paciente — Path: /v1/pacientes/{}, Method: PUT, ID: {}, payload: {}, Exception: {}",
                id, id, request, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir paciente", description = "Exclui (inativa) um paciente do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Paciente excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do paciente", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/pacientes/{}", id);
        try {
            pacienteService.excluir(id);
            log.info("Paciente excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Paciente não encontrado para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir paciente — Path: /v1/pacientes/{}, Method: DELETE, ID: {}, Exception: {}",
                id, id, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @PatchMapping("/{id}/inativar")
    @Operation(summary = "Inativar paciente", description = "Inativa um paciente no sistema (soft delete)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Paciente inativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado"),
            @ApiResponse(responseCode = "400", description = "Paciente já está inativo"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> inativar(
            @Parameter(description = "ID do paciente", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST PATCH /v1/pacientes/{}/inativar", id);
        try {
            pacienteService.inativar(id);
            log.info("Paciente inativado com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Paciente não encontrado para inativação — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inativar paciente — Path: /v1/pacientes/{}/inativar, Method: PATCH, ID: {}, Exception: {}",
                id, id, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}/permanente")
    @Operation(summary = "Deletar paciente permanentemente", description = "Remove permanentemente um paciente do banco de dados (hard delete)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Paciente deletado permanentemente com sucesso"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> deletarPermanentemente(
            @Parameter(description = "ID do paciente", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/pacientes/{}/permanente", id);
        try {
            pacienteService.deletarPermanentemente(id);
            log.info("Paciente deletado permanentemente com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Paciente não encontrado para exclusão permanente — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao deletar paciente permanentemente — Path: /v1/pacientes/{}/permanente, Method: DELETE, ID: {}, Exception: {}",
                id, id, ex.getClass().getName(), ex);
            throw ex;
        }
    }
}
