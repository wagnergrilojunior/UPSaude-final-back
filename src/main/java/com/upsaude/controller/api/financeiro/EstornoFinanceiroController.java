package com.upsaude.controller.api.financeiro;

import com.upsaude.api.request.financeiro.EstornoFinanceiroRequest;
import com.upsaude.api.response.financeiro.EstornoFinanceiroResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.financeiro.EstornoFinanceiroService;
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
@RequestMapping("/v1/financeiro/estornos")
@Tag(name = "Financeiro - Estornos", description = "API para gerenciamento de Estorno Financeiro")
@RequiredArgsConstructor
@Slf4j
public class EstornoFinanceiroController {

    private final EstornoFinanceiroService service;

    @PostMapping
    @Operation(summary = "Criar estorno financeiro", description = "Cria um novo estorno financeiro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estorno criado com sucesso",
                    content = @Content(schema = @Schema(implementation = EstornoFinanceiroResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EstornoFinanceiroResponse> criar(@Valid @RequestBody EstornoFinanceiroRequest request) {
        log.debug("REQUEST POST /v1/financeiro/estornos - payload: {}", request);
        try {
            EstornoFinanceiroResponse response = service.criar(request);
            log.info("Estorno financeiro criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar estorno financeiro — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar estorno financeiro — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar estornos", description = "Retorna uma lista paginada de estornos financeiros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<EstornoFinanceiroResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/financeiro/estornos - pageable: {}", pageable);
        try {
            return ResponseEntity.ok(service.listar(pageable));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar estornos — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter estorno por ID", description = "Retorna um estorno financeiro específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estorno encontrado",
                    content = @Content(schema = @Schema(implementation = EstornoFinanceiroResponse.class))),
            @ApiResponse(responseCode = "404", description = "Estorno não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EstornoFinanceiroResponse> obterPorId(
            @Parameter(description = "ID do estorno", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/financeiro/estornos/{}", id);
        try {
            return ResponseEntity.ok(service.obterPorId(id));
        } catch (NotFoundException ex) {
            log.warn("Estorno não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter estorno — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar estorno", description = "Atualiza um estorno financeiro existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estorno atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = EstornoFinanceiroResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Estorno não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EstornoFinanceiroResponse> atualizar(
            @Parameter(description = "ID do estorno", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody EstornoFinanceiroRequest request) {
        log.debug("REQUEST PUT /v1/financeiro/estornos/{} - payload: {}", id, request);
        try {
            EstornoFinanceiroResponse response = service.atualizar(id, request);
            log.info("Estorno financeiro atualizado com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar estorno — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar estorno — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir estorno", description = "Exclui um estorno financeiro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Estorno excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estorno não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do estorno", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/financeiro/estornos/{}", id);
        try {
            service.excluir(id);
            log.info("Estorno financeiro excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Estorno não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir estorno — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/inativar")
    @Operation(summary = "Inativar estorno", description = "Inativa um estorno financeiro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Estorno inativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estorno não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> inativar(
            @Parameter(description = "ID do estorno", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST PUT /v1/financeiro/estornos/{}/inativar", id);
        try {
            service.inativar(id);
            log.info("Estorno financeiro inativado com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Estorno não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inativar estorno — ID: {}", id, ex);
            throw ex;
        }
    }
}

