package com.upsaude.controller;

import com.upsaude.api.request.DadosSociodemograficosRequest;
import com.upsaude.api.response.DadosSociodemograficosResponse;
import com.upsaude.service.DadosSociodemograficosService;
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

@RestController
@RequestMapping("/v1/dados-sociodemograficos")
@Tag(name = "Dados Sociodemográficos", description = "API para gerenciamento de Dados Sociodemográficos")
@RequiredArgsConstructor
public class DadosSociodemograficosController {

    private final DadosSociodemograficosService service;

    @PostMapping
    @Operation(summary = "Criar dados sociodemográficos", description = "Cria novos dados sociodemográficos para um paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dados sociodemográficos criados com sucesso",
                    content = @Content(schema = @Schema(implementation = DadosSociodemograficosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DadosSociodemograficosResponse> criar(@Valid @RequestBody DadosSociodemograficosRequest request) {
        DadosSociodemograficosResponse response = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar dados sociodemográficos", description = "Retorna uma lista paginada de dados sociodemográficos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<DadosSociodemograficosResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<DadosSociodemograficosResponse> response = service.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter por ID", description = "Retorna dados sociodemográficos específicos pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados encontrados",
                    content = @Content(schema = @Schema(implementation = DadosSociodemograficosResponse.class))),
            @ApiResponse(responseCode = "404", description = "Dados não encontrados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DadosSociodemograficosResponse> obterPorId(
            @Parameter(description = "ID dos dados sociodemográficos", required = true)
            @PathVariable UUID id) {
        DadosSociodemograficosResponse response = service.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Obter por paciente ID", description = "Retorna dados sociodemográficos de um paciente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados encontrados",
                    content = @Content(schema = @Schema(implementation = DadosSociodemograficosResponse.class))),
            @ApiResponse(responseCode = "404", description = "Dados não encontrados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DadosSociodemograficosResponse> obterPorPacienteId(
            @Parameter(description = "ID do paciente", required = true)
            @PathVariable UUID pacienteId) {
        DadosSociodemograficosResponse response = service.obterPorPacienteId(pacienteId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados sociodemográficos", description = "Atualiza dados sociodemográficos existentes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados atualizados com sucesso",
                    content = @Content(schema = @Schema(implementation = DadosSociodemograficosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Dados não encontrados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DadosSociodemograficosResponse> atualizar(
            @Parameter(description = "ID dos dados sociodemográficos", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody DadosSociodemograficosRequest request) {
        DadosSociodemograficosResponse response = service.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir dados sociodemográficos", description = "Exclui (desativa) dados sociodemográficos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Dados excluídos com sucesso"),
            @ApiResponse(responseCode = "404", description = "Dados não encontrados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID dos dados sociodemográficos", required = true)
            @PathVariable UUID id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

