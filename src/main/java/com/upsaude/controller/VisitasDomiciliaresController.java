package com.upsaude.controller;

import com.upsaude.api.request.VisitasDomiciliaresRequest;
import com.upsaude.api.response.VisitasDomiciliaresResponse;
import com.upsaude.service.VisitasDomiciliaresService;
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
 * Controlador REST para operações relacionadas a Visitas Domiciliares.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/api/v1/visitas-domiciliares")
@Tag(name = "Visitas Domiciliares", description = "API para gerenciamento de Visitas Domiciliares")
@RequiredArgsConstructor
public class VisitasDomiciliaresController {

    private final VisitasDomiciliaresService visitasDomiciliaresService;

    @PostMapping
    @Operation(summary = "Criar nova visita domiciliar", description = "Cria uma nova visita domiciliar no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Visita domiciliar criada com sucesso",
                    content = @Content(schema = @Schema(implementation = VisitasDomiciliaresResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<VisitasDomiciliaresResponse> criar(@Valid @RequestBody VisitasDomiciliaresRequest request) {
        VisitasDomiciliaresResponse response = visitasDomiciliaresService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar visitas domiciliares", description = "Retorna uma lista paginada de visitas domiciliares")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de visitas domiciliares retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<VisitasDomiciliaresResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<VisitasDomiciliaresResponse> response = visitasDomiciliaresService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter visita domiciliar por ID", description = "Retorna uma visita domiciliar específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Visita domiciliar encontrada",
                    content = @Content(schema = @Schema(implementation = VisitasDomiciliaresResponse.class))),
            @ApiResponse(responseCode = "404", description = "Visita domiciliar não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<VisitasDomiciliaresResponse> obterPorId(
            @Parameter(description = "ID da visita domiciliar", required = true)
            @PathVariable UUID id) {
        VisitasDomiciliaresResponse response = visitasDomiciliaresService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar visita domiciliar", description = "Atualiza uma visita domiciliar existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Visita domiciliar atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = VisitasDomiciliaresResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Visita domiciliar não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<VisitasDomiciliaresResponse> atualizar(
            @Parameter(description = "ID da visita domiciliar", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody VisitasDomiciliaresRequest request) {
        VisitasDomiciliaresResponse response = visitasDomiciliaresService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir visita domiciliar", description = "Exclui (desativa) uma visita domiciliar do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Visita domiciliar excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Visita domiciliar não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da visita domiciliar", required = true)
            @PathVariable UUID id) {
        visitasDomiciliaresService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

