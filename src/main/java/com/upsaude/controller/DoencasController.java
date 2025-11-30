package com.upsaude.controller;

import com.upsaude.api.request.DoencasRequest;
import com.upsaude.api.response.DoencasResponse;
import com.upsaude.service.DoencasService;
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
 * Controlador REST para operações relacionadas a Doencas.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/doencas")
@Tag(name = "Doenças", description = "API para gerenciamento de Doenças")
@RequiredArgsConstructor
public class DoencasController {

    private final DoencasService doencasService;

    @PostMapping
    @Operation(summary = "Criar nova doença", description = "Cria uma nova doença no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Doença criada com sucesso",
                    content = @Content(schema = @Schema(implementation = DoencasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DoencasResponse> criar(@Valid @RequestBody DoencasRequest request) {
        DoencasResponse response = doencasService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar doenças", description = "Retorna uma lista paginada de doenças")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de doenças retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<DoencasResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<DoencasResponse> response = doencasService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar doenças por nome", description = "Retorna uma lista paginada de doenças que contêm o nome informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de doenças retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Nome inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<DoencasResponse>> listarPorNome(
            @Parameter(description = "Nome da doença para busca", required = true)
            @RequestParam String nome,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<DoencasResponse> response = doencasService.listarPorNome(nome, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cid/{codigoCid}")
    @Operation(summary = "Listar doenças por código CID", description = "Retorna uma lista paginada de doenças relacionadas a um código CID específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de doenças retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Código CID inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<DoencasResponse>> listarPorCodigoCid(
            @Parameter(description = "Código CID", required = true)
            @PathVariable String codigoCid,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<DoencasResponse> response = doencasService.listarPorCodigoCid(codigoCid, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter doença por ID", description = "Retorna uma doença específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doença encontrada",
                    content = @Content(schema = @Schema(implementation = DoencasResponse.class))),
            @ApiResponse(responseCode = "404", description = "Doença não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DoencasResponse> obterPorId(
            @Parameter(description = "ID da doença", required = true)
            @PathVariable UUID id) {
        DoencasResponse response = doencasService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar doença", description = "Atualiza uma doença existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doença atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = DoencasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Doença não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DoencasResponse> atualizar(
            @Parameter(description = "ID da doença", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody DoencasRequest request) {
        DoencasResponse response = doencasService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir doença", description = "Exclui (desativa) uma doença do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Doença excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Doença não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da doença", required = true)
            @PathVariable UUID id) {
        doencasService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

