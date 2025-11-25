package com.upsaude.controller;

import com.upsaude.api.request.ProcedimentosOdontologicosRequest;
import com.upsaude.api.response.ProcedimentosOdontologicosResponse;
import com.upsaude.service.ProcedimentosOdontologicosService;
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
 * Controlador REST para operações relacionadas a Procedimentos Odontológicos.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/procedimentos-odontologicos")
@Tag(name = "Procedimentos Odontológicos", description = "API para gerenciamento de Procedimentos Odontológicos")
@RequiredArgsConstructor
public class ProcedimentosOdontologicosController {

    private final ProcedimentosOdontologicosService procedimentosOdontologicosService;

    @PostMapping
    @Operation(summary = "Criar novo procedimento odontológico", description = "Cria um novo procedimento odontológico no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Procedimento odontológico criado com sucesso",
                    content = @Content(schema = @Schema(implementation = ProcedimentosOdontologicosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ProcedimentosOdontologicosResponse> criar(@Valid @RequestBody ProcedimentosOdontologicosRequest request) {
        ProcedimentosOdontologicosResponse response = procedimentosOdontologicosService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar procedimentos odontológicos", description = "Retorna uma lista paginada de procedimentos odontológicos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de procedimentos odontológicos retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ProcedimentosOdontologicosResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<ProcedimentosOdontologicosResponse> response = procedimentosOdontologicosService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter procedimento odontológico por ID", description = "Retorna um procedimento odontológico específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Procedimento odontológico encontrado",
                    content = @Content(schema = @Schema(implementation = ProcedimentosOdontologicosResponse.class))),
            @ApiResponse(responseCode = "404", description = "Procedimento odontológico não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ProcedimentosOdontologicosResponse> obterPorId(
            @Parameter(description = "ID do procedimento odontológico", required = true)
            @PathVariable UUID id) {
        ProcedimentosOdontologicosResponse response = procedimentosOdontologicosService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar procedimento odontológico", description = "Atualiza um procedimento odontológico existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Procedimento odontológico atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = ProcedimentosOdontologicosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Procedimento odontológico não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ProcedimentosOdontologicosResponse> atualizar(
            @Parameter(description = "ID do procedimento odontológico", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody ProcedimentosOdontologicosRequest request) {
        ProcedimentosOdontologicosResponse response = procedimentosOdontologicosService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir procedimento odontológico", description = "Exclui (desativa) um procedimento odontológico do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Procedimento odontológico excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Procedimento odontológico não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do procedimento odontológico", required = true)
            @PathVariable UUID id) {
        procedimentosOdontologicosService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

