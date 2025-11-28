package com.upsaude.controller;

import com.upsaude.api.request.LGPDConsentimentoRequest;
import com.upsaude.api.response.LGPDConsentimentoResponse;
import com.upsaude.service.LGPDConsentimentoService;
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
@RequestMapping("/v1/lgpd-consentimentos")
@Tag(name = "LGPD Consentimentos", description = "API para gerenciamento de Consentimentos LGPD")
@RequiredArgsConstructor
public class LGPDConsentimentoController {

    private final LGPDConsentimentoService service;

    @PostMapping
    @Operation(summary = "Criar consentimento LGPD", description = "Cria novo consentimento LGPD para um paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Consentimento LGPD criado com sucesso",
                    content = @Content(schema = @Schema(implementation = LGPDConsentimentoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<LGPDConsentimentoResponse> criar(@Valid @RequestBody LGPDConsentimentoRequest request) {
        LGPDConsentimentoResponse response = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar consentimentos LGPD", description = "Retorna uma lista paginada de consentimentos LGPD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<LGPDConsentimentoResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<LGPDConsentimentoResponse> response = service.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter por ID", description = "Retorna consentimento LGPD específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consentimento encontrado",
                    content = @Content(schema = @Schema(implementation = LGPDConsentimentoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Consentimento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<LGPDConsentimentoResponse> obterPorId(
            @Parameter(description = "ID do consentimento LGPD", required = true)
            @PathVariable UUID id) {
        LGPDConsentimentoResponse response = service.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Obter por paciente ID", description = "Retorna consentimento LGPD de um paciente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consentimento encontrado",
                    content = @Content(schema = @Schema(implementation = LGPDConsentimentoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Consentimento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<LGPDConsentimentoResponse> obterPorPacienteId(
            @Parameter(description = "ID do paciente", required = true)
            @PathVariable UUID pacienteId) {
        LGPDConsentimentoResponse response = service.obterPorPacienteId(pacienteId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar consentimento LGPD", description = "Atualiza consentimento LGPD existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consentimento atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = LGPDConsentimentoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Consentimento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<LGPDConsentimentoResponse> atualizar(
            @Parameter(description = "ID do consentimento LGPD", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody LGPDConsentimentoRequest request) {
        LGPDConsentimentoResponse response = service.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir consentimento LGPD", description = "Exclui (desativa) consentimento LGPD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Consentimento excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Consentimento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do consentimento LGPD", required = true)
            @PathVariable UUID id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

