package com.upsaude.controller.api.financeiro;

import com.upsaude.api.request.financeiro.BaixaReceberRequest;
import com.upsaude.api.response.financeiro.BaixaReceberResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.financeiro.BaixaReceberService;
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

@RestController
@RequestMapping("/v1/financeiro/baixas-receber")
@Tag(name = "Financeiro - Baixas (Receber)", description = "API para gerenciamento de Baixas de Títulos a Receber")
@RequiredArgsConstructor
@Slf4j
public class BaixaReceberController {

    private final BaixaReceberService service;

    @PostMapping
    @Operation(summary = "Criar baixa a receber", description = "Cria uma nova baixa para um título a receber")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Baixa criada com sucesso",
                    content = @Content(schema = @Schema(implementation = BaixaReceberResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<BaixaReceberResponse> criar(@Valid @RequestBody BaixaReceberRequest request) {
        log.debug("REQUEST POST /v1/financeiro/baixas-receber - payload: {}", request);
        try {
            BaixaReceberResponse response = service.criar(request);
            log.info("Baixa a receber criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar baixa a receber — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar baixa a receber — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar baixas a receber", description = "Retorna uma lista paginada de baixas a receber")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<BaixaReceberResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/financeiro/baixas-receber - pageable: {}", pageable);
        try {
            return ResponseEntity.ok(service.listar(pageable));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar baixas a receber — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter baixa por ID", description = "Retorna uma baixa a receber específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Baixa encontrada",
                    content = @Content(schema = @Schema(implementation = BaixaReceberResponse.class))),
            @ApiResponse(responseCode = "404", description = "Baixa não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<BaixaReceberResponse> obterPorId(
            @Parameter(description = "ID da baixa", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/financeiro/baixas-receber/{}", id);
        try {
            return ResponseEntity.ok(service.obterPorId(id));
        } catch (NotFoundException ex) {
            log.warn("Baixa não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter baixa a receber — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar baixa", description = "Atualiza uma baixa a receber existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Baixa atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = BaixaReceberResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Baixa não encontrada"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<BaixaReceberResponse> atualizar(
            @Parameter(description = "ID da baixa", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody BaixaReceberRequest request) {
        log.debug("REQUEST PUT /v1/financeiro/baixas-receber/{} - payload: {}", id, request);
        try {
            BaixaReceberResponse response = service.atualizar(id, request);
            log.info("Baixa a receber atualizada com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar baixa — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar baixa — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir baixa", description = "Exclui uma baixa a receber")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Baixa excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Baixa não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da baixa", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/financeiro/baixas-receber/{}", id);
        try {
            service.excluir(id);
            log.info("Baixa a receber excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Baixa não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir baixa — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/inativar")
    @Operation(summary = "Inativar baixa", description = "Inativa uma baixa a receber")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Baixa inativada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Baixa não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> inativar(
            @Parameter(description = "ID da baixa", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST PUT /v1/financeiro/baixas-receber/{}/inativar", id);
        try {
            service.inativar(id);
            log.info("Baixa a receber inativada com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Baixa não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inativar baixa — ID: {}", id, ex);
            throw ex;
        }
    }
}

