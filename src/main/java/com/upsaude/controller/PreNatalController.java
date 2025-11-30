package com.upsaude.controller;

import com.upsaude.api.request.PreNatalRequest;
import com.upsaude.api.response.PreNatalResponse;
import com.upsaude.service.PreNatalService;
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

import java.util.List;
import java.util.UUID;

/**
 * Controlador REST para operações relacionadas a Pré-Natal.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/pre-natal")
@Tag(name = "Pré-Natal", description = "API para gerenciamento de acompanhamento Pré-Natal")
@RequiredArgsConstructor
public class PreNatalController {

    private final PreNatalService preNatalService;

    @PostMapping
    @Operation(summary = "Criar novo pré-natal", description = "Cria um novo acompanhamento pré-natal no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pré-natal criado com sucesso",
                    content = @Content(schema = @Schema(implementation = PreNatalResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<PreNatalResponse> criar(@Valid @RequestBody PreNatalRequest request) {
        PreNatalResponse response = preNatalService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar pré-natais", description = "Retorna uma lista paginada de pré-natais")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pré-natais retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<PreNatalResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<PreNatalResponse> response = preNatalService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter pré-natal por ID", description = "Retorna um pré-natal específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pré-natal encontrado",
                    content = @Content(schema = @Schema(implementation = PreNatalResponse.class))),
            @ApiResponse(responseCode = "404", description = "Pré-natal não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<PreNatalResponse> obterPorId(
            @Parameter(description = "ID do pré-natal", required = true)
            @PathVariable UUID id) {
        PreNatalResponse response = preNatalService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/estabelecimento/{estabelecimentoId}")
    @Operation(summary = "Listar pré-natais por estabelecimento", description = "Retorna uma lista paginada de pré-natais por estabelecimento")
    public ResponseEntity<Page<PreNatalResponse>> listarPorEstabelecimento(
            @PathVariable UUID estabelecimentoId,
            Pageable pageable) {
        Page<PreNatalResponse> response = preNatalService.listarPorEstabelecimento(estabelecimentoId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Listar pré-natais por paciente", description = "Retorna uma lista de pré-natais por paciente")
    public ResponseEntity<List<PreNatalResponse>> listarPorPaciente(
            @PathVariable UUID pacienteId) {
        List<PreNatalResponse> response = preNatalService.listarPorPaciente(pacienteId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/em-acompanhamento/{estabelecimentoId}")
    @Operation(summary = "Listar pré-natais em acompanhamento", description = "Retorna uma lista paginada de pré-natais em acompanhamento")
    public ResponseEntity<Page<PreNatalResponse>> listarEmAcompanhamento(
            @PathVariable UUID estabelecimentoId,
            Pageable pageable) {
        Page<PreNatalResponse> response = preNatalService.listarEmAcompanhamento(estabelecimentoId, pageable);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar pré-natal", description = "Atualiza um pré-natal existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pré-natal atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = PreNatalResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Pré-natal não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<PreNatalResponse> atualizar(
            @Parameter(description = "ID do pré-natal", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody PreNatalRequest request) {
        PreNatalResponse response = preNatalService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir pré-natal", description = "Exclui (desativa) um pré-natal do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pré-natal excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pré-natal não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do pré-natal", required = true)
            @PathVariable UUID id) {
        preNatalService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

