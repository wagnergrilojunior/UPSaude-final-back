package com.upsaude.controller;

import com.upsaude.api.request.DeficienciasPacienteRequest;
import com.upsaude.api.response.DeficienciasPacienteResponse;
import com.upsaude.service.DeficienciasPacienteService;
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
 * Controlador REST para operações relacionadas a Deficiências de Paciente.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/deficiencias-paciente")
@Tag(name = "Deficiências de Paciente", description = "API para gerenciamento de Deficiências de Paciente")
@RequiredArgsConstructor
public class DeficienciasPacienteController {

    private final DeficienciasPacienteService deficienciasPacienteService;

    @PostMapping
    @Operation(summary = "Criar nova ligação paciente-deficiência", description = "Cria uma nova ligação entre paciente e deficiência no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ligação paciente-deficiência criada com sucesso",
                    content = @Content(schema = @Schema(implementation = DeficienciasPacienteResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DeficienciasPacienteResponse> criar(@Valid @RequestBody DeficienciasPacienteRequest request) {
        DeficienciasPacienteResponse response = deficienciasPacienteService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar ligações paciente-deficiência", description = "Retorna uma lista paginada de ligações paciente-deficiência")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ligações paciente-deficiência retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<DeficienciasPacienteResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<DeficienciasPacienteResponse> response = deficienciasPacienteService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter ligação paciente-deficiência por ID", description = "Retorna uma ligação paciente-deficiência específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ligação paciente-deficiência encontrada",
                    content = @Content(schema = @Schema(implementation = DeficienciasPacienteResponse.class))),
            @ApiResponse(responseCode = "404", description = "Ligação paciente-deficiência não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DeficienciasPacienteResponse> obterPorId(
            @Parameter(description = "ID da ligação paciente-deficiência", required = true)
            @PathVariable UUID id) {
        DeficienciasPacienteResponse response = deficienciasPacienteService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar ligação paciente-deficiência", description = "Atualiza uma ligação paciente-deficiência existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ligação paciente-deficiência atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = DeficienciasPacienteResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Ligação paciente-deficiência não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DeficienciasPacienteResponse> atualizar(
            @Parameter(description = "ID da ligação paciente-deficiência", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody DeficienciasPacienteRequest request) {
        DeficienciasPacienteResponse response = deficienciasPacienteService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir ligação paciente-deficiência", description = "Exclui (desativa) uma ligação paciente-deficiência do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ligação paciente-deficiência excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ligação paciente-deficiência não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da ligação paciente-deficiência", required = true)
            @PathVariable UUID id) {
        deficienciasPacienteService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

