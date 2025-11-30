package com.upsaude.controller;

import com.upsaude.api.request.CatalogoExamesRequest;
import com.upsaude.api.response.CatalogoExamesResponse;
import com.upsaude.service.CatalogoExamesService;
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
 * Controlador REST para operações relacionadas a Catálogo de Exames.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/catalogo-exames")
@Tag(name = "Catálogo de Exames", description = "API para gerenciamento de Catálogo de Exames")
@RequiredArgsConstructor
public class CatalogoExamesController {

    private final CatalogoExamesService catalogoExamesService;

    @PostMapping
    @Operation(summary = "Criar novo exame no catálogo", description = "Cria um novo exame no catálogo de exames")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Exame criado com sucesso",
                    content = @Content(schema = @Schema(implementation = CatalogoExamesResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CatalogoExamesResponse> criar(@Valid @RequestBody CatalogoExamesRequest request) {
        CatalogoExamesResponse response = catalogoExamesService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar exames do catálogo", description = "Retorna uma lista paginada de exames do catálogo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de exames retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<CatalogoExamesResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<CatalogoExamesResponse> response = catalogoExamesService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter exame por ID", description = "Retorna um exame específico do catálogo pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exame encontrado",
                    content = @Content(schema = @Schema(implementation = CatalogoExamesResponse.class))),
            @ApiResponse(responseCode = "404", description = "Exame não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CatalogoExamesResponse> obterPorId(
            @Parameter(description = "ID do exame", required = true)
            @PathVariable UUID id) {
        CatalogoExamesResponse response = catalogoExamesService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar exame", description = "Atualiza um exame existente no catálogo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exame atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = CatalogoExamesResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Exame não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CatalogoExamesResponse> atualizar(
            @Parameter(description = "ID do exame", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody CatalogoExamesRequest request) {
        CatalogoExamesResponse response = catalogoExamesService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir exame", description = "Exclui (desativa) um exame do catálogo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Exame excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Exame não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do exame", required = true)
            @PathVariable UUID id) {
        catalogoExamesService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

