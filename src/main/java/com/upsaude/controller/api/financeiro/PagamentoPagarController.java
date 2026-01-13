package com.upsaude.controller.api.financeiro;

import com.upsaude.api.request.financeiro.PagamentoPagarRequest;
import com.upsaude.api.response.financeiro.PagamentoPagarResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.financeiro.PagamentoPagarService;
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
@RequestMapping("/v1/financeiro/pagamentos-pagar")
@Tag(name = "Financeiro - Pagamentos (Pagar)", description = "API para gerenciamento de Pagamentos de Títulos a Pagar")
@RequiredArgsConstructor
@Slf4j
public class PagamentoPagarController {

    private final PagamentoPagarService service;

    @PostMapping
    @Operation(summary = "Criar pagamento", description = "Cria um novo pagamento para um título a pagar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pagamento criado com sucesso",
                    content = @Content(schema = @Schema(implementation = PagamentoPagarResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<PagamentoPagarResponse> criar(@Valid @RequestBody PagamentoPagarRequest request) {
        log.debug("REQUEST POST /v1/financeiro/pagamentos-pagar - payload: {}", request);
        try {
            PagamentoPagarResponse response = service.criar(request);
            log.info("Pagamento criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar pagamento — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar pagamento — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar pagamentos", description = "Retorna uma lista paginada de pagamentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<PagamentoPagarResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/financeiro/pagamentos-pagar - pageable: {}", pageable);
        try {
            return ResponseEntity.ok(service.listar(pageable));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar pagamentos — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter pagamento por ID", description = "Retorna um pagamento específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pagamento encontrado",
                    content = @Content(schema = @Schema(implementation = PagamentoPagarResponse.class))),
            @ApiResponse(responseCode = "404", description = "Pagamento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<PagamentoPagarResponse> obterPorId(
            @Parameter(description = "ID do pagamento", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/financeiro/pagamentos-pagar/{}", id);
        try {
            return ResponseEntity.ok(service.obterPorId(id));
        } catch (NotFoundException ex) {
            log.warn("Pagamento não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter pagamento — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar pagamento", description = "Atualiza um pagamento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pagamento atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = PagamentoPagarResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Pagamento não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<PagamentoPagarResponse> atualizar(
            @Parameter(description = "ID do pagamento", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody PagamentoPagarRequest request) {
        log.debug("REQUEST PUT /v1/financeiro/pagamentos-pagar/{} - payload: {}", id, request);
        try {
            PagamentoPagarResponse response = service.atualizar(id, request);
            log.info("Pagamento atualizado com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar pagamento — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar pagamento — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir pagamento", description = "Exclui um pagamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pagamento excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pagamento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do pagamento", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/financeiro/pagamentos-pagar/{}", id);
        try {
            service.excluir(id);
            log.info("Pagamento excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Pagamento não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir pagamento — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/inativar")
    @Operation(summary = "Inativar pagamento", description = "Inativa um pagamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pagamento inativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pagamento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> inativar(
            @Parameter(description = "ID do pagamento", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST PUT /v1/financeiro/pagamentos-pagar/{}/inativar", id);
        try {
            service.inativar(id);
            log.info("Pagamento inativado com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Pagamento não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inativar pagamento — ID: {}", id, ex);
            throw ex;
        }
    }
}

