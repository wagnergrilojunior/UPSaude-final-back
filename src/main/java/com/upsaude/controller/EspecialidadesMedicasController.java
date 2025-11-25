package com.upsaude.controller;

import com.upsaude.api.request.EspecialidadesMedicasRequest;
import com.upsaude.api.response.EspecialidadesMedicasResponse;
import com.upsaude.service.EspecialidadesMedicasService;
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
 * Controlador REST para operações relacionadas a Especialidades Médicas.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/especialidades-medicas")
@Tag(name = "Especialidades Médicas", description = "API para gerenciamento de Especialidades Médicas")
@RequiredArgsConstructor
public class EspecialidadesMedicasController {

    private final EspecialidadesMedicasService especialidadesMedicasService;

    @PostMapping
    @Operation(summary = "Criar nova especialidade médica", description = "Cria uma nova especialidade médica no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Especialidade médica criada com sucesso",
                    content = @Content(schema = @Schema(implementation = EspecialidadesMedicasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EspecialidadesMedicasResponse> criar(@Valid @RequestBody EspecialidadesMedicasRequest request) {
        EspecialidadesMedicasResponse response = especialidadesMedicasService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar especialidades médicas", description = "Retorna uma lista paginada de especialidades médicas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de especialidades médicas retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<EspecialidadesMedicasResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<EspecialidadesMedicasResponse> response = especialidadesMedicasService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter especialidade médica por ID", description = "Retorna uma especialidade médica específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Especialidade médica encontrada",
                    content = @Content(schema = @Schema(implementation = EspecialidadesMedicasResponse.class))),
            @ApiResponse(responseCode = "404", description = "Especialidade médica não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EspecialidadesMedicasResponse> obterPorId(
            @Parameter(description = "ID da especialidade médica", required = true)
            @PathVariable UUID id) {
        EspecialidadesMedicasResponse response = especialidadesMedicasService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar especialidade médica", description = "Atualiza uma especialidade médica existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Especialidade médica atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = EspecialidadesMedicasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Especialidade médica não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EspecialidadesMedicasResponse> atualizar(
            @Parameter(description = "ID da especialidade médica", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody EspecialidadesMedicasRequest request) {
        EspecialidadesMedicasResponse response = especialidadesMedicasService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir especialidade médica", description = "Exclui (desativa) uma especialidade médica do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Especialidade médica excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Especialidade médica não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da especialidade médica", required = true)
            @PathVariable UUID id) {
        especialidadesMedicasService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

