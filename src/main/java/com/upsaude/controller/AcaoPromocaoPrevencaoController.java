package com.upsaude.controller;

import com.upsaude.api.request.AcaoPromocaoPrevencaoRequest;
import com.upsaude.api.response.AcaoPromocaoPrevencaoResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
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
        log.debug("REQUEST POST /v1/acoes-promocao-prevencao - payload: {}", request);
        try {
            AcaoPromocaoPrevencaoResponse response = acaoPromocaoPrevencaoService.criar(request);
            log.info("Ação de promoção e prevenção criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar ação de promoção e prevenção — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar ação de promoção e prevenção — payload: {}", request, ex);
            throw ex;
        }
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
        log.debug("REQUEST GET /v1/acoes-promocao-prevencao - pageable: {}", pageable);
        try {
            Page<AcaoPromocaoPrevencaoResponse> response = acaoPromocaoPrevencaoService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar ações de promoção e prevenção — pageable: {}", pageable, ex);
            throw ex;
        }
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
        log.debug("REQUEST GET /v1/acoes-promocao-prevencao/{}", id);
        try {
            AcaoPromocaoPrevencaoResponse response = acaoPromocaoPrevencaoService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Ação de promoção e prevenção não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter ação de promoção e prevenção por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @GetMapping("/estabelecimento/{estabelecimentoId}")
    @Operation(summary = "Listar ações de promoção e prevenção por estabelecimento", description = "Retorna uma lista paginada de ações de promoção e prevenção por estabelecimento")
    public ResponseEntity<Page<AcaoPromocaoPrevencaoResponse>> listarPorEstabelecimento(
            @PathVariable UUID estabelecimentoId,
            Pageable pageable) {
        log.debug("REQUEST GET /v1/acoes-promocao-prevencao/estabelecimento/{} - pageable: {}", estabelecimentoId, pageable);
        try {
            Page<AcaoPromocaoPrevencaoResponse> response = acaoPromocaoPrevencaoService.listarPorEstabelecimento(estabelecimentoId, pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar ações de promoção e prevenção por estabelecimento — estabelecimentoId: {}, pageable: {}", estabelecimentoId, pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/profissional/{profissionalId}")
    @Operation(summary = "Listar ações de promoção e prevenção por profissional", description = "Retorna uma lista paginada de ações de promoção e prevenção por profissional responsável")
    public ResponseEntity<Page<AcaoPromocaoPrevencaoResponse>> listarPorProfissionalResponsavel(
            @PathVariable UUID profissionalId,
            Pageable pageable) {
        log.debug("REQUEST GET /v1/acoes-promocao-prevencao/profissional/{} - pageable: {}", profissionalId, pageable);
        try {
            Page<AcaoPromocaoPrevencaoResponse> response = acaoPromocaoPrevencaoService.listarPorProfissionalResponsavel(profissionalId, pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar ações de promoção e prevenção por profissional — profissionalId: {}, pageable: {}", profissionalId, pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/status/{status}/estabelecimento/{estabelecimentoId}")
    @Operation(summary = "Listar ações de promoção e prevenção por status", description = "Retorna uma lista paginada de ações de promoção e prevenção por status")
    public ResponseEntity<Page<AcaoPromocaoPrevencaoResponse>> listarPorStatus(
            @PathVariable String status,
            @PathVariable UUID estabelecimentoId,
            Pageable pageable) {
        log.debug("REQUEST GET /v1/acoes-promocao-prevencao/status/{}/estabelecimento/{} - pageable: {}", status, estabelecimentoId, pageable);
        try {
            Page<AcaoPromocaoPrevencaoResponse> response = acaoPromocaoPrevencaoService.listarPorStatus(status, estabelecimentoId, pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar ações de promoção e prevenção por status — status: {}, estabelecimentoId: {}, pageable: {}", status, estabelecimentoId, pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/continuas/{estabelecimentoId}")
    @Operation(summary = "Listar ações de promoção e prevenção contínuas", description = "Retorna uma lista paginada de ações de promoção e prevenção contínuas")
    public ResponseEntity<Page<AcaoPromocaoPrevencaoResponse>> listarContinuas(
            @PathVariable UUID estabelecimentoId,
            Pageable pageable) {
        log.debug("REQUEST GET /v1/acoes-promocao-prevencao/continuas/{} - pageable: {}", estabelecimentoId, pageable);
        try {
            Page<AcaoPromocaoPrevencaoResponse> response = acaoPromocaoPrevencaoService.listarContinuas(estabelecimentoId, pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar ações de promoção e prevenção contínuas — estabelecimentoId: {}, pageable: {}", estabelecimentoId, pageable, ex);
            throw ex;
        }
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
        log.debug("REQUEST PUT /v1/acoes-promocao-prevencao/{} - payload: {}", id, request);
        try {
            AcaoPromocaoPrevencaoResponse response = acaoPromocaoPrevencaoService.atualizar(id, request);
            log.info("Ação de promoção e prevenção atualizada com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar ação de promoção e prevenção — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar ação de promoção e prevenção — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
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
        log.debug("REQUEST DELETE /v1/acoes-promocao-prevencao/{}", id);
        try {
            acaoPromocaoPrevencaoService.excluir(id);
            log.info("Ação de promoção e prevenção excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Falha ao excluir ação de promoção e prevenção — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir ação de promoção e prevenção — ID: {}", id, ex);
            throw ex;
        }
    }
}
