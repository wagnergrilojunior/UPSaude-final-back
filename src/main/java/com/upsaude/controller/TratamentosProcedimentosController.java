package com.upsaude.controller;

import com.upsaude.api.request.TratamentosProcedimentosRequest;
import com.upsaude.api.response.TratamentosProcedimentosResponse;
import com.upsaude.service.TratamentosProcedimentosService;
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
 * Controlador REST para operações relacionadas a Tratamentos e Procedimentos.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/api/v1/tratamentos-procedimentos")
@Tag(name = "Tratamentos e Procedimentos", description = "API para gerenciamento de Tratamentos e Procedimentos")
@RequiredArgsConstructor
public class TratamentosProcedimentosController {

    private final TratamentosProcedimentosService tratamentosProcedimentosService;

    @PostMapping
    @Operation(summary = "Criar novo tratamento e procedimento", description = "Cria um novo tratamento e procedimento no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tratamento e procedimento criado com sucesso",
                    content = @Content(schema = @Schema(implementation = TratamentosProcedimentosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<TratamentosProcedimentosResponse> criar(@Valid @RequestBody TratamentosProcedimentosRequest request) {
        TratamentosProcedimentosResponse response = tratamentosProcedimentosService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar tratamentos e procedimentos", description = "Retorna uma lista paginada de tratamentos e procedimentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tratamentos e procedimentos retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<TratamentosProcedimentosResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<TratamentosProcedimentosResponse> response = tratamentosProcedimentosService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter tratamento e procedimento por ID", description = "Retorna um tratamento e procedimento específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tratamento e procedimento encontrado",
                    content = @Content(schema = @Schema(implementation = TratamentosProcedimentosResponse.class))),
            @ApiResponse(responseCode = "404", description = "Tratamento e procedimento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<TratamentosProcedimentosResponse> obterPorId(
            @Parameter(description = "ID do tratamento e procedimento", required = true)
            @PathVariable UUID id) {
        TratamentosProcedimentosResponse response = tratamentosProcedimentosService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar tratamento e procedimento", description = "Atualiza um tratamento e procedimento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tratamento e procedimento atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = TratamentosProcedimentosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Tratamento e procedimento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<TratamentosProcedimentosResponse> atualizar(
            @Parameter(description = "ID do tratamento e procedimento", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody TratamentosProcedimentosRequest request) {
        TratamentosProcedimentosResponse response = tratamentosProcedimentosService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir tratamento e procedimento", description = "Exclui (desativa) um tratamento e procedimento do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tratamento e procedimento excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tratamento e procedimento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do tratamento e procedimento", required = true)
            @PathVariable UUID id) {
        tratamentosProcedimentosService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

