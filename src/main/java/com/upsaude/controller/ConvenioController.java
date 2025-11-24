package com.upsaude.controller;

import com.upsaude.api.request.ConvenioRequest;
import com.upsaude.api.response.ConvenioResponse;
import com.upsaude.service.ConvenioService;
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
 * Controlador REST para operações relacionadas a Convênios.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/api/v1/convenios")
@Tag(name = "Convênios", description = "API para gerenciamento de Convênios")
@RequiredArgsConstructor
public class ConvenioController {

    private final ConvenioService convenioService;

    @PostMapping
    @Operation(summary = "Criar novo convênio", description = "Cria um novo convênio no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Convênio criado com sucesso",
                    content = @Content(schema = @Schema(implementation = ConvenioResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ConvenioResponse> criar(@Valid @RequestBody ConvenioRequest request) {
        ConvenioResponse response = convenioService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar convênios", description = "Retorna uma lista paginada de convênios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de convênios retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ConvenioResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<ConvenioResponse> response = convenioService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter convênio por ID", description = "Retorna um convênio específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Convênio encontrado",
                    content = @Content(schema = @Schema(implementation = ConvenioResponse.class))),
            @ApiResponse(responseCode = "404", description = "Convênio não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ConvenioResponse> obterPorId(
            @Parameter(description = "ID do convênio", required = true)
            @PathVariable UUID id) {
        ConvenioResponse response = convenioService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar convênio", description = "Atualiza um convênio existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Convênio atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = ConvenioResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Convênio não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ConvenioResponse> atualizar(
            @Parameter(description = "ID do convênio", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody ConvenioRequest request) {
        ConvenioResponse response = convenioService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir convênio", description = "Exclui (desativa) um convênio do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Convênio excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Convênio não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do convênio", required = true)
            @PathVariable UUID id) {
        convenioService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

