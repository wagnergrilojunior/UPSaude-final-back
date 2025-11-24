package com.upsaude.controller;

import com.upsaude.api.request.TratamentosOdontologicosRequest;
import com.upsaude.api.response.TratamentosOdontologicosResponse;
import com.upsaude.service.TratamentosOdontologicosService;
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
 * Controlador REST para operações relacionadas a Tratamentos Odontológicos.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/api/v1/tratamentos-odontologicos")
@Tag(name = "Tratamentos Odontológicos", description = "API para gerenciamento de Tratamentos Odontológicos")
@RequiredArgsConstructor
public class TratamentosOdontologicosController {

    private final TratamentosOdontologicosService tratamentosOdontologicosService;

    @PostMapping
    @Operation(summary = "Criar novo tratamento odontológico", description = "Cria um novo tratamento odontológico no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tratamento odontológico criado com sucesso",
                    content = @Content(schema = @Schema(implementation = TratamentosOdontologicosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<TratamentosOdontologicosResponse> criar(@Valid @RequestBody TratamentosOdontologicosRequest request) {
        TratamentosOdontologicosResponse response = tratamentosOdontologicosService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar tratamentos odontológicos", description = "Retorna uma lista paginada de tratamentos odontológicos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tratamentos odontológicos retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<TratamentosOdontologicosResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<TratamentosOdontologicosResponse> response = tratamentosOdontologicosService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter tratamento odontológico por ID", description = "Retorna um tratamento odontológico específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tratamento odontológico encontrado",
                    content = @Content(schema = @Schema(implementation = TratamentosOdontologicosResponse.class))),
            @ApiResponse(responseCode = "404", description = "Tratamento odontológico não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<TratamentosOdontologicosResponse> obterPorId(
            @Parameter(description = "ID do tratamento odontológico", required = true)
            @PathVariable UUID id) {
        TratamentosOdontologicosResponse response = tratamentosOdontologicosService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar tratamento odontológico", description = "Atualiza um tratamento odontológico existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tratamento odontológico atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = TratamentosOdontologicosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Tratamento odontológico não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<TratamentosOdontologicosResponse> atualizar(
            @Parameter(description = "ID do tratamento odontológico", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody TratamentosOdontologicosRequest request) {
        TratamentosOdontologicosResponse response = tratamentosOdontologicosService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir tratamento odontológico", description = "Exclui (desativa) um tratamento odontológico do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tratamento odontológico excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tratamento odontológico não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do tratamento odontológico", required = true)
            @PathVariable UUID id) {
        tratamentosOdontologicosService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

