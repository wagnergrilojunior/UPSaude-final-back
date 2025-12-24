package com.upsaude.controller.api.sistema;

import com.upsaude.api.request.sistema.usuario.PermissoesRequest;
import com.upsaude.api.response.sistema.usuario.PermissoesResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.sistema.usuario.PermissoesService;
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

@Slf4j
@RestController
@RequestMapping("/v1/permissoes")
@Tag(name = "Permissões", description = "API para gerenciamento de Permissões")
@RequiredArgsConstructor
public class PermissoesController {

    private final PermissoesService permissoesService;

    @PostMapping
    @Operation(summary = "Criar nova permissão", description = "Cria uma nova permissão no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Permissão criada com sucesso",
                    content = @Content(schema = @Schema(implementation = PermissoesResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<PermissoesResponse> criar(@Valid @RequestBody PermissoesRequest request) {
        log.debug("REQUEST POST /v1/permissoes - payload: {}", request);
        try {
            PermissoesResponse response = permissoesService.criar(request);
            log.info("Permissão criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar permissão — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar permissão — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar permissões", description = "Retorna uma lista paginada de permissões")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de permissões retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<PermissoesResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable,
            @RequestParam(required = false) UUID estabelecimentoId,
            @RequestParam(required = false) String modulo,
            @RequestParam(required = false) String nome) {
        log.debug("REQUEST GET /v1/permissoes - pageable: {}, estabelecimentoId: {}, modulo: {}, nome: {}", pageable, estabelecimentoId, modulo, nome);
        try {
            Page<PermissoesResponse> response = permissoesService.listar(pageable, estabelecimentoId, modulo, nome);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar permissões — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter permissão por ID", description = "Retorna uma permissão específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permissão encontrada",
                    content = @Content(schema = @Schema(implementation = PermissoesResponse.class))),
            @ApiResponse(responseCode = "404", description = "Permissão não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<PermissoesResponse> obterPorId(
            @Parameter(description = "ID da permissão", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/permissoes/{}", id);
        try {
            PermissoesResponse response = permissoesService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Permissão não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter permissão por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar permissão", description = "Atualiza uma permissão existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permissão atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = PermissoesResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Permissão não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<PermissoesResponse> atualizar(
            @Parameter(description = "ID da permissão", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody PermissoesRequest request) {
        log.debug("REQUEST PUT /v1/permissoes/{} - payload: {}", id, request);
        try {
            PermissoesResponse response = permissoesService.atualizar(id, request);
            log.info("Permissão atualizada com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar permissão — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar permissão — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir permissão", description = "Exclui (desativa) uma permissão do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Permissão excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Permissão não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da permissão", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/permissoes/{}", id);
        try {
            permissoesService.excluir(id);
            log.info("Permissão excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Falha ao excluir permissão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir permissão — ID: {}", id, ex);
            throw ex;
        }
    }
}
