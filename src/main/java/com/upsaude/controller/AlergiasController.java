package com.upsaude.controller;

import com.upsaude.api.request.AlergiasRequest;
import com.upsaude.api.response.AlergiasResponse;
import com.upsaude.service.AlergiasService;
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
 * Controlador REST para operações relacionadas a Alergias.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/alergias")
@Tag(name = "Alergias", description = "API para gerenciamento de Alergias")
@RequiredArgsConstructor
public class AlergiasController {

    private final AlergiasService alergiasService;

    @PostMapping
    @Operation(summary = "Criar nova alergia", description = "Cria uma nova alergia no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alergia criada com sucesso",
                    content = @Content(schema = @Schema(implementation = AlergiasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AlergiasResponse> criar(@Valid @RequestBody AlergiasRequest request) {
        AlergiasResponse response = alergiasService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar alergias", description = "Retorna uma lista paginada de alergias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de alergias retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<AlergiasResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<AlergiasResponse> response = alergiasService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter alergia por ID", description = "Retorna uma alergia específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alergia encontrada",
                    content = @Content(schema = @Schema(implementation = AlergiasResponse.class))),
            @ApiResponse(responseCode = "404", description = "Alergia não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AlergiasResponse> obterPorId(
            @Parameter(description = "ID da alergia", required = true)
            @PathVariable UUID id) {
        AlergiasResponse response = alergiasService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar alergia", description = "Atualiza uma alergia existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alergia atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = AlergiasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Alergia não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AlergiasResponse> atualizar(
            @Parameter(description = "ID da alergia", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody AlergiasRequest request) {
        AlergiasResponse response = alergiasService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir alergia", description = "Exclui (desativa) uma alergia do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Alergia excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Alergia não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da alergia", required = true)
            @PathVariable UUID id) {
        alergiasService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

