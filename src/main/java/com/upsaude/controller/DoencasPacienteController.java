package com.upsaude.controller;

import com.upsaude.api.request.DoencasPacienteRequest;
import com.upsaude.api.response.DoencasPacienteResponse;
import com.upsaude.service.DoencasPacienteService;
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
 * Controlador REST para operações relacionadas a Doenças de Paciente.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/doencas-paciente")
@Tag(name = "Doenças de Paciente", description = "API para gerenciamento de Doenças de Paciente")
@RequiredArgsConstructor
public class DoencasPacienteController {

    private final DoencasPacienteService doencasPacienteService;

    @PostMapping
    @Operation(summary = "Criar novo registro de doença do paciente", description = "Cria um novo registro de doença do paciente no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registro de doença do paciente criado com sucesso",
                    content = @Content(schema = @Schema(implementation = DoencasPacienteResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DoencasPacienteResponse> criar(@Valid @RequestBody DoencasPacienteRequest request) {
        DoencasPacienteResponse response = doencasPacienteService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar registros de doenças do paciente", description = "Retorna uma lista paginada de registros de doenças do paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de registros retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<DoencasPacienteResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<DoencasPacienteResponse> response = doencasPacienteService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Listar doenças por paciente", description = "Retorna uma lista paginada de doenças de um paciente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de doenças do paciente retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID do paciente inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<DoencasPacienteResponse>> listarPorPaciente(
            @Parameter(description = "ID do paciente", required = true)
            @PathVariable UUID pacienteId,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<DoencasPacienteResponse> response = doencasPacienteService.listarPorPaciente(pacienteId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/doenca/{doencaId}")
    @Operation(summary = "Listar pacientes por doença", description = "Retorna uma lista paginada de pacientes que têm uma doença específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pacientes retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID da doença inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<DoencasPacienteResponse>> listarPorDoenca(
            @Parameter(description = "ID da doença", required = true)
            @PathVariable UUID doencaId,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<DoencasPacienteResponse> response = doencasPacienteService.listarPorDoenca(doencaId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter registro de doença do paciente por ID", description = "Retorna um registro específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro encontrado",
                    content = @Content(schema = @Schema(implementation = DoencasPacienteResponse.class))),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DoencasPacienteResponse> obterPorId(
            @Parameter(description = "ID do registro", required = true)
            @PathVariable UUID id) {
        DoencasPacienteResponse response = doencasPacienteService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar registro de doença do paciente", description = "Atualiza um registro existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = DoencasPacienteResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DoencasPacienteResponse> atualizar(
            @Parameter(description = "ID do registro", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody DoencasPacienteRequest request) {
        DoencasPacienteResponse response = doencasPacienteService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir registro de doença do paciente", description = "Exclui (desativa) um registro do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Registro excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do registro", required = true)
            @PathVariable UUID id) {
        doencasPacienteService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

