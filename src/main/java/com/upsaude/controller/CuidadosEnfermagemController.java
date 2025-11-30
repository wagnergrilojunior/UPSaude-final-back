package com.upsaude.controller;

import com.upsaude.api.request.CuidadosEnfermagemRequest;
import com.upsaude.api.response.CuidadosEnfermagemResponse;
import com.upsaude.service.CuidadosEnfermagemService;
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
 * Controlador REST para operações relacionadas a Cuidados de Enfermagem.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/cuidados-enfermagem")
@Tag(name = "Cuidados de Enfermagem", description = "API para gerenciamento de cuidados e procedimentos de enfermagem")
@RequiredArgsConstructor
public class CuidadosEnfermagemController {

    private final CuidadosEnfermagemService cuidadosEnfermagemService;

    @PostMapping
    @Operation(summary = "Criar novo cuidado de enfermagem", description = "Cria um novo registro de cuidado de enfermagem no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cuidado de enfermagem criado com sucesso",
                    content = @Content(schema = @Schema(implementation = CuidadosEnfermagemResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CuidadosEnfermagemResponse> criar(@Valid @RequestBody CuidadosEnfermagemRequest request) {
        CuidadosEnfermagemResponse response = cuidadosEnfermagemService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar cuidados de enfermagem", description = "Retorna uma lista paginada de cuidados de enfermagem")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cuidados de enfermagem retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<CuidadosEnfermagemResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<CuidadosEnfermagemResponse> response = cuidadosEnfermagemService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter cuidado de enfermagem por ID", description = "Retorna um cuidado de enfermagem específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuidado de enfermagem encontrado",
                    content = @Content(schema = @Schema(implementation = CuidadosEnfermagemResponse.class))),
            @ApiResponse(responseCode = "404", description = "Cuidado de enfermagem não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CuidadosEnfermagemResponse> obterPorId(
            @Parameter(description = "ID do cuidado de enfermagem", required = true)
            @PathVariable UUID id) {
        CuidadosEnfermagemResponse response = cuidadosEnfermagemService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/estabelecimento/{estabelecimentoId}")
    @Operation(summary = "Listar cuidados de enfermagem por estabelecimento", description = "Retorna uma lista paginada de cuidados de enfermagem por estabelecimento")
    public ResponseEntity<Page<CuidadosEnfermagemResponse>> listarPorEstabelecimento(
            @PathVariable UUID estabelecimentoId,
            Pageable pageable) {
        Page<CuidadosEnfermagemResponse> response = cuidadosEnfermagemService.listarPorEstabelecimento(estabelecimentoId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Listar cuidados de enfermagem por paciente", description = "Retorna uma lista paginada de cuidados de enfermagem por paciente")
    public ResponseEntity<Page<CuidadosEnfermagemResponse>> listarPorPaciente(
            @PathVariable UUID pacienteId,
            Pageable pageable) {
        Page<CuidadosEnfermagemResponse> response = cuidadosEnfermagemService.listarPorPaciente(pacienteId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profissional/{profissionalId}")
    @Operation(summary = "Listar cuidados de enfermagem por profissional", description = "Retorna uma lista paginada de cuidados de enfermagem por profissional")
    public ResponseEntity<Page<CuidadosEnfermagemResponse>> listarPorProfissional(
            @PathVariable UUID profissionalId,
            Pageable pageable) {
        Page<CuidadosEnfermagemResponse> response = cuidadosEnfermagemService.listarPorProfissional(profissionalId, pageable);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cuidado de enfermagem", description = "Atualiza um cuidado de enfermagem existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuidado de enfermagem atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = CuidadosEnfermagemResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Cuidado de enfermagem não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CuidadosEnfermagemResponse> atualizar(
            @Parameter(description = "ID do cuidado de enfermagem", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody CuidadosEnfermagemRequest request) {
        CuidadosEnfermagemResponse response = cuidadosEnfermagemService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir cuidado de enfermagem", description = "Exclui (desativa) um cuidado de enfermagem do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cuidado de enfermagem excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cuidado de enfermagem não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do cuidado de enfermagem", required = true)
            @PathVariable UUID id) {
        cuidadosEnfermagemService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

