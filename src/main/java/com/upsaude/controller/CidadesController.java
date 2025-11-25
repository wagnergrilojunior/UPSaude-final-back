package com.upsaude.controller;

import com.upsaude.api.request.CidadesRequest;
import com.upsaude.api.response.CidadesResponse;
import com.upsaude.service.CidadesService;
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
 * Controlador REST para operações relacionadas a Cidades.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/cidades")
@Tag(name = "Cidades", description = "API para gerenciamento de Cidades")
@RequiredArgsConstructor
public class CidadesController {

    private final CidadesService cidadesService;

    @PostMapping
    @Operation(summary = "Criar nova cidade", description = "Cria uma nova cidade no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cidade criada com sucesso",
                    content = @Content(schema = @Schema(implementation = CidadesResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CidadesResponse> criar(@Valid @RequestBody CidadesRequest request) {
        CidadesResponse response = cidadesService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar cidades", description = "Retorna uma lista paginada de cidades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cidades retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<CidadesResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<CidadesResponse> response = cidadesService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter cidade por ID", description = "Retorna uma cidade específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cidade encontrada",
                    content = @Content(schema = @Schema(implementation = CidadesResponse.class))),
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CidadesResponse> obterPorId(
            @Parameter(description = "ID da cidade", required = true)
            @PathVariable UUID id) {
        CidadesResponse response = cidadesService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cidade", description = "Atualiza uma cidade existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cidade atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = CidadesResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CidadesResponse> atualizar(
            @Parameter(description = "ID da cidade", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody CidadesRequest request) {
        CidadesResponse response = cidadesService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir cidade", description = "Exclui (desativa) uma cidade do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cidade excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da cidade", required = true)
            @PathVariable UUID id) {
        cidadesService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

