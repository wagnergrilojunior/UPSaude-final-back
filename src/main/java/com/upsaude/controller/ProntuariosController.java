package com.upsaude.controller;

import com.upsaude.api.request.ProntuariosRequest;
import com.upsaude.api.response.ProntuariosResponse;
import com.upsaude.service.ProntuariosService;
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
 * Controlador REST para operações relacionadas a Prontuários.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/prontuarios")
@Tag(name = "Prontuários", description = "API para gerenciamento de Prontuários")
@RequiredArgsConstructor
public class ProntuariosController {

    private final ProntuariosService prontuariosService;

    @PostMapping
    @Operation(summary = "Criar novo prontuário", description = "Cria um novo prontuário no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Prontuário criado com sucesso",
                    content = @Content(schema = @Schema(implementation = ProntuariosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ProntuariosResponse> criar(@Valid @RequestBody ProntuariosRequest request) {
        ProntuariosResponse response = prontuariosService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar prontuários", description = "Retorna uma lista paginada de prontuários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de prontuários retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ProntuariosResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<ProntuariosResponse> response = prontuariosService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter prontuário por ID", description = "Retorna um prontuário específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prontuário encontrado",
                    content = @Content(schema = @Schema(implementation = ProntuariosResponse.class))),
            @ApiResponse(responseCode = "404", description = "Prontuário não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ProntuariosResponse> obterPorId(
            @Parameter(description = "ID do prontuário", required = true)
            @PathVariable UUID id) {
        ProntuariosResponse response = prontuariosService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar prontuário", description = "Atualiza um prontuário existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prontuário atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = ProntuariosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Prontuário não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ProntuariosResponse> atualizar(
            @Parameter(description = "ID do prontuário", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody ProntuariosRequest request) {
        ProntuariosResponse response = prontuariosService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir prontuário", description = "Exclui (desativa) um prontuário do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Prontuário excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Prontuário não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do prontuário", required = true)
            @PathVariable UUID id) {
        prontuariosService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

