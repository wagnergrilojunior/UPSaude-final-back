package com.upsaude.controller;

import com.upsaude.api.request.EquipeSaudeRequest;
import com.upsaude.api.response.EquipeSaudeResponse;
import com.upsaude.enums.StatusAtivoEnum;
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
        EquipeSaudeResponse response = equipeSaudeService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
        Page<EquipeSaudeResponse> response = equipeSaudeService.listar(pageable);
        return ResponseEntity.ok(response);
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
        Page<EquipeSaudeResponse> response = equipeSaudeService.listarPorEstabelecimento(estabelecimentoId, pageable);
        return ResponseEntity.ok(response);
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
        Page<EquipeSaudeResponse> response = equipeSaudeService.listarPorStatus(status, estabelecimentoId, pageable);
        return ResponseEntity.ok(response);
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
        EquipeSaudeResponse response = equipeSaudeService.obterPorId(id);
        return ResponseEntity.ok(response);
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
        EquipeSaudeResponse response = equipeSaudeService.atualizar(id, request);
        return ResponseEntity.ok(response);
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
        equipeSaudeService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

