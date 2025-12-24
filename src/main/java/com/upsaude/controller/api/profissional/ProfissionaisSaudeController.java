package com.upsaude.controller.api.profissional;

import com.upsaude.api.request.profissional.ProfissionaisSaudeRequest;
import com.upsaude.api.response.profissional.ProfissionaisSaudeResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.profissional.ProfissionaisSaudeService;
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
@RequestMapping("/v1/profissionais-saude")
@Tag(name = "Profissionais de Saúde", description = "API para gerenciamento de Profissionais de Saúde")
@RequiredArgsConstructor
@Slf4j
public class ProfissionaisSaudeController {

    private final ProfissionaisSaudeService profissionaisSaudeService;

    @PostMapping
    @Operation(summary = "Criar novo profissional de saúde", description = "Cria um novo profissional de saúde no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Profissional de saúde criado com sucesso",
                    content = @Content(schema = @Schema(implementation = ProfissionaisSaudeResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ProfissionaisSaudeResponse> criar(@Valid @RequestBody ProfissionaisSaudeRequest request) {
        log.debug("REQUEST POST /v1/profissionais-saude - payload: {}", request);
        try {
            ProfissionaisSaudeResponse response = profissionaisSaudeService.criar(request);
            log.info("Profissional de saúde criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar profissional de saúde — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar profissional de saúde — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar profissionais de saúde", description = "Retorna uma lista paginada de profissionais de saúde")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de profissionais de saúde retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ProfissionaisSaudeResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/profissionais-saude - pageable: {}", pageable);
        try {
            Page<ProfissionaisSaudeResponse> response = profissionaisSaudeService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar profissionais de saúde — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter profissional de saúde por ID", description = "Retorna um profissional de saúde específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profissional de saúde encontrado",
                    content = @Content(schema = @Schema(implementation = ProfissionaisSaudeResponse.class))),
            @ApiResponse(responseCode = "404", description = "Profissional de saúde não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ProfissionaisSaudeResponse> obterPorId(
            @Parameter(description = "ID do profissional de saúde", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/profissionais-saude/{}", id);
        try {
            ProfissionaisSaudeResponse response = profissionaisSaudeService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Profissional de saúde não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter profissional de saúde por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar profissional de saúde", description = "Atualiza um profissional de saúde existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profissional de saúde atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = ProfissionaisSaudeResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Profissional de saúde não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ProfissionaisSaudeResponse> atualizar(
            @Parameter(description = "ID do profissional de saúde", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody ProfissionaisSaudeRequest request) {
        log.debug("REQUEST PUT /v1/profissionais-saude/{} - payload: {}", id, request);
        try {
            ProfissionaisSaudeResponse response = profissionaisSaudeService.atualizar(id, request);
            log.info("Profissional de saúde atualizado com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar profissional de saúde — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar profissional de saúde — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir profissional de saúde", description = "Exclui (inativa) um profissional de saúde do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Profissional de saúde excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Profissional de saúde não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do profissional de saúde", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/profissionais-saude/{}", id);
        try {
            profissionaisSaudeService.excluir(id);
            log.info("Profissional de saúde excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Profissional de saúde não encontrado para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir profissional de saúde — ID: {}", id, ex);
            throw ex;
        }
    }

    @PatchMapping("/{id}/inativar")
    @Operation(summary = "Inativar profissional de saúde", description = "Inativa um profissional de saúde no sistema (soft delete)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Profissional de saúde inativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Profissional de saúde não encontrado"),
            @ApiResponse(responseCode = "400", description = "Profissional de saúde já está inativo"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> inativar(
            @Parameter(description = "ID do profissional de saúde", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST PATCH /v1/profissionais-saude/{}/inativar", id);
        try {
            profissionaisSaudeService.inativar(id);
            log.info("Profissional de saúde inativado com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Profissional de saúde não encontrado para inativação — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inativar profissional de saúde — ID: {}", id, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}/permanente")
    @Operation(summary = "Deletar profissional de saúde permanentemente", description = "Remove permanentemente um profissional de saúde do banco de dados (hard delete)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Profissional de saúde deletado permanentemente com sucesso"),
            @ApiResponse(responseCode = "404", description = "Profissional de saúde não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> deletarPermanentemente(
            @Parameter(description = "ID do profissional de saúde", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/profissionais-saude/{}/permanente", id);
        try {
            profissionaisSaudeService.deletarPermanentemente(id);
            log.info("Profissional de saúde deletado permanentemente com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Profissional de saúde não encontrado para exclusão permanente — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao deletar profissional de saúde permanentemente — ID: {}", id, ex);
            throw ex;
        }
    }
}
