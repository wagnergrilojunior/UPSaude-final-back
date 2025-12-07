package com.upsaude.controller;

import com.upsaude.api.request.DoencasPacienteRequest;
import com.upsaude.api.request.DoencasPacienteSimplificadoRequest;
import com.upsaude.api.response.DoencasPacienteResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.DoencasPacienteService;
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
 * Controlador REST para operações relacionadas a Doenças de Paciente.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/doencas-paciente")
@Tag(name = "Doenças de Paciente", description = "API para gerenciamento de Doenças de Paciente")
@RequiredArgsConstructor
@Slf4j
public class DoencasPacienteController {

    private final DoencasPacienteService doencasPacienteService;

    @PostMapping
    @Operation(summary = "Criar novo registro de doença do paciente", description = "Cria um novo registro de doença do paciente no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registro de doença do paciente criado com sucesso",
                    content = @Content(schema = @Schema(implementation = DoencasPacienteResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DoencasPacienteResponse> criar(@Valid @RequestBody DoencasPacienteRequest request) {
        log.debug("REQUEST POST /v1/doencas-paciente - payload: {}", request);
        try {
            DoencasPacienteResponse response = doencasPacienteService.criar(request);
            log.info("Registro de doença do paciente criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar registro de doença do paciente — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar registro de doença do paciente — payload: {}", request, ex);
            throw ex;
        }
    }

    @PostMapping("/simplificado")
    @Operation(summary = "Criar registro de doença do paciente simplificado", description = "Cria um novo registro de doença do paciente informando apenas paciente, tenant e doença. Os demais campos são criados com valores padrão")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registro de doença do paciente criado com sucesso",
                    content = @Content(schema = @Schema(implementation = DoencasPacienteResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Paciente, tenant ou doença não encontrados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DoencasPacienteResponse> criarSimplificado(@Valid @RequestBody DoencasPacienteSimplificadoRequest request) {
        log.debug("REQUEST POST /v1/doencas-paciente/simplificado - payload: {}", request);
        try {
            DoencasPacienteResponse response = doencasPacienteService.criarSimplificado(request);
            log.info("Registro de doença do paciente criado com sucesso (simplificado). ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao criar registro de doença do paciente simplificado — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar registro de doença do paciente simplificado — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar registros de doenças do paciente", description = "Retorna uma lista paginada de registros de doenças do paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de registros retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<DoencasPacienteResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/doencas-paciente - pageable: {}", pageable);
        try {
            Page<DoencasPacienteResponse> response = doencasPacienteService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar registros de doenças do paciente — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Listar doenças por paciente", description = "Retorna uma lista paginada de doenças de um paciente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de doenças do paciente retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID do paciente inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<DoencasPacienteResponse>> listarPorPaciente(
            @Parameter(description = "ID do paciente", required = true)
            @PathVariable UUID pacienteId,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/doencas-paciente/paciente/{} - pageable: {}", pacienteId, pageable);
        try {
            Page<DoencasPacienteResponse> response = doencasPacienteService.listarPorPaciente(pacienteId, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar doenças por paciente — pacienteId: {}, mensagem: {}", pacienteId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar doenças por paciente — pacienteId: {}, pageable: {}", pacienteId, pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/doenca/{doencaId}")
    @Operation(summary = "Listar pacientes por doença", description = "Retorna uma lista paginada de pacientes que têm uma doença específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pacientes retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID da doença inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<DoencasPacienteResponse>> listarPorDoenca(
            @Parameter(description = "ID da doença", required = true)
            @PathVariable UUID doencaId,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/doencas-paciente/doenca/{} - pageable: {}", doencaId, pageable);
        try {
            Page<DoencasPacienteResponse> response = doencasPacienteService.listarPorDoenca(doencaId, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar pacientes por doença — doencaId: {}, mensagem: {}", doencaId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar pacientes por doença — doencaId: {}, pageable: {}", doencaId, pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter registro de doença do paciente por ID", description = "Retorna um registro específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro encontrado",
                    content = @Content(schema = @Schema(implementation = DoencasPacienteResponse.class))),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DoencasPacienteResponse> obterPorId(
            @Parameter(description = "ID do registro", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/doencas-paciente/{}", id);
        try {
            DoencasPacienteResponse response = doencasPacienteService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Registro de doença do paciente não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter registro de doença do paciente por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar registro de doença do paciente", description = "Atualiza um registro existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = DoencasPacienteResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DoencasPacienteResponse> atualizar(
            @Parameter(description = "ID do registro", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody DoencasPacienteRequest request) {
        log.debug("REQUEST PUT /v1/doencas-paciente/{} - payload: {}", id, request);
        try {
            DoencasPacienteResponse response = doencasPacienteService.atualizar(id, request);
            log.info("Registro de doença do paciente atualizado com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar registro de doença do paciente — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar registro de doença do paciente — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir registro de doença do paciente", description = "Exclui (desativa) um registro do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Registro excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do registro", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/doencas-paciente/{}", id);
        try {
            doencasPacienteService.excluir(id);
            log.info("Registro de doença do paciente excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Registro de doença do paciente não encontrado para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir registro de doença do paciente — ID: {}", id, ex);
            throw ex;
        }
    }
}
