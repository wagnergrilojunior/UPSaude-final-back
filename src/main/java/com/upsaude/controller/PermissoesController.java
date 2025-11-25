package com.upsaude.controller;

import com.upsaude.api.request.PermissoesRequest;
import com.upsaude.api.response.PermissoesResponse;
import com.upsaude.service.PermissoesService;
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
 * Controlador REST para operações relacionadas a Permissões.
 *
 * @author UPSaúde
 */
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
        PermissoesResponse response = permissoesService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar permissões", description = "Retorna uma lista paginada de permissões")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de permissões retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<PermissoesResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<PermissoesResponse> response = permissoesService.listar(pageable);
        return ResponseEntity.ok(response);
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
        PermissoesResponse response = permissoesService.obterPorId(id);
        return ResponseEntity.ok(response);
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
        PermissoesResponse response = permissoesService.atualizar(id, request);
        return ResponseEntity.ok(response);
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
        permissoesService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

