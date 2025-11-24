package com.upsaude.controller;

import com.upsaude.api.request.VacinacoesRequest;
import com.upsaude.api.response.VacinacoesResponse;
import com.upsaude.service.VacinacoesService;
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
 * Controlador REST para operações relacionadas a Vacinações.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/api/v1/vacinacoes")
@Tag(name = "Vacinações", description = "API para gerenciamento de Vacinações")
@RequiredArgsConstructor
public class VacinacoesController {

    private final VacinacoesService vacinacoesService;

    @PostMapping
    @Operation(summary = "Criar nova vacinação", description = "Cria uma nova vacinação no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vacinação criada com sucesso",
                    content = @Content(schema = @Schema(implementation = VacinacoesResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<VacinacoesResponse> criar(@Valid @RequestBody VacinacoesRequest request) {
        VacinacoesResponse response = vacinacoesService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar vacinações", description = "Retorna uma lista paginada de vacinações")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de vacinações retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<VacinacoesResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<VacinacoesResponse> response = vacinacoesService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter vacinação por ID", description = "Retorna uma vacinação específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vacinação encontrada",
                    content = @Content(schema = @Schema(implementation = VacinacoesResponse.class))),
            @ApiResponse(responseCode = "404", description = "Vacinação não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<VacinacoesResponse> obterPorId(
            @Parameter(description = "ID da vacinação", required = true)
            @PathVariable UUID id) {
        VacinacoesResponse response = vacinacoesService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar vacinação", description = "Atualiza uma vacinação existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vacinação atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = VacinacoesResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Vacinação não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<VacinacoesResponse> atualizar(
            @Parameter(description = "ID da vacinação", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody VacinacoesRequest request) {
        VacinacoesResponse response = vacinacoesService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir vacinação", description = "Exclui (desativa) uma vacinação do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vacinação excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Vacinação não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da vacinação", required = true)
            @PathVariable UUID id) {
        vacinacoesService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

