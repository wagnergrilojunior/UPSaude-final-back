package com.upsaude.controller.estabelecimento.estoque;

import com.upsaude.api.request.estabelecimento.estoque.MovimentacoesEstoqueRequest;
import com.upsaude.api.response.estabelecimento.estoque.MovimentacoesEstoqueResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.estabelecimento.estoque.MovimentacoesEstoqueService;
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
@RequestMapping("/v1/movimentacoes-estoque")
@Tag(name = "Movimentações de Estoque", description = "API para gerenciamento de Movimentações de Estoque")
@RequiredArgsConstructor
@Slf4j
public class MovimentacoesEstoqueController {

    private final MovimentacoesEstoqueService movimentacoesEstoqueService;

    @PostMapping
    @Operation(summary = "Criar nova movimentação de estoque", description = "Cria uma nova movimentação de estoque no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movimentação de estoque criada com sucesso",
                    content = @Content(schema = @Schema(implementation = MovimentacoesEstoqueResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MovimentacoesEstoqueResponse> criar(@Valid @RequestBody MovimentacoesEstoqueRequest request) {
        log.debug("REQUEST POST /v1/movimentacoes-estoque - payload: {}", request);
        try {
            MovimentacoesEstoqueResponse response = movimentacoesEstoqueService.criar(request);
            log.info("Movimentação de estoque criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar movimentação de estoque — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar movimentação de estoque — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar movimentações de estoque", description = "Retorna uma lista paginada de movimentações de estoque")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de movimentações de estoque retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<MovimentacoesEstoqueResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/movimentacoes-estoque - pageable: {}", pageable);
        try {
            Page<MovimentacoesEstoqueResponse> response = movimentacoesEstoqueService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar movimentações de estoque — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter movimentação de estoque por ID", description = "Retorna uma movimentação de estoque específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimentação de estoque encontrada",
                    content = @Content(schema = @Schema(implementation = MovimentacoesEstoqueResponse.class))),
            @ApiResponse(responseCode = "404", description = "Movimentação de estoque não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MovimentacoesEstoqueResponse> obterPorId(
            @Parameter(description = "ID da movimentação de estoque", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/movimentacoes-estoque/{}", id);
        try {
            MovimentacoesEstoqueResponse response = movimentacoesEstoqueService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Falha ao obter movimentação de estoque por ID — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter movimentação de estoque por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar movimentação de estoque", description = "Atualiza uma movimentação de estoque existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimentação de estoque atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = MovimentacoesEstoqueResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Movimentação de estoque não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MovimentacoesEstoqueResponse> atualizar(
            @Parameter(description = "ID da movimentação de estoque", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody MovimentacoesEstoqueRequest request) {
        log.debug("REQUEST PUT /v1/movimentacoes-estoque/{} - payload: {}", id, request);
        try {
            MovimentacoesEstoqueResponse response = movimentacoesEstoqueService.atualizar(id, request);
            log.info("Movimentação de estoque atualizada com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar movimentação de estoque — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar movimentação de estoque — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir movimentação de estoque", description = "Exclui (desativa) uma movimentação de estoque do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Movimentação de estoque excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Movimentação de estoque não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da movimentação de estoque", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/movimentacoes-estoque/{}", id);
        try {
            movimentacoesEstoqueService.excluir(id);
            log.info("Movimentação de estoque excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Falha ao excluir movimentação de estoque — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir movimentação de estoque — ID: {}", id, ex);
            throw ex;
        }
    }
}
