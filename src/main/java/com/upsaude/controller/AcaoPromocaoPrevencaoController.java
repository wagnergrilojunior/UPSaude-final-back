package com.upsaude.controller;

import com.upsaude.api.request.AcaoPromocaoPrevencaoRequest;
import com.upsaude.api.response.AcaoPromocaoPrevencaoResponse;
import com.upsaude.service.AcaoPromocaoPrevencaoService;
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
 * Controlador REST para operações relacionadas a Ações de Promoção e Prevenção.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/acoes-promocao-prevencao")
@Tag(name = "Ações de Promoção e Prevenção", description = "API para gerenciamento de programas e ações de promoção e prevenção em saúde")
@RequiredArgsConstructor
public class AcaoPromocaoPrevencaoController {

    private final AcaoPromocaoPrevencaoService acaoPromocaoPrevencaoService;

    @PostMapping
    @Operation(summary = "Criar nova ação de promoção e prevenção", description = "Cria uma nova ação de promoção e prevenção no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ação de promoção e prevenção criada com sucesso",
                    content = @Content(schema = @Schema(implementation = AcaoPromocaoPrevencaoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AcaoPromocaoPrevencaoResponse> criar(@Valid @RequestBody AcaoPromocaoPrevencaoRequest request) {
        AcaoPromocaoPrevencaoResponse response = acaoPromocaoPrevencaoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar ações de promoção e prevenção", description = "Retorna uma lista paginada de ações de promoção e prevenção")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ações de promoção e prevenção retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<AcaoPromocaoPrevencaoResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<AcaoPromocaoPrevencaoResponse> response = acaoPromocaoPrevencaoService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter ação de promoção e prevenção por ID", description = "Retorna uma ação de promoção e prevenção específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ação de promoção e prevenção encontrada",
                    content = @Content(schema = @Schema(implementation = AcaoPromocaoPrevencaoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Ação de promoção e prevenção não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AcaoPromocaoPrevencaoResponse> obterPorId(
            @Parameter(description = "ID da ação de promoção e prevenção", required = true)
            @PathVariable UUID id) {
        AcaoPromocaoPrevencaoResponse response = acaoPromocaoPrevencaoService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/estabelecimento/{estabelecimentoId}")
    @Operation(summary = "Listar ações de promoção e prevenção por estabelecimento", description = "Retorna uma lista paginada de ações de promoção e prevenção por estabelecimento")
    public ResponseEntity<Page<AcaoPromocaoPrevencaoResponse>> listarPorEstabelecimento(
            @PathVariable UUID estabelecimentoId,
            Pageable pageable) {
        Page<AcaoPromocaoPrevencaoResponse> response = acaoPromocaoPrevencaoService.listarPorEstabelecimento(estabelecimentoId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profissional/{profissionalId}")
    @Operation(summary = "Listar ações de promoção e prevenção por profissional", description = "Retorna uma lista paginada de ações de promoção e prevenção por profissional responsável")
    public ResponseEntity<Page<AcaoPromocaoPrevencaoResponse>> listarPorProfissionalResponsavel(
            @PathVariable UUID profissionalId,
            Pageable pageable) {
        Page<AcaoPromocaoPrevencaoResponse> response = acaoPromocaoPrevencaoService.listarPorProfissionalResponsavel(profissionalId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{status}/estabelecimento/{estabelecimentoId}")
    @Operation(summary = "Listar ações de promoção e prevenção por status", description = "Retorna uma lista paginada de ações de promoção e prevenção por status")
    public ResponseEntity<Page<AcaoPromocaoPrevencaoResponse>> listarPorStatus(
            @PathVariable String status,
            @PathVariable UUID estabelecimentoId,
            Pageable pageable) {
        Page<AcaoPromocaoPrevencaoResponse> response = acaoPromocaoPrevencaoService.listarPorStatus(status, estabelecimentoId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/continuas/{estabelecimentoId}")
    @Operation(summary = "Listar ações de promoção e prevenção contínuas", description = "Retorna uma lista paginada de ações de promoção e prevenção contínuas")
    public ResponseEntity<Page<AcaoPromocaoPrevencaoResponse>> listarContinuas(
            @PathVariable UUID estabelecimentoId,
            Pageable pageable) {
        Page<AcaoPromocaoPrevencaoResponse> response = acaoPromocaoPrevencaoService.listarContinuas(estabelecimentoId, pageable);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar ação de promoção e prevenção", description = "Atualiza uma ação de promoção e prevenção existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ação de promoção e prevenção atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = AcaoPromocaoPrevencaoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Ação de promoção e prevenção não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AcaoPromocaoPrevencaoResponse> atualizar(
            @Parameter(description = "ID da ação de promoção e prevenção", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody AcaoPromocaoPrevencaoRequest request) {
        AcaoPromocaoPrevencaoResponse response = acaoPromocaoPrevencaoService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir ação de promoção e prevenção", description = "Exclui (desativa) uma ação de promoção e prevenção do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ação de promoção e prevenção excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ação de promoção e prevenção não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da ação de promoção e prevenção", required = true)
            @PathVariable UUID id) {
        acaoPromocaoPrevencaoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

