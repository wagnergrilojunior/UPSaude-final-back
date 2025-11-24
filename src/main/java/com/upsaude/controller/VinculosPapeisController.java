package com.upsaude.controller;

import com.upsaude.api.request.VinculosPapeisRequest;
import com.upsaude.api.response.VinculosPapeisResponse;
import com.upsaude.service.VinculosPapeisService;
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
 * Controlador REST para operações relacionadas a Vínculos de Papéis.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/api/v1/vinculos-papeis")
@Tag(name = "Vínculos de Papéis", description = "API para gerenciamento de Vínculos de Papéis")
@RequiredArgsConstructor
public class VinculosPapeisController {

    private final VinculosPapeisService vinculosPapeisService;

    @PostMapping
    @Operation(summary = "Criar novo vínculo de papel", description = "Cria um novo vínculo de papel no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vínculo de papel criado com sucesso",
                    content = @Content(schema = @Schema(implementation = VinculosPapeisResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<VinculosPapeisResponse> criar(@Valid @RequestBody VinculosPapeisRequest request) {
        VinculosPapeisResponse response = vinculosPapeisService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar vínculos de papéis", description = "Retorna uma lista paginada de vínculos de papéis")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de vínculos de papéis retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<VinculosPapeisResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<VinculosPapeisResponse> response = vinculosPapeisService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter vínculo de papel por ID", description = "Retorna um vínculo de papel específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vínculo de papel encontrado",
                    content = @Content(schema = @Schema(implementation = VinculosPapeisResponse.class))),
            @ApiResponse(responseCode = "404", description = "Vínculo de papel não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<VinculosPapeisResponse> obterPorId(
            @Parameter(description = "ID do vínculo de papel", required = true)
            @PathVariable UUID id) {
        VinculosPapeisResponse response = vinculosPapeisService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar vínculo de papel", description = "Atualiza um vínculo de papel existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vínculo de papel atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = VinculosPapeisResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Vínculo de papel não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<VinculosPapeisResponse> atualizar(
            @Parameter(description = "ID do vínculo de papel", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody VinculosPapeisRequest request) {
        VinculosPapeisResponse response = vinculosPapeisService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir vínculo de papel", description = "Exclui (desativa) um vínculo de papel do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vínculo de papel excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Vínculo de papel não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do vínculo de papel", required = true)
            @PathVariable UUID id) {
        vinculosPapeisService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

