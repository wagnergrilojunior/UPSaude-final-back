package com.upsaude.controller;

import com.upsaude.api.request.PapeisRequest;
import com.upsaude.api.response.PapeisResponse;
import com.upsaude.service.PapeisService;
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
 * Controlador REST para operações relacionadas a Papéis.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/papeis")
@Tag(name = "Papéis", description = "API para gerenciamento de Papéis")
@RequiredArgsConstructor
public class PapeisController {

    private final PapeisService papeisService;

    @PostMapping
    @Operation(summary = "Criar novo papel", description = "Cria um novo papel no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Papel criado com sucesso",
                    content = @Content(schema = @Schema(implementation = PapeisResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<PapeisResponse> criar(@Valid @RequestBody PapeisRequest request) {
        PapeisResponse response = papeisService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar papéis", description = "Retorna uma lista paginada de papéis")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de papéis retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<PapeisResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<PapeisResponse> response = papeisService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter papel por ID", description = "Retorna um papel específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Papel encontrado",
                    content = @Content(schema = @Schema(implementation = PapeisResponse.class))),
            @ApiResponse(responseCode = "404", description = "Papel não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<PapeisResponse> obterPorId(
            @Parameter(description = "ID do papel", required = true)
            @PathVariable UUID id) {
        PapeisResponse response = papeisService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar papel", description = "Atualiza um papel existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Papel atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = PapeisResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Papel não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<PapeisResponse> atualizar(
            @Parameter(description = "ID do papel", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody PapeisRequest request) {
        PapeisResponse response = papeisService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir papel", description = "Exclui (desativa) um papel do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Papel excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Papel não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do papel", required = true)
            @PathVariable UUID id) {
        papeisService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

