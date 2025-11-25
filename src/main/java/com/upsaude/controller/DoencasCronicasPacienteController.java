package com.upsaude.controller;

import com.upsaude.api.request.DoencasCronicasPacienteRequest;
import com.upsaude.api.response.DoencasCronicasPacienteResponse;
import com.upsaude.service.DoencasCronicasPacienteService;
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
 * Controlador REST para operações relacionadas a Doenças Crônicas de Paciente.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/doencas-cronicas-paciente")
@Tag(name = "Doenças Crônicas de Paciente", description = "API para gerenciamento de Doenças Crônicas de Paciente")
@RequiredArgsConstructor
public class DoencasCronicasPacienteController {

    private final DoencasCronicasPacienteService doencasCronicasPacienteService;

    @PostMapping
    @Operation(summary = "Criar nova doença crônica de paciente", description = "Cria uma nova doença crônica de paciente no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Doença crônica de paciente criada com sucesso",
                    content = @Content(schema = @Schema(implementation = DoencasCronicasPacienteResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DoencasCronicasPacienteResponse> criar(@Valid @RequestBody DoencasCronicasPacienteRequest request) {
        DoencasCronicasPacienteResponse response = doencasCronicasPacienteService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar doenças crônicas de paciente", description = "Retorna uma lista paginada de doenças crônicas de paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de doenças crônicas de paciente retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<DoencasCronicasPacienteResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<DoencasCronicasPacienteResponse> response = doencasCronicasPacienteService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter doença crônica de paciente por ID", description = "Retorna uma doença crônica de paciente específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doença crônica de paciente encontrada",
                    content = @Content(schema = @Schema(implementation = DoencasCronicasPacienteResponse.class))),
            @ApiResponse(responseCode = "404", description = "Doença crônica de paciente não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DoencasCronicasPacienteResponse> obterPorId(
            @Parameter(description = "ID da doença crônica de paciente", required = true)
            @PathVariable UUID id) {
        DoencasCronicasPacienteResponse response = doencasCronicasPacienteService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar doença crônica de paciente", description = "Atualiza uma doença crônica de paciente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doença crônica de paciente atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = DoencasCronicasPacienteResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Doença crônica de paciente não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DoencasCronicasPacienteResponse> atualizar(
            @Parameter(description = "ID da doença crônica de paciente", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody DoencasCronicasPacienteRequest request) {
        DoencasCronicasPacienteResponse response = doencasCronicasPacienteService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir doença crônica de paciente", description = "Exclui (desativa) uma doença crônica de paciente do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Doença crônica de paciente excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Doença crônica de paciente não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da doença crônica de paciente", required = true)
            @PathVariable UUID id) {
        doencasCronicasPacienteService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

