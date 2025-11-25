package com.upsaude.controller;

import com.upsaude.api.request.MedicamentosRequest;
import com.upsaude.api.response.MedicamentosResponse;
import com.upsaude.service.MedicamentosService;
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
 * Controlador REST para operações relacionadas a Medicamentos.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/medicamentos")
@Tag(name = "Medicamentos", description = "API para gerenciamento de Medicamentos")
@RequiredArgsConstructor
public class MedicamentosController {

    private final MedicamentosService medicamentosService;

    @PostMapping
    @Operation(summary = "Criar novo medicamento", description = "Cria um novo medicamento no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Medicamento criado com sucesso",
                    content = @Content(schema = @Schema(implementation = MedicamentosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MedicamentosResponse> criar(@Valid @RequestBody MedicamentosRequest request) {
        MedicamentosResponse response = medicamentosService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar medicamentos", description = "Retorna uma lista paginada de medicamentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de medicamentos retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<MedicamentosResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<MedicamentosResponse> response = medicamentosService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter medicamento por ID", description = "Retorna um medicamento específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicamento encontrado",
                    content = @Content(schema = @Schema(implementation = MedicamentosResponse.class))),
            @ApiResponse(responseCode = "404", description = "Medicamento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MedicamentosResponse> obterPorId(
            @Parameter(description = "ID do medicamento", required = true)
            @PathVariable UUID id) {
        MedicamentosResponse response = medicamentosService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar medicamento", description = "Atualiza um medicamento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicamento atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = MedicamentosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Medicamento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MedicamentosResponse> atualizar(
            @Parameter(description = "ID do medicamento", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody MedicamentosRequest request) {
        MedicamentosResponse response = medicamentosService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir medicamento", description = "Exclui (desativa) um medicamento do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Medicamento excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Medicamento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do medicamento", required = true)
            @PathVariable UUID id) {
        medicamentosService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

