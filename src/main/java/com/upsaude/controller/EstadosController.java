package com.upsaude.controller;

import com.upsaude.api.request.EstadosRequest;
import com.upsaude.api.response.EstadosResponse;
import com.upsaude.service.EstadosService;
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
 * Controlador REST para operações relacionadas a Estados.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/estados")
@Tag(name = "Estados", description = "API para gerenciamento de Estados")
@RequiredArgsConstructor
public class EstadosController {

    private final EstadosService estadosService;

    @PostMapping
    @Operation(summary = "Criar novo estado", description = "Cria um novo estado no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estado criado com sucesso",
                    content = @Content(schema = @Schema(implementation = EstadosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EstadosResponse> criar(@Valid @RequestBody EstadosRequest request) {
        EstadosResponse response = estadosService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar estados", description = "Retorna uma lista paginada de estados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de estados retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<EstadosResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<EstadosResponse> response = estadosService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter estado por ID", description = "Retorna um estado específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado encontrado",
                    content = @Content(schema = @Schema(implementation = EstadosResponse.class))),
            @ApiResponse(responseCode = "404", description = "Estado não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EstadosResponse> obterPorId(
            @Parameter(description = "ID do estado", required = true)
            @PathVariable UUID id) {
        EstadosResponse response = estadosService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar estado", description = "Atualiza um estado existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = EstadosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Estado não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EstadosResponse> atualizar(
            @Parameter(description = "ID do estado", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody EstadosRequest request) {
        EstadosResponse response = estadosService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir estado", description = "Exclui (desativa) um estado do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Estado excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estado não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do estado", required = true)
            @PathVariable UUID id) {
        estadosService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

