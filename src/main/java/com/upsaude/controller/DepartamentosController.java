package com.upsaude.controller;

import com.upsaude.api.request.DepartamentosRequest;
import com.upsaude.api.response.DepartamentosResponse;
import com.upsaude.service.DepartamentosService;
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
 * Controlador REST para operações relacionadas a Departamentos.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/departamentos")
@Tag(name = "Departamentos", description = "API para gerenciamento de Departamentos")
@RequiredArgsConstructor
public class DepartamentosController {

    private final DepartamentosService departamentosService;

    @PostMapping
    @Operation(summary = "Criar novo departamento", description = "Cria um novo departamento no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Departamento criado com sucesso",
                    content = @Content(schema = @Schema(implementation = DepartamentosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DepartamentosResponse> criar(@Valid @RequestBody DepartamentosRequest request) {
        DepartamentosResponse response = departamentosService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar departamentos", description = "Retorna uma lista paginada de departamentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de departamentos retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<DepartamentosResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<DepartamentosResponse> response = departamentosService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter departamento por ID", description = "Retorna um departamento específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Departamento encontrado",
                    content = @Content(schema = @Schema(implementation = DepartamentosResponse.class))),
            @ApiResponse(responseCode = "404", description = "Departamento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DepartamentosResponse> obterPorId(
            @Parameter(description = "ID do departamento", required = true)
            @PathVariable UUID id) {
        DepartamentosResponse response = departamentosService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar departamento", description = "Atualiza um departamento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Departamento atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = DepartamentosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Departamento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DepartamentosResponse> atualizar(
            @Parameter(description = "ID do departamento", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody DepartamentosRequest request) {
        DepartamentosResponse response = departamentosService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir departamento", description = "Exclui (desativa) um departamento do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Departamento excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Departamento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do departamento", required = true)
            @PathVariable UUID id) {
        departamentosService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

