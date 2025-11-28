package com.upsaude.controller;

import com.upsaude.api.request.MedicacaoPacienteRequest;
import com.upsaude.api.response.MedicacaoPacienteResponse;
import com.upsaude.service.MedicacaoPacienteService;
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
 * Controlador REST para operações relacionadas a Medicações de Paciente.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/medicacoes-paciente")
@Tag(name = "Medicações de Paciente", description = "API para gerenciamento de Medicações de Paciente")
@RequiredArgsConstructor
public class MedicacaoPacienteController {

    private final MedicacaoPacienteService medicacaoPacienteService;

    @PostMapping
    @Operation(summary = "Criar nova ligação paciente-medicação", description = "Cria uma nova ligação entre paciente e medicação no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ligação paciente-medicação criada com sucesso",
                    content = @Content(schema = @Schema(implementation = MedicacaoPacienteResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MedicacaoPacienteResponse> criar(@Valid @RequestBody MedicacaoPacienteRequest request) {
        MedicacaoPacienteResponse response = medicacaoPacienteService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar ligações paciente-medicação", description = "Retorna uma lista paginada de ligações paciente-medicação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ligações paciente-medicação retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<MedicacaoPacienteResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<MedicacaoPacienteResponse> response = medicacaoPacienteService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter ligação paciente-medicação por ID", description = "Retorna uma ligação paciente-medicação específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ligação paciente-medicação encontrada",
                    content = @Content(schema = @Schema(implementation = MedicacaoPacienteResponse.class))),
            @ApiResponse(responseCode = "404", description = "Ligação paciente-medicação não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MedicacaoPacienteResponse> obterPorId(
            @Parameter(description = "ID da ligação paciente-medicação", required = true)
            @PathVariable UUID id) {
        MedicacaoPacienteResponse response = medicacaoPacienteService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar ligação paciente-medicação", description = "Atualiza uma ligação paciente-medicação existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ligação paciente-medicação atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = MedicacaoPacienteResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Ligação paciente-medicação não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MedicacaoPacienteResponse> atualizar(
            @Parameter(description = "ID da ligação paciente-medicação", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody MedicacaoPacienteRequest request) {
        MedicacaoPacienteResponse response = medicacaoPacienteService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir ligação paciente-medicação", description = "Exclui (desativa) uma ligação paciente-medicação do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ligação paciente-medicação excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ligação paciente-medicação não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da ligação paciente-medicação", required = true)
            @PathVariable UUID id) {
        medicacaoPacienteService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

