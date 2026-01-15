package com.upsaude.controller.api.financeiro;

import com.upsaude.api.request.financeiro.ConciliacaoItemRequest;
import com.upsaude.api.response.financeiro.ConciliacaoItemResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.financeiro.ConciliacaoItemService;
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
@RequestMapping("/api/v1/financeiro/conciliacoes-itens")
@Tag(name = "Financeiro - Itens de Conciliação", description = "API para gerenciamento de Itens de Conciliação")
@RequiredArgsConstructor
@Slf4j
public class ConciliacaoItemController {

    private final ConciliacaoItemService service;

    @PostMapping
    @Operation(summary = "Criar item de conciliação", description = "Cria um novo item de conciliação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item criado com sucesso",
                    content = @Content(schema = @Schema(implementation = ConciliacaoItemResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ConciliacaoItemResponse> criar(@Valid @RequestBody ConciliacaoItemRequest request) {
        log.debug("REQUEST POST /api/v1/financeiro/conciliacoes-itens - payload: {}", request);
        try {
            ConciliacaoItemResponse response = service.criar(request);
            log.info("Item de conciliação criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar item de conciliação — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar item de conciliação — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar itens de conciliação", description = "Retorna uma lista paginada de itens de conciliação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ConciliacaoItemResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /api/v1/financeiro/conciliacoes-itens - pageable: {}", pageable);
        try {
            return ResponseEntity.ok(service.listar(pageable));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar itens de conciliação — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter item por ID", description = "Retorna um item de conciliação específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item encontrado",
                    content = @Content(schema = @Schema(implementation = ConciliacaoItemResponse.class))),
            @ApiResponse(responseCode = "404", description = "Item não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ConciliacaoItemResponse> obterPorId(
            @Parameter(description = "ID do item", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /api/v1/financeiro/conciliacoes-itens/{}", id);
        try {
            return ResponseEntity.ok(service.obterPorId(id));
        } catch (NotFoundException ex) {
            log.warn("Item não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter item de conciliação — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar item", description = "Atualiza um item de conciliação existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = ConciliacaoItemResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ConciliacaoItemResponse> atualizar(
            @Parameter(description = "ID do item", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody ConciliacaoItemRequest request) {
        log.debug("REQUEST PUT /api/v1/financeiro/conciliacoes-itens/{} - payload: {}", id, request);
        try {
            ConciliacaoItemResponse response = service.atualizar(id, request);
            log.info("Item de conciliação atualizado com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar item — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar item — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir item", description = "Exclui um item de conciliação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do item", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /api/v1/financeiro/conciliacoes-itens/{}", id);
        try {
            service.excluir(id);
            log.info("Item de conciliação excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Item não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir item de conciliação — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/inativar")
    @Operation(summary = "Inativar item", description = "Inativa um item de conciliação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item inativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> inativar(
            @Parameter(description = "ID do item", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST PUT /api/v1/financeiro/conciliacoes-itens/{}/inativar", id);
        try {
            service.inativar(id);
            log.info("Item de conciliação inativado com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Item não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inativar item de conciliação — ID: {}", id, ex);
            throw ex;
        }
    }
}

