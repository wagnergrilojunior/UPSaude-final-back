package com.upsaude.controller;

import com.upsaude.api.request.AtendimentoRequest;
import com.upsaude.api.response.AtendimentoResponse;
import com.upsaude.service.AtendimentoService;
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
 * Controlador REST para operações relacionadas a Atendimentos.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/atendimentos")
@Tag(name = "Atendimentos", description = "API para gerenciamento de Atendimentos")
@RequiredArgsConstructor
public class AtendimentoController {

    private final AtendimentoService atendimentoService;

    @PostMapping
    @Operation(summary = "Criar novo atendimento", description = "Cria um novo atendimento no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Atendimento criado com sucesso",
                    content = @Content(schema = @Schema(implementation = AtendimentoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AtendimentoResponse> criar(@Valid @RequestBody AtendimentoRequest request) {
        AtendimentoResponse response = atendimentoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar atendimentos", description = "Retorna uma lista paginada de atendimentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de atendimentos retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<AtendimentoResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<AtendimentoResponse> response = atendimentoService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Listar atendimentos por paciente", description = "Retorna uma lista paginada de atendimentos de um paciente específico, ordenados por data/hora decrescente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de atendimentos do paciente retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID do paciente inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<AtendimentoResponse>> listarPorPaciente(
            @Parameter(description = "ID do paciente", required = true)
            @PathVariable UUID pacienteId,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<AtendimentoResponse> response = atendimentoService.listarPorPaciente(pacienteId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profissional/{profissionalId}")
    @Operation(summary = "Listar atendimentos por profissional", description = "Retorna uma lista paginada de atendimentos realizados por um profissional específico, ordenados por data/hora decrescente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de atendimentos do profissional retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID do profissional inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<AtendimentoResponse>> listarPorProfissional(
            @Parameter(description = "ID do profissional de saúde", required = true)
            @PathVariable UUID profissionalId,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<AtendimentoResponse> response = atendimentoService.listarPorProfissional(profissionalId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter atendimento por ID", description = "Retorna um atendimento específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atendimento encontrado",
                    content = @Content(schema = @Schema(implementation = AtendimentoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Atendimento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AtendimentoResponse> obterPorId(
            @Parameter(description = "ID do atendimento", required = true)
            @PathVariable UUID id) {
        AtendimentoResponse response = atendimentoService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar atendimento", description = "Atualiza um atendimento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atendimento atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = AtendimentoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Atendimento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AtendimentoResponse> atualizar(
            @Parameter(description = "ID do atendimento", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody AtendimentoRequest request) {
        AtendimentoResponse response = atendimentoService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir atendimento", description = "Exclui (desativa) um atendimento do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Atendimento excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Atendimento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do atendimento", required = true)
            @PathVariable UUID id) {
        atendimentoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

