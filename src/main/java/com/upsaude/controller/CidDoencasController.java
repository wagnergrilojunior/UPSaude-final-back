package com.upsaude.controller;

import com.upsaude.api.request.CidDoencasRequest;
import com.upsaude.api.response.CidDoencasResponse;
import com.upsaude.service.CidDoencasService;
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
 * Controlador REST para operações relacionadas a CID Doenças.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/cid-doencas")
@Tag(name = "CID Doenças", description = "API para gerenciamento de CID Doenças")
@RequiredArgsConstructor
public class CidDoencasController {

    private final CidDoencasService cidDoencasService;

    @PostMapping
    @Operation(summary = "Criar nova CID doença", description = "Cria uma nova CID doença no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CID doença criada com sucesso",
                    content = @Content(schema = @Schema(implementation = CidDoencasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CidDoencasResponse> criar(@Valid @RequestBody CidDoencasRequest request) {
        CidDoencasResponse response = cidDoencasService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar CID doenças", description = "Retorna uma lista paginada de CID doenças")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de CID doenças retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<CidDoencasResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<CidDoencasResponse> response = cidDoencasService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter CID doença por ID", description = "Retorna uma CID doença específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CID doença encontrada",
                    content = @Content(schema = @Schema(implementation = CidDoencasResponse.class))),
            @ApiResponse(responseCode = "404", description = "CID doença não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CidDoencasResponse> obterPorId(
            @Parameter(description = "ID da CID doença", required = true)
            @PathVariable UUID id) {
        CidDoencasResponse response = cidDoencasService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar CID doença", description = "Atualiza uma CID doença existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CID doença atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = CidDoencasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "CID doença não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CidDoencasResponse> atualizar(
            @Parameter(description = "ID da CID doença", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody CidDoencasRequest request) {
        CidDoencasResponse response = cidDoencasService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir CID doença", description = "Exclui (desativa) uma CID doença do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "CID doença excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "CID doença não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da CID doença", required = true)
            @PathVariable UUID id) {
        cidDoencasService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

