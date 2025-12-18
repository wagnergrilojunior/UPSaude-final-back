package com.upsaude.controller.clinica.atendimento;

import com.upsaude.entity.clinica.atendimento.Atendimento;

import com.upsaude.api.request.clinica.atendimento.AtendimentoRequest;
import com.upsaude.api.response.clinica.atendimento.AtendimentoResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.clinica.atendimento.AtendimentoService;
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
@RequestMapping("/v1/atendimentos")
@Tag(name = "Atendimentos", description = "API para gerenciamento de Atendimentos")
@RequiredArgsConstructor
@Slf4j
public class AtendimentoController {

    private final AtendimentoService atendimentoService;

    @PostMapping
    @Operation(summary = "Criar novo atendimento", description = "Cria um novo atendimento no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Atendimento criado com sucesso",
                    content = @Content(schema = @Schema(implementation = AtendimentoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AtendimentoResponse> criar(@Valid @RequestBody AtendimentoRequest request) {
        log.debug("REQUEST POST /v1/atendimentos - payload: {}", request);
        try {
            AtendimentoResponse response = atendimentoService.criar(request);
            log.info("Atendimento criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar atendimento — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar atendimento — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar atendimentos", description = "Retorna uma lista paginada de atendimentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de atendimentos retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<AtendimentoResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/atendimentos - pageable: {}", pageable);
        try {
            Page<AtendimentoResponse> response = atendimentoService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar atendimentos — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Listar atendimentos por paciente", description = "Retorna uma lista paginada de atendimentos de um paciente específico, ordenados por data/hora decrescente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de atendimentos do paciente retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID do paciente inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<AtendimentoResponse>> listarPorPaciente(
            @Parameter(description = "ID do paciente", required = true)
            @PathVariable UUID pacienteId,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/atendimentos/paciente/{} - pageable: {}", pacienteId, pageable);
        try {
            Page<AtendimentoResponse> response = atendimentoService.listarPorPaciente(pacienteId, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar atendimentos por paciente — pacienteId: {}, mensagem: {}", pacienteId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar atendimentos por paciente — pacienteId: {}, pageable: {}", pacienteId, pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/profissional/{profissionalId}")
    @Operation(summary = "Listar atendimentos por profissional", description = "Retorna uma lista paginada de atendimentos realizados por um profissional específico, ordenados por data/hora decrescente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de atendimentos do profissional retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID do profissional inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<AtendimentoResponse>> listarPorProfissional(
            @Parameter(description = "ID do profissional de saúde", required = true)
            @PathVariable UUID profissionalId,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/atendimentos/profissional/{} - pageable: {}", profissionalId, pageable);
        try {
            Page<AtendimentoResponse> response = atendimentoService.listarPorProfissional(profissionalId, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar atendimentos por profissional — profissionalId: {}, mensagem: {}", profissionalId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar atendimentos por profissional — profissionalId: {}, pageable: {}", profissionalId, pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter atendimento por ID", description = "Retorna um atendimento específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atendimento encontrado",
                    content = @Content(schema = @Schema(implementation = AtendimentoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Atendimento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AtendimentoResponse> obterPorId(
            @Parameter(description = "ID do atendimento", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/atendimentos/{}", id);
        try {
            AtendimentoResponse response = atendimentoService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Atendimento não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter atendimento por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar atendimento", description = "Atualiza um atendimento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atendimento atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = AtendimentoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Atendimento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AtendimentoResponse> atualizar(
            @Parameter(description = "ID do atendimento", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody AtendimentoRequest request) {
        log.debug("REQUEST PUT /v1/atendimentos/{} - payload: {}", id, request);
        try {
            AtendimentoResponse response = atendimentoService.atualizar(id, request);
            log.info("Atendimento atualizado com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar atendimento — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar atendimento — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir atendimento", description = "Exclui (desativa) um atendimento do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Atendimento excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Atendimento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do atendimento", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/atendimentos/{}", id);
        try {
            atendimentoService.excluir(id);
            log.info("Atendimento excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Atendimento não encontrado para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir atendimento — ID: {}", id, ex);
            throw ex;
        }
    }
}
