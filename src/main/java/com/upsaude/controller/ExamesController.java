package com.upsaude.controller;

import com.upsaude.api.request.ExamesRequest;
import com.upsaude.api.response.ExamesResponse;
import com.upsaude.service.ExamesService;
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
 * Controlador REST para operações relacionadas a Exames.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/api/v1/exames")
@Tag(name = "Exames", description = "API para gerenciamento de Exames")
@RequiredArgsConstructor
public class ExamesController {

    private final ExamesService examesService;

    @PostMapping
    @Operation(summary = "Criar novo exame", description = "Cria um novo exame no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Exame criado com sucesso",
                    content = @Content(schema = @Schema(implementation = ExamesResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ExamesResponse> criar(@Valid @RequestBody ExamesRequest request) {
        ExamesResponse response = examesService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar exames", description = "Retorna uma lista paginada de exames")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de exames retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ExamesResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<ExamesResponse> response = examesService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter exame por ID", description = "Retorna um exame específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exame encontrado",
                    content = @Content(schema = @Schema(implementation = ExamesResponse.class))),
            @ApiResponse(responseCode = "404", description = "Exame não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ExamesResponse> obterPorId(
            @Parameter(description = "ID do exame", required = true)
            @PathVariable UUID id) {
        ExamesResponse response = examesService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar exame", description = "Atualiza um exame existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exame atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = ExamesResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Exame não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ExamesResponse> atualizar(
            @Parameter(description = "ID do exame", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody ExamesRequest request) {
        ExamesResponse response = examesService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir exame", description = "Exclui (desativa) um exame do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Exame excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Exame não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do exame", required = true)
            @PathVariable UUID id) {
        examesService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

