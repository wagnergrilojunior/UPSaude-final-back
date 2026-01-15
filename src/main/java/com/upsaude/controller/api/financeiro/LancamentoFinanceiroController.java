package com.upsaude.controller.api.financeiro;

import com.upsaude.api.request.financeiro.LancamentoFinanceiroRequest;
import com.upsaude.api.response.financeiro.LancamentoFinanceiroResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.financeiro.LancamentoFinanceiroService;
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
@RequestMapping("/api/v1/financeiro/lancamentos")
@Tag(name = "Financeiro - Lançamentos", description = "API para gerenciamento de Lançamentos Financeiros (débito/crédito/estornos)")
@RequiredArgsConstructor
@Slf4j
public class LancamentoFinanceiroController {

    private final LancamentoFinanceiroService service;

    @PostMapping
    @Operation(summary = "Criar lançamento financeiro", description = "Cria um novo lançamento financeiro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Lançamento criado com sucesso",
                    content = @Content(schema = @Schema(implementation = LancamentoFinanceiroResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<LancamentoFinanceiroResponse> criar(@Valid @RequestBody LancamentoFinanceiroRequest request) {
        log.debug("REQUEST POST /api/v1/financeiro/lancamentos - payload: {}", request);
        try {
            LancamentoFinanceiroResponse response = service.criar(request);
            log.info("Lançamento financeiro criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar lançamento financeiro — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar lançamento financeiro — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar lançamentos", description = "Retorna uma lista paginada de lançamentos financeiros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<LancamentoFinanceiroResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /api/v1/financeiro/lancamentos - pageable: {}", pageable);
        try {
            return ResponseEntity.ok(service.listar(pageable));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar lançamentos — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter lançamento por ID", description = "Retorna um lançamento financeiro específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lançamento encontrado",
                    content = @Content(schema = @Schema(implementation = LancamentoFinanceiroResponse.class))),
            @ApiResponse(responseCode = "404", description = "Lançamento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<LancamentoFinanceiroResponse> obterPorId(
            @Parameter(description = "ID do lançamento", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /api/v1/financeiro/lancamentos/{}", id);
        try {
            return ResponseEntity.ok(service.obterPorId(id));
        } catch (NotFoundException ex) {
            log.warn("Lançamento não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter lançamento — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar lançamento", description = "Atualiza um lançamento financeiro existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lançamento atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = LancamentoFinanceiroResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Lançamento não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<LancamentoFinanceiroResponse> atualizar(
            @Parameter(description = "ID do lançamento", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody LancamentoFinanceiroRequest request) {
        log.debug("REQUEST PUT /api/v1/financeiro/lancamentos/{} - payload: {}", id, request);
        try {
            LancamentoFinanceiroResponse response = service.atualizar(id, request);
            log.info("Lançamento financeiro atualizado com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar lançamento — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar lançamento — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir lançamento", description = "Exclui um lançamento financeiro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Lançamento excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Lançamento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do lançamento", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /api/v1/financeiro/lancamentos/{}", id);
        try {
            service.excluir(id);
            log.info("Lançamento financeiro excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Lançamento não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir lançamento — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/inativar")
    @Operation(summary = "Inativar lançamento", description = "Inativa um lançamento financeiro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Lançamento inativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Lançamento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> inativar(
            @Parameter(description = "ID do lançamento", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST PUT /api/v1/financeiro/lancamentos/{}/inativar", id);
        try {
            service.inativar(id);
            log.info("Lançamento financeiro inativado com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Lançamento não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inativar lançamento — ID: {}", id, ex);
            throw ex;
        }
    }
}

