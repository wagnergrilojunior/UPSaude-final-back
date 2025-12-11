package com.upsaude.controller;

import com.upsaude.api.request.PreNatalRequest;
import com.upsaude.api.response.PreNatalResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.PreNatalService;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/pre-natal")
@Tag(name = "Pré-Natal", description = "API para gerenciamento de acompanhamento Pré-Natal")
@RequiredArgsConstructor
@Slf4j
public class PreNatalController {

    private final PreNatalService preNatalService;

    @PostMapping
    @Operation(summary = "Criar novo pré-natal", description = "Cria um novo acompanhamento pré-natal no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pré-natal criado com sucesso",
                    content = @Content(schema = @Schema(implementation = PreNatalResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<PreNatalResponse> criar(@Valid @RequestBody PreNatalRequest request) {
        log.debug("REQUEST POST /v1/pre-natal - payload: {}", request);
        try {
            PreNatalResponse response = preNatalService.criar(request);
            log.info("Pré-natal criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar pré-natal — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar pré-natal — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar pré-natais", description = "Retorna uma lista paginada de pré-natais")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pré-natais retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<PreNatalResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/pre-natal - pageable: {}", pageable);
        try {
            Page<PreNatalResponse> response = preNatalService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar pré-natais — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter pré-natal por ID", description = "Retorna um pré-natal específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pré-natal encontrado",
                    content = @Content(schema = @Schema(implementation = PreNatalResponse.class))),
            @ApiResponse(responseCode = "404", description = "Pré-natal não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<PreNatalResponse> obterPorId(
            @Parameter(description = "ID do pré-natal", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/pre-natal/{}", id);
        try {
            PreNatalResponse response = preNatalService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Pré-natal não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter pré-natal por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @GetMapping("/estabelecimento/{estabelecimentoId}")
    @Operation(summary = "Listar pré-natais por estabelecimento", description = "Retorna uma lista paginada de pré-natais por estabelecimento")
    public ResponseEntity<Page<PreNatalResponse>> listarPorEstabelecimento(
            @PathVariable UUID estabelecimentoId,
            Pageable pageable) {
        log.debug("REQUEST GET /v1/pre-natal/estabelecimento/{} - pageable: {}", estabelecimentoId, pageable);
        try {
            Page<PreNatalResponse> response = preNatalService.listarPorEstabelecimento(estabelecimentoId, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar pré-natais por estabelecimento — estabelecimentoId: {}, mensagem: {}", estabelecimentoId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar pré-natais por estabelecimento — estabelecimentoId: {}, pageable: {}", estabelecimentoId, pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Listar pré-natais por paciente", description = "Retorna uma lista de pré-natais por paciente")
    public ResponseEntity<List<PreNatalResponse>> listarPorPaciente(
            @PathVariable UUID pacienteId) {
        log.debug("REQUEST GET /v1/pre-natal/paciente/{}", pacienteId);
        try {
            List<PreNatalResponse> response = preNatalService.listarPorPaciente(pacienteId);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar pré-natais por paciente — pacienteId: {}, mensagem: {}", pacienteId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar pré-natais por paciente — pacienteId: {}", pacienteId, ex);
            throw ex;
        }
    }

    @GetMapping("/em-acompanhamento/{estabelecimentoId}")
    @Operation(summary = "Listar pré-natais em acompanhamento", description = "Retorna uma lista paginada de pré-natais em acompanhamento")
    public ResponseEntity<Page<PreNatalResponse>> listarEmAcompanhamento(
            @PathVariable UUID estabelecimentoId,
            Pageable pageable) {
        log.debug("REQUEST GET /v1/pre-natal/em-acompanhamento/{} - pageable: {}", estabelecimentoId, pageable);
        try {
            Page<PreNatalResponse> response = preNatalService.listarEmAcompanhamento(estabelecimentoId, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar pré-natais em acompanhamento — estabelecimentoId: {}, mensagem: {}", estabelecimentoId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar pré-natais em acompanhamento — estabelecimentoId: {}, pageable: {}", estabelecimentoId, pageable, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar pré-natal", description = "Atualiza um pré-natal existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pré-natal atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = PreNatalResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Pré-natal não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<PreNatalResponse> atualizar(
            @Parameter(description = "ID do pré-natal", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody PreNatalRequest request) {
        log.debug("REQUEST PUT /v1/pre-natal/{} - payload: {}", id, request);
        try {
            PreNatalResponse response = preNatalService.atualizar(id, request);
            log.info("Pré-natal atualizado com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar pré-natal — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar pré-natal — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir pré-natal", description = "Exclui (desativa) um pré-natal do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pré-natal excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pré-natal não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do pré-natal", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/pre-natal/{}", id);
        try {
            preNatalService.excluir(id);
            log.info("Pré-natal excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Pré-natal não encontrado para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir pré-natal — ID: {}", id, ex);
            throw ex;
        }
    }
}
