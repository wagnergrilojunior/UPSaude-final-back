package com.upsaude.controller;

import com.upsaude.api.request.DoencasCronicasRequest;
import com.upsaude.api.response.DoencasCronicasResponse;
import com.upsaude.service.DoencasCronicasService;
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
 * Controlador REST para operações relacionadas a Doenças Crônicas.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/api/v1/doencas-cronicas")
@Tag(name = "Doenças Crônicas", description = "API para gerenciamento de Doenças Crônicas")
@RequiredArgsConstructor
public class DoencasCronicasController {

    private final DoencasCronicasService doencasCronicasService;

    @PostMapping
    @Operation(summary = "Criar nova doença crônica", description = "Cria uma nova doença crônica no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Doença crônica criada com sucesso",
                    content = @Content(schema = @Schema(implementation = DoencasCronicasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DoencasCronicasResponse> criar(@Valid @RequestBody DoencasCronicasRequest request) {
        DoencasCronicasResponse response = doencasCronicasService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar doenças crônicas", description = "Retorna uma lista paginada de doenças crônicas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de doenças crônicas retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<DoencasCronicasResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<DoencasCronicasResponse> response = doencasCronicasService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter doença crônica por ID", description = "Retorna uma doença crônica específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doença crônica encontrada",
                    content = @Content(schema = @Schema(implementation = DoencasCronicasResponse.class))),
            @ApiResponse(responseCode = "404", description = "Doença crônica não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DoencasCronicasResponse> obterPorId(
            @Parameter(description = "ID da doença crônica", required = true)
            @PathVariable UUID id) {
        DoencasCronicasResponse response = doencasCronicasService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar doença crônica", description = "Atualiza uma doença crônica existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doença crônica atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = DoencasCronicasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Doença crônica não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DoencasCronicasResponse> atualizar(
            @Parameter(description = "ID da doença crônica", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody DoencasCronicasRequest request) {
        DoencasCronicasResponse response = doencasCronicasService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir doença crônica", description = "Exclui (desativa) uma doença crônica do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Doença crônica excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Doença crônica não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da doença crônica", required = true)
            @PathVariable UUID id) {
        doencasCronicasService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

