package com.upsaude.controller;

import com.upsaude.api.request.MedicacoesContinuasPacienteRequest;
import com.upsaude.api.response.MedicacoesContinuasPacienteResponse;
import com.upsaude.service.MedicacoesContinuasPacienteService;
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
 * Controlador REST para operações relacionadas a Medicações Contínuas de Paciente.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/api/v1/medicacoes-continuas-paciente")
@Tag(name = "Medicações Contínuas de Paciente", description = "API para gerenciamento de Medicações Contínuas de Paciente")
@RequiredArgsConstructor
public class MedicacoesContinuasPacienteController {

    private final MedicacoesContinuasPacienteService medicacoesContinuasPacienteService;

    @PostMapping
    @Operation(summary = "Criar nova medicação contínua de paciente", description = "Cria uma nova medicação contínua de paciente no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Medicação contínua de paciente criada com sucesso",
                    content = @Content(schema = @Schema(implementation = MedicacoesContinuasPacienteResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MedicacoesContinuasPacienteResponse> criar(@Valid @RequestBody MedicacoesContinuasPacienteRequest request) {
        MedicacoesContinuasPacienteResponse response = medicacoesContinuasPacienteService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar medicações contínuas de paciente", description = "Retorna uma lista paginada de medicações contínuas de paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de medicações contínuas de paciente retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<MedicacoesContinuasPacienteResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<MedicacoesContinuasPacienteResponse> response = medicacoesContinuasPacienteService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter medicação contínua de paciente por ID", description = "Retorna uma medicação contínua de paciente específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicação contínua de paciente encontrada",
                    content = @Content(schema = @Schema(implementation = MedicacoesContinuasPacienteResponse.class))),
            @ApiResponse(responseCode = "404", description = "Medicação contínua de paciente não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MedicacoesContinuasPacienteResponse> obterPorId(
            @Parameter(description = "ID da medicação contínua de paciente", required = true)
            @PathVariable UUID id) {
        MedicacoesContinuasPacienteResponse response = medicacoesContinuasPacienteService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar medicação contínua de paciente", description = "Atualiza uma medicação contínua de paciente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicação contínua de paciente atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = MedicacoesContinuasPacienteResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Medicação contínua de paciente não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MedicacoesContinuasPacienteResponse> atualizar(
            @Parameter(description = "ID da medicação contínua de paciente", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody MedicacoesContinuasPacienteRequest request) {
        MedicacoesContinuasPacienteResponse response = medicacoesContinuasPacienteService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir medicação contínua de paciente", description = "Exclui (desativa) uma medicação contínua de paciente do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Medicação contínua de paciente excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Medicação contínua de paciente não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da medicação contínua de paciente", required = true)
            @PathVariable UUID id) {
        medicacoesContinuasPacienteService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

