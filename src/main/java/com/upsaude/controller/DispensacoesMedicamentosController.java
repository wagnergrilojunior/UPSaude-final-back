package com.upsaude.controller;

import com.upsaude.api.request.DispensacoesMedicamentosRequest;
import com.upsaude.api.response.DispensacoesMedicamentosResponse;
import com.upsaude.service.DispensacoesMedicamentosService;
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
 * Controlador REST para operações relacionadas a Dispensações de Medicamentos.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/dispensacoes-medicamentos")
@Tag(name = "Dispensações de Medicamentos", description = "API para gerenciamento de Dispensações de Medicamentos")
@RequiredArgsConstructor
public class DispensacoesMedicamentosController {

    private final DispensacoesMedicamentosService dispensacoesMedicamentosService;

    @PostMapping
    @Operation(summary = "Criar nova dispensação de medicamento", description = "Cria uma nova dispensação de medicamento no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dispensação de medicamento criada com sucesso",
                    content = @Content(schema = @Schema(implementation = DispensacoesMedicamentosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DispensacoesMedicamentosResponse> criar(@Valid @RequestBody DispensacoesMedicamentosRequest request) {
        DispensacoesMedicamentosResponse response = dispensacoesMedicamentosService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar dispensações de medicamentos", description = "Retorna uma lista paginada de dispensações de medicamentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de dispensações de medicamentos retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<DispensacoesMedicamentosResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<DispensacoesMedicamentosResponse> response = dispensacoesMedicamentosService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter dispensação de medicamento por ID", description = "Retorna uma dispensação de medicamento específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dispensação de medicamento encontrada",
                    content = @Content(schema = @Schema(implementation = DispensacoesMedicamentosResponse.class))),
            @ApiResponse(responseCode = "404", description = "Dispensação de medicamento não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DispensacoesMedicamentosResponse> obterPorId(
            @Parameter(description = "ID da dispensação de medicamento", required = true)
            @PathVariable UUID id) {
        DispensacoesMedicamentosResponse response = dispensacoesMedicamentosService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dispensação de medicamento", description = "Atualiza uma dispensação de medicamento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dispensação de medicamento atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = DispensacoesMedicamentosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Dispensação de medicamento não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DispensacoesMedicamentosResponse> atualizar(
            @Parameter(description = "ID da dispensação de medicamento", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody DispensacoesMedicamentosRequest request) {
        DispensacoesMedicamentosResponse response = dispensacoesMedicamentosService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir dispensação de medicamento", description = "Exclui (desativa) uma dispensação de medicamento do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Dispensação de medicamento excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Dispensação de medicamento não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da dispensação de medicamento", required = true)
            @PathVariable UUID id) {
        dispensacoesMedicamentosService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

