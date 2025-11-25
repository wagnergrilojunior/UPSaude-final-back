package com.upsaude.controller;

import com.upsaude.api.request.AlergiasPacienteRequest;
import com.upsaude.api.response.AlergiasPacienteResponse;
import com.upsaude.service.AlergiasPacienteService;
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
 * Controlador REST para operações relacionadas a Alergias de Paciente.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/alergias-paciente")
@Tag(name = "Alergias de Paciente", description = "API para gerenciamento de Alergias de Paciente")
@RequiredArgsConstructor
public class AlergiasPacienteController {

    private final AlergiasPacienteService alergiasPacienteService;

    @PostMapping
    @Operation(summary = "Criar nova alergia de paciente", description = "Cria uma nova alergia de paciente no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alergia de paciente criada com sucesso",
                    content = @Content(schema = @Schema(implementation = AlergiasPacienteResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AlergiasPacienteResponse> criar(@Valid @RequestBody AlergiasPacienteRequest request) {
        AlergiasPacienteResponse response = alergiasPacienteService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar alergias de paciente", description = "Retorna uma lista paginada de alergias de paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de alergias de paciente retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<AlergiasPacienteResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<AlergiasPacienteResponse> response = alergiasPacienteService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter alergia de paciente por ID", description = "Retorna uma alergia de paciente específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alergia de paciente encontrada",
                    content = @Content(schema = @Schema(implementation = AlergiasPacienteResponse.class))),
            @ApiResponse(responseCode = "404", description = "Alergia de paciente não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AlergiasPacienteResponse> obterPorId(
            @Parameter(description = "ID da alergia de paciente", required = true)
            @PathVariable UUID id) {
        AlergiasPacienteResponse response = alergiasPacienteService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar alergia de paciente", description = "Atualiza uma alergia de paciente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alergia de paciente atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = AlergiasPacienteResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Alergia de paciente não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AlergiasPacienteResponse> atualizar(
            @Parameter(description = "ID da alergia de paciente", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody AlergiasPacienteRequest request) {
        AlergiasPacienteResponse response = alergiasPacienteService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir alergia de paciente", description = "Exclui (desativa) uma alergia de paciente do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Alergia de paciente excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Alergia de paciente não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da alergia de paciente", required = true)
            @PathVariable UUID id) {
        alergiasPacienteService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

