package com.upsaude.controller;

import com.upsaude.api.request.ControlePontoRequest;
import com.upsaude.api.response.ControlePontoResponse;
import com.upsaude.service.ControlePontoService;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Controlador REST para operações relacionadas a ControlePonto.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/controle-ponto")
@Tag(name = "Controle de Ponto", description = "API para gerenciamento de Controle de Ponto")
@RequiredArgsConstructor
public class ControlePontoController {

    private final ControlePontoService controlePontoService;

    @PostMapping
    @Operation(summary = "Criar novo registro de ponto", description = "Cria um novo registro de ponto no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registro de ponto criado com sucesso",
                    content = @Content(schema = @Schema(implementation = ControlePontoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ControlePontoResponse> criar(@Valid @RequestBody ControlePontoRequest request) {
        ControlePontoResponse response = controlePontoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar registros de ponto", description = "Retorna uma lista paginada de registros de ponto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de registros de ponto retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ControlePontoResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<ControlePontoResponse> response = controlePontoService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profissional/{profissionalId}")
    @Operation(summary = "Listar registros de ponto por profissional", description = "Retorna uma lista paginada de registros de ponto de um profissional específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de registros de ponto do profissional retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID do profissional inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ControlePontoResponse>> listarPorProfissional(
            @Parameter(description = "ID do profissional de saúde", required = true)
            @PathVariable UUID profissionalId,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<ControlePontoResponse> response = controlePontoService.listarPorProfissional(profissionalId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/medico/{medicoId}")
    @Operation(summary = "Listar registros de ponto por médico", description = "Retorna uma lista paginada de registros de ponto de um médico específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de registros de ponto do médico retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID do médico inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ControlePontoResponse>> listarPorMedico(
            @Parameter(description = "ID do médico", required = true)
            @PathVariable UUID medicoId,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<ControlePontoResponse> response = controlePontoService.listarPorMedico(medicoId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/estabelecimento/{estabelecimentoId}")
    @Operation(summary = "Listar registros de ponto por estabelecimento", description = "Retorna uma lista paginada de registros de ponto de um estabelecimento específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de registros de ponto do estabelecimento retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID do estabelecimento inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ControlePontoResponse>> listarPorEstabelecimento(
            @Parameter(description = "ID do estabelecimento", required = true)
            @PathVariable UUID estabelecimentoId,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<ControlePontoResponse> response = controlePontoService.listarPorEstabelecimento(estabelecimentoId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profissional/{profissionalId}/data/{data}")
    @Operation(summary = "Listar registros de ponto por profissional e data", description = "Retorna uma lista paginada de registros de ponto de um profissional em uma data específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de registros de ponto retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ControlePontoResponse>> listarPorProfissionalEData(
            @Parameter(description = "ID do profissional de saúde", required = true)
            @PathVariable UUID profissionalId,
            @Parameter(description = "Data do ponto", required = true)
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<ControlePontoResponse> response = controlePontoService.listarPorProfissionalEData(profissionalId, data, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profissional/{profissionalId}/periodo")
    @Operation(summary = "Listar registros de ponto por profissional e período", description = "Retorna uma lista paginada de registros de ponto de um profissional em um período")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de registros de ponto retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ControlePontoResponse>> listarPorProfissionalEPeriodo(
            @Parameter(description = "ID do profissional de saúde", required = true)
            @PathVariable UUID profissionalId,
            @Parameter(description = "Data de início do período", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @Parameter(description = "Data fim do período", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<ControlePontoResponse> response = controlePontoService.listarPorProfissionalEPeriodo(profissionalId, dataInicio, dataFim, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter registro de ponto por ID", description = "Retorna um registro de ponto específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro de ponto encontrado",
                    content = @Content(schema = @Schema(implementation = ControlePontoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Registro de ponto não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ControlePontoResponse> obterPorId(
            @Parameter(description = "ID do registro de ponto", required = true)
            @PathVariable UUID id) {
        ControlePontoResponse response = controlePontoService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar registro de ponto", description = "Atualiza um registro de ponto existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro de ponto atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = ControlePontoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Registro de ponto não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ControlePontoResponse> atualizar(
            @Parameter(description = "ID do registro de ponto", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody ControlePontoRequest request) {
        ControlePontoResponse response = controlePontoService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir registro de ponto", description = "Exclui (desativa) um registro de ponto do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Registro de ponto excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Registro de ponto não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do registro de ponto", required = true)
            @PathVariable UUID id) {
        controlePontoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

