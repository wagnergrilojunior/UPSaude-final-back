package com.upsaude.controller;

import com.upsaude.api.request.EstabelecimentosRequest;
import com.upsaude.api.response.EstabelecimentosResponse;
import com.upsaude.service.EstabelecimentosService;
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
 * Controlador REST para operações relacionadas a Estabelecimentos.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/api/v1/estabelecimentos")
@Tag(name = "Estabelecimentos", description = "API para gerenciamento de Estabelecimentos")
@RequiredArgsConstructor
public class EstabelecimentosController {

    private final EstabelecimentosService estabelecimentosService;

    @PostMapping
    @Operation(summary = "Criar novo estabelecimento", description = "Cria um novo estabelecimento no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estabelecimento criado com sucesso",
                    content = @Content(schema = @Schema(implementation = EstabelecimentosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EstabelecimentosResponse> criar(@Valid @RequestBody EstabelecimentosRequest request) {
        EstabelecimentosResponse response = estabelecimentosService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar estabelecimentos", description = "Retorna uma lista paginada de estabelecimentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de estabelecimentos retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<EstabelecimentosResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<EstabelecimentosResponse> response = estabelecimentosService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter estabelecimento por ID", description = "Retorna um estabelecimento específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estabelecimento encontrado",
                    content = @Content(schema = @Schema(implementation = EstabelecimentosResponse.class))),
            @ApiResponse(responseCode = "404", description = "Estabelecimento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EstabelecimentosResponse> obterPorId(
            @Parameter(description = "ID do estabelecimento", required = true)
            @PathVariable UUID id) {
        EstabelecimentosResponse response = estabelecimentosService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar estabelecimento", description = "Atualiza um estabelecimento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estabelecimento atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = EstabelecimentosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Estabelecimento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EstabelecimentosResponse> atualizar(
            @Parameter(description = "ID do estabelecimento", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody EstabelecimentosRequest request) {
        EstabelecimentosResponse response = estabelecimentosService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir estabelecimento", description = "Exclui (desativa) um estabelecimento do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Estabelecimento excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estabelecimento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do estabelecimento", required = true)
            @PathVariable UUID id) {
        estabelecimentosService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

