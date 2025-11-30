package com.upsaude.controller;

import com.upsaude.api.request.EducacaoSaudeRequest;
import com.upsaude.api.response.EducacaoSaudeResponse;
import com.upsaude.service.EducacaoSaudeService;
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
 * Controlador REST para operações relacionadas a Educação em Saúde.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/educacao-saude")
@Tag(name = "Educação em Saúde", description = "API para gerenciamento de ações de educação em saúde")
@RequiredArgsConstructor
public class EducacaoSaudeController {

    private final EducacaoSaudeService educacaoSaudeService;

    @PostMapping
    @Operation(summary = "Criar nova ação de educação em saúde", description = "Cria uma nova ação de educação em saúde no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Educação em saúde criada com sucesso",
                    content = @Content(schema = @Schema(implementation = EducacaoSaudeResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EducacaoSaudeResponse> criar(@Valid @RequestBody EducacaoSaudeRequest request) {
        EducacaoSaudeResponse response = educacaoSaudeService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar ações de educação em saúde", description = "Retorna uma lista paginada de ações de educação em saúde")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ações de educação em saúde retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<EducacaoSaudeResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<EducacaoSaudeResponse> response = educacaoSaudeService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter ação de educação em saúde por ID", description = "Retorna uma ação de educação em saúde específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Educação em saúde encontrada",
                    content = @Content(schema = @Schema(implementation = EducacaoSaudeResponse.class))),
            @ApiResponse(responseCode = "404", description = "Educação em saúde não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EducacaoSaudeResponse> obterPorId(
            @Parameter(description = "ID da ação de educação em saúde", required = true)
            @PathVariable UUID id) {
        EducacaoSaudeResponse response = educacaoSaudeService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/estabelecimento/{estabelecimentoId}")
    @Operation(summary = "Listar ações de educação em saúde por estabelecimento", description = "Retorna uma lista paginada de ações de educação em saúde por estabelecimento")
    public ResponseEntity<Page<EducacaoSaudeResponse>> listarPorEstabelecimento(
            @PathVariable UUID estabelecimentoId,
            Pageable pageable) {
        Page<EducacaoSaudeResponse> response = educacaoSaudeService.listarPorEstabelecimento(estabelecimentoId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profissional/{profissionalId}")
    @Operation(summary = "Listar ações de educação em saúde por profissional", description = "Retorna uma lista paginada de ações de educação em saúde por profissional responsável")
    public ResponseEntity<Page<EducacaoSaudeResponse>> listarPorProfissionalResponsavel(
            @PathVariable UUID profissionalId,
            Pageable pageable) {
        Page<EducacaoSaudeResponse> response = educacaoSaudeService.listarPorProfissionalResponsavel(profissionalId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/realizadas/{estabelecimentoId}")
    @Operation(summary = "Listar ações de educação em saúde realizadas", description = "Retorna uma lista paginada de ações de educação em saúde já realizadas")
    public ResponseEntity<Page<EducacaoSaudeResponse>> listarRealizadas(
            @PathVariable UUID estabelecimentoId,
            Pageable pageable) {
        Page<EducacaoSaudeResponse> response = educacaoSaudeService.listarRealizadas(estabelecimentoId, pageable);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar ação de educação em saúde", description = "Atualiza uma ação de educação em saúde existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Educação em saúde atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = EducacaoSaudeResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Educação em saúde não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EducacaoSaudeResponse> atualizar(
            @Parameter(description = "ID da ação de educação em saúde", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody EducacaoSaudeRequest request) {
        EducacaoSaudeResponse response = educacaoSaudeService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir ação de educação em saúde", description = "Exclui (desativa) uma ação de educação em saúde do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Educação em saúde excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Educação em saúde não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da ação de educação em saúde", required = true)
            @PathVariable UUID id) {
        educacaoSaudeService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

