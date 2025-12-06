package com.upsaude.controller;

import com.upsaude.api.request.EquipeSaudeRequest;
import com.upsaude.api.response.EquipeSaudeResponse;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.EquipeSaudeService;
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
 * Controlador REST para operações relacionadas a Equipes de Saúde.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/equipes-saude")
@Tag(name = "Equipes de Saúde", description = "API para gerenciamento de Equipes de Saúde")
@RequiredArgsConstructor
@Slf4j
public class EquipeSaudeController {

    private final EquipeSaudeService equipeSaudeService;

    @PostMapping
    @Operation(summary = "Criar nova equipe de saúde", description = "Cria uma nova equipe de saúde no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Equipe de saúde criada com sucesso",
                    content = @Content(schema = @Schema(implementation = EquipeSaudeResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EquipeSaudeResponse> criar(@Valid @RequestBody EquipeSaudeRequest request) {
        log.debug("REQUEST POST /v1/equipes-saude - payload: {}", request);
        try {
            EquipeSaudeResponse response = equipeSaudeService.criar(request);
            log.info("Equipe de saúde criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar equipe de saúde — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar equipe de saúde — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar equipes de saúde", description = "Retorna uma lista paginada de equipes de saúde")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de equipes retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<EquipeSaudeResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/equipes-saude - pageable: {}", pageable);
        try {
            Page<EquipeSaudeResponse> response = equipeSaudeService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar equipes de saúde — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/estabelecimento/{estabelecimentoId}")
    @Operation(summary = "Listar equipes por estabelecimento", description = "Retorna uma lista paginada de equipes de um estabelecimento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de equipes retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID do estabelecimento inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<EquipeSaudeResponse>> listarPorEstabelecimento(
            @Parameter(description = "ID do estabelecimento", required = true)
            @PathVariable UUID estabelecimentoId,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/equipes-saude/estabelecimento/{} - pageable: {}", estabelecimentoId, pageable);
        try {
            Page<EquipeSaudeResponse> response = equipeSaudeService.listarPorEstabelecimento(estabelecimentoId, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar equipes por estabelecimento — estabelecimentoId: {}, mensagem: {}", estabelecimentoId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar equipes por estabelecimento — estabelecimentoId: {}, pageable: {}", estabelecimentoId, pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/estabelecimento/{estabelecimentoId}/status/{status}")
    @Operation(summary = "Listar equipes por status e estabelecimento", description = "Retorna uma lista paginada de equipes filtradas por status e estabelecimento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de equipes retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<EquipeSaudeResponse>> listarPorStatus(
            @Parameter(description = "ID do estabelecimento", required = true)
            @PathVariable UUID estabelecimentoId,
            @Parameter(description = "Status da equipe (ATIVO, INATIVO)", required = true)
            @PathVariable StatusAtivoEnum status,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/equipes-saude/estabelecimento/{}/status/{} - pageable: {}", estabelecimentoId, status, pageable);
        try {
            Page<EquipeSaudeResponse> response = equipeSaudeService.listarPorStatus(status, estabelecimentoId, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar equipes por status e estabelecimento — estabelecimentoId: {}, status: {}, mensagem: {}", estabelecimentoId, status, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar equipes por status e estabelecimento — estabelecimentoId: {}, status: {}, pageable: {}", estabelecimentoId, status, pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter equipe por ID", description = "Retorna uma equipe específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipe encontrada",
                    content = @Content(schema = @Schema(implementation = EquipeSaudeResponse.class))),
            @ApiResponse(responseCode = "404", description = "Equipe não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EquipeSaudeResponse> obterPorId(
            @Parameter(description = "ID da equipe", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/equipes-saude/{}", id);
        try {
            EquipeSaudeResponse response = equipeSaudeService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Equipe não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter equipe por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar equipe de saúde", description = "Atualiza uma equipe de saúde existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipe atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = EquipeSaudeResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Equipe não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EquipeSaudeResponse> atualizar(
            @Parameter(description = "ID da equipe", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody EquipeSaudeRequest request) {
        log.debug("REQUEST PUT /v1/equipes-saude/{} - payload: {}", id, request);
        try {
            EquipeSaudeResponse response = equipeSaudeService.atualizar(id, request);
            log.info("Equipe de saúde atualizada com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar equipe de saúde — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar equipe de saúde — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir equipe de saúde", description = "Exclui (desativa) uma equipe de saúde do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Equipe excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Equipe não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da equipe", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/equipes-saude/{}", id);
        try {
            equipeSaudeService.excluir(id);
            log.info("Equipe de saúde excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Equipe não encontrada para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir equipe de saúde — ID: {}", id, ex);
            throw ex;
        }
    }
}
