package com.upsaude.controller;

import com.upsaude.api.request.IntegracaoGovRequest;
import com.upsaude.api.response.IntegracaoGovResponse;
import com.upsaude.service.IntegracaoGovService;
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
@RequestMapping("/v1/integracao-gov")
@Tag(name = "Integração Governamental", description = "API para gerenciamento de Integração Governamental")
@RequiredArgsConstructor
public class IntegracaoGovController {

    private final IntegracaoGovService service;

    @PostMapping
    @Operation(summary = "Criar integração gov", description = "Cria nova integração governamental para um paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Integração gov criada com sucesso",
                    content = @Content(schema = @Schema(implementation = IntegracaoGovResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<IntegracaoGovResponse> criar(@Valid @RequestBody IntegracaoGovRequest request) {
        IntegracaoGovResponse response = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar integrações gov", description = "Retorna uma lista paginada de integrações governamentais")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<IntegracaoGovResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<IntegracaoGovResponse> response = service.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter por ID", description = "Retorna integração gov específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Integração encontrada",
                    content = @Content(schema = @Schema(implementation = IntegracaoGovResponse.class))),
            @ApiResponse(responseCode = "404", description = "Integração não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<IntegracaoGovResponse> obterPorId(
            @Parameter(description = "ID da integração gov", required = true)
            @PathVariable UUID id) {
        IntegracaoGovResponse response = service.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Obter por paciente ID", description = "Retorna integração gov de um paciente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Integração encontrada",
                    content = @Content(schema = @Schema(implementation = IntegracaoGovResponse.class))),
            @ApiResponse(responseCode = "404", description = "Integração não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<IntegracaoGovResponse> obterPorPacienteId(
            @Parameter(description = "ID do paciente", required = true)
            @PathVariable UUID pacienteId) {
        IntegracaoGovResponse response = service.obterPorPacienteId(pacienteId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar integração gov", description = "Atualiza integração governamental existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Integração atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = IntegracaoGovResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Integração não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<IntegracaoGovResponse> atualizar(
            @Parameter(description = "ID da integração gov", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody IntegracaoGovRequest request) {
        IntegracaoGovResponse response = service.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir integração gov", description = "Exclui (desativa) integração governamental")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Integração excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Integração não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da integração gov", required = true)
            @PathVariable UUID id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

