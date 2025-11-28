package com.upsaude.controller;

import com.upsaude.api.request.DadosClinicosBasicosRequest;
import com.upsaude.api.response.DadosClinicosBasicosResponse;
import com.upsaude.service.DadosClinicosBasicosService;
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
@RequestMapping("/v1/dados-clinicos-basicos")
@Tag(name = "Dados Clínicos Básicos", description = "API para gerenciamento de Dados Clínicos Básicos")
@RequiredArgsConstructor
public class DadosClinicosBasicosController {

    private final DadosClinicosBasicosService service;

    @PostMapping
    @Operation(summary = "Criar dados clínicos básicos", description = "Cria novos dados clínicos básicos para um paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dados clínicos básicos criados com sucesso",
                    content = @Content(schema = @Schema(implementation = DadosClinicosBasicosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DadosClinicosBasicosResponse> criar(@Valid @RequestBody DadosClinicosBasicosRequest request) {
        DadosClinicosBasicosResponse response = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar dados clínicos básicos", description = "Retorna uma lista paginada de dados clínicos básicos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<DadosClinicosBasicosResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<DadosClinicosBasicosResponse> response = service.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter por ID", description = "Retorna dados clínicos básicos específicos pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados encontrados",
                    content = @Content(schema = @Schema(implementation = DadosClinicosBasicosResponse.class))),
            @ApiResponse(responseCode = "404", description = "Dados não encontrados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DadosClinicosBasicosResponse> obterPorId(
            @Parameter(description = "ID dos dados clínicos básicos", required = true)
            @PathVariable UUID id) {
        DadosClinicosBasicosResponse response = service.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Obter por paciente ID", description = "Retorna dados clínicos básicos de um paciente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados encontrados",
                    content = @Content(schema = @Schema(implementation = DadosClinicosBasicosResponse.class))),
            @ApiResponse(responseCode = "404", description = "Dados não encontrados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DadosClinicosBasicosResponse> obterPorPacienteId(
            @Parameter(description = "ID do paciente", required = true)
            @PathVariable UUID pacienteId) {
        DadosClinicosBasicosResponse response = service.obterPorPacienteId(pacienteId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados clínicos básicos", description = "Atualiza dados clínicos básicos existentes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados atualizados com sucesso",
                    content = @Content(schema = @Schema(implementation = DadosClinicosBasicosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Dados não encontrados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DadosClinicosBasicosResponse> atualizar(
            @Parameter(description = "ID dos dados clínicos básicos", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody DadosClinicosBasicosRequest request) {
        DadosClinicosBasicosResponse response = service.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir dados clínicos básicos", description = "Exclui (desativa) dados clínicos básicos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Dados excluídos com sucesso"),
            @ApiResponse(responseCode = "404", description = "Dados não encontrados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID dos dados clínicos básicos", required = true)
            @PathVariable UUID id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

