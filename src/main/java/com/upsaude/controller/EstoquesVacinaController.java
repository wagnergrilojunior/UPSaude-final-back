package com.upsaude.controller;

import com.upsaude.api.request.EstoquesVacinaRequest;
import com.upsaude.api.response.EstoquesVacinaResponse;
import com.upsaude.service.EstoquesVacinaService;
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
 * Controlador REST para operações relacionadas a Estoques de Vacina.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/api/v1/estoques-vacina")
@Tag(name = "Estoques de Vacina", description = "API para gerenciamento de Estoques de Vacina")
@RequiredArgsConstructor
public class EstoquesVacinaController {

    private final EstoquesVacinaService estoquesVacinaService;

    @PostMapping
    @Operation(summary = "Criar novo estoque de vacina", description = "Cria um novo estoque de vacina no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estoque de vacina criado com sucesso",
                    content = @Content(schema = @Schema(implementation = EstoquesVacinaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EstoquesVacinaResponse> criar(@Valid @RequestBody EstoquesVacinaRequest request) {
        EstoquesVacinaResponse response = estoquesVacinaService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar estoques de vacina", description = "Retorna uma lista paginada de estoques de vacina")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de estoques de vacina retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<EstoquesVacinaResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<EstoquesVacinaResponse> response = estoquesVacinaService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter estoque de vacina por ID", description = "Retorna um estoque de vacina específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estoque de vacina encontrado",
                    content = @Content(schema = @Schema(implementation = EstoquesVacinaResponse.class))),
            @ApiResponse(responseCode = "404", description = "Estoque de vacina não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EstoquesVacinaResponse> obterPorId(
            @Parameter(description = "ID do estoque de vacina", required = true)
            @PathVariable UUID id) {
        EstoquesVacinaResponse response = estoquesVacinaService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar estoque de vacina", description = "Atualiza um estoque de vacina existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estoque de vacina atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = EstoquesVacinaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Estoque de vacina não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EstoquesVacinaResponse> atualizar(
            @Parameter(description = "ID do estoque de vacina", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody EstoquesVacinaRequest request) {
        EstoquesVacinaResponse response = estoquesVacinaService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir estoque de vacina", description = "Exclui (desativa) um estoque de vacina do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Estoque de vacina excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estoque de vacina não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do estoque de vacina", required = true)
            @PathVariable UUID id) {
        estoquesVacinaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

