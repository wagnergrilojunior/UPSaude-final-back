package com.upsaude.controller;

import com.upsaude.api.request.MovimentacoesEstoqueRequest;
import com.upsaude.api.response.MovimentacoesEstoqueResponse;
import com.upsaude.service.MovimentacoesEstoqueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controlador REST para operações relacionadas a Movimentações de Estoque.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/api/v1/movimentacoes-estoque")
@Tag(name = "Movimentações de Estoque", description = "API para gerenciamento de Movimentações de Estoque")
@RequiredArgsConstructor
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
        MovimentacoesEstoqueResponse response = movimentacoesEstoqueService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
        Page<MovimentacoesEstoqueResponse> response = movimentacoesEstoqueService.listar(pageable);
        return ResponseEntity.ok(response);
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
        MovimentacoesEstoqueResponse response = movimentacoesEstoqueService.obterPorId(id);
        return ResponseEntity.ok(response);
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
        MovimentacoesEstoqueResponse response = movimentacoesEstoqueService.atualizar(id, request);
        return ResponseEntity.ok(response);
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
        movimentacoesEstoqueService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

