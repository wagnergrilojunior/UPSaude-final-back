package com.upsaude.controller;

import com.upsaude.api.request.ResponsavelLegalRequest;
import com.upsaude.api.response.ResponsavelLegalResponse;
import com.upsaude.service.ResponsavelLegalService;
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
@RequestMapping("/v1/responsaveis-legais")
@Tag(name = "Responsáveis Legais", description = "API para gerenciamento de Responsáveis Legais")
@RequiredArgsConstructor
public class ResponsavelLegalController {

    private final ResponsavelLegalService service;

    @PostMapping
    @Operation(summary = "Criar responsável legal", description = "Cria novo responsável legal para um paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Responsável legal criado com sucesso",
                    content = @Content(schema = @Schema(implementation = ResponsavelLegalResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ResponsavelLegalResponse> criar(@Valid @RequestBody ResponsavelLegalRequest request) {
        ResponsavelLegalResponse response = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar responsáveis legais", description = "Retorna uma lista paginada de responsáveis legais")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ResponsavelLegalResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<ResponsavelLegalResponse> response = service.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter por ID", description = "Retorna responsável legal específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Responsável legal encontrado",
                    content = @Content(schema = @Schema(implementation = ResponsavelLegalResponse.class))),
            @ApiResponse(responseCode = "404", description = "Responsável legal não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ResponsavelLegalResponse> obterPorId(
            @Parameter(description = "ID do responsável legal", required = true)
            @PathVariable UUID id) {
        ResponsavelLegalResponse response = service.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Obter por paciente ID", description = "Retorna responsável legal de um paciente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Responsável legal encontrado",
                    content = @Content(schema = @Schema(implementation = ResponsavelLegalResponse.class))),
            @ApiResponse(responseCode = "404", description = "Responsável legal não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ResponsavelLegalResponse> obterPorPacienteId(
            @Parameter(description = "ID do paciente", required = true)
            @PathVariable UUID pacienteId) {
        ResponsavelLegalResponse response = service.obterPorPacienteId(pacienteId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar responsável legal", description = "Atualiza responsável legal existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Responsável legal atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = ResponsavelLegalResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Responsável legal não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ResponsavelLegalResponse> atualizar(
            @Parameter(description = "ID do responsável legal", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody ResponsavelLegalRequest request) {
        ResponsavelLegalResponse response = service.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir responsável legal", description = "Exclui (desativa) responsável legal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Responsável legal excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Responsável legal não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do responsável legal", required = true)
            @PathVariable UUID id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

