package com.upsaude.controller;

import com.upsaude.api.request.VacinasRequest;
import com.upsaude.api.response.VacinasResponse;
import com.upsaude.service.VacinasService;
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
 * Controlador REST para operações relacionadas a Vacinas.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/vacinas")
@Tag(name = "Vacinas", description = "API para gerenciamento de Vacinas")
@RequiredArgsConstructor
public class VacinasController {

    private final VacinasService vacinasService;

    @PostMapping
    @Operation(summary = "Criar nova vacina", description = "Cria uma nova vacina no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vacina criada com sucesso",
                    content = @Content(schema = @Schema(implementation = VacinasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<VacinasResponse> criar(@Valid @RequestBody VacinasRequest request) {
        VacinasResponse response = vacinasService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar vacinas", description = "Retorna uma lista paginada de vacinas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de vacinas retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<VacinasResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<VacinasResponse> response = vacinasService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter vacina por ID", description = "Retorna uma vacina específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vacina encontrada",
                    content = @Content(schema = @Schema(implementation = VacinasResponse.class))),
            @ApiResponse(responseCode = "404", description = "Vacina não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<VacinasResponse> obterPorId(
            @Parameter(description = "ID da vacina", required = true)
            @PathVariable UUID id) {
        VacinasResponse response = vacinasService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar vacina", description = "Atualiza uma vacina existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vacina atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = VacinasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Vacina não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<VacinasResponse> atualizar(
            @Parameter(description = "ID da vacina", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody VacinasRequest request) {
        VacinasResponse response = vacinasService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir vacina", description = "Exclui (desativa) uma vacina do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vacina excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Vacina não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da vacina", required = true)
            @PathVariable UUID id) {
        vacinasService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

