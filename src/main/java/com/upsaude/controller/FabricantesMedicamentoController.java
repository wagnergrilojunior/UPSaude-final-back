package com.upsaude.controller;

import com.upsaude.api.request.FabricantesMedicamentoRequest;
import com.upsaude.api.response.FabricantesMedicamentoResponse;
import com.upsaude.service.FabricantesMedicamentoService;
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
 * Controlador REST para operações relacionadas a Fabricantes de Medicamento.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/api/v1/fabricantes-medicamento")
@Tag(name = "Fabricantes de Medicamento", description = "API para gerenciamento de Fabricantes de Medicamento")
@RequiredArgsConstructor
public class FabricantesMedicamentoController {

    private final FabricantesMedicamentoService fabricantesMedicamentoService;

    @PostMapping
    @Operation(summary = "Criar novo fabricante de medicamento", description = "Cria um novo fabricante de medicamento no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Fabricante de medicamento criado com sucesso",
                    content = @Content(schema = @Schema(implementation = FabricantesMedicamentoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<FabricantesMedicamentoResponse> criar(@Valid @RequestBody FabricantesMedicamentoRequest request) {
        FabricantesMedicamentoResponse response = fabricantesMedicamentoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar fabricantes de medicamento", description = "Retorna uma lista paginada de fabricantes de medicamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de fabricantes de medicamento retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<FabricantesMedicamentoResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<FabricantesMedicamentoResponse> response = fabricantesMedicamentoService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter fabricante de medicamento por ID", description = "Retorna um fabricante de medicamento específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fabricante de medicamento encontrado",
                    content = @Content(schema = @Schema(implementation = FabricantesMedicamentoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Fabricante de medicamento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<FabricantesMedicamentoResponse> obterPorId(
            @Parameter(description = "ID do fabricante de medicamento", required = true)
            @PathVariable UUID id) {
        FabricantesMedicamentoResponse response = fabricantesMedicamentoService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar fabricante de medicamento", description = "Atualiza um fabricante de medicamento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fabricante de medicamento atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = FabricantesMedicamentoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Fabricante de medicamento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<FabricantesMedicamentoResponse> atualizar(
            @Parameter(description = "ID do fabricante de medicamento", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody FabricantesMedicamentoRequest request) {
        FabricantesMedicamentoResponse response = fabricantesMedicamentoService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir fabricante de medicamento", description = "Exclui (desativa) um fabricante de medicamento do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Fabricante de medicamento excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Fabricante de medicamento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do fabricante de medicamento", required = true)
            @PathVariable UUID id) {
        fabricantesMedicamentoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

