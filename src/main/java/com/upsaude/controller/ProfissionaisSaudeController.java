package com.upsaude.controller;

import com.upsaude.api.request.ProfissionaisSaudeRequest;
import com.upsaude.api.response.ProfissionaisSaudeResponse;
import com.upsaude.service.ProfissionaisSaudeService;
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
 * Controlador REST para operações relacionadas a Profissionais de Saúde.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/api/v1/profissionais-saude")
@Tag(name = "Profissionais de Saúde", description = "API para gerenciamento de Profissionais de Saúde")
@RequiredArgsConstructor
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
        ProfissionaisSaudeResponse response = profissionaisSaudeService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
        Page<ProfissionaisSaudeResponse> response = profissionaisSaudeService.listar(pageable);
        return ResponseEntity.ok(response);
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
        ProfissionaisSaudeResponse response = profissionaisSaudeService.obterPorId(id);
        return ResponseEntity.ok(response);
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
        ProfissionaisSaudeResponse response = profissionaisSaudeService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir profissional de saúde", description = "Exclui (desativa) um profissional de saúde do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Profissional de saúde excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Profissional de saúde não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do profissional de saúde", required = true)
            @PathVariable UUID id) {
        profissionaisSaudeService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

