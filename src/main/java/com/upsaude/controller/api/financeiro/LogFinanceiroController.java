package com.upsaude.controller.api.financeiro;

import com.upsaude.api.request.financeiro.LogFinanceiroRequest;
import com.upsaude.api.response.financeiro.LogFinanceiroResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.financeiro.LogFinanceiroService;
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
@RequestMapping("/v1/financeiro/logs")
@Tag(name = "Financeiro - Logs", description = "API para gerenciamento de Logs Financeiros (auditoria)")
@RequiredArgsConstructor
@Slf4j
public class LogFinanceiroController {

    private final LogFinanceiroService service;

    @PostMapping
    @Operation(summary = "Criar log financeiro", description = "Cria um novo log financeiro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Log criado com sucesso",
                    content = @Content(schema = @Schema(implementation = LogFinanceiroResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<LogFinanceiroResponse> criar(@Valid @RequestBody LogFinanceiroRequest request) {
        log.debug("REQUEST POST /v1/financeiro/logs - payload: {}", request);
        try {
            LogFinanceiroResponse response = service.criar(request);
            log.info("Log financeiro criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar log financeiro — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar log financeiro — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar logs", description = "Retorna uma lista paginada de logs financeiros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<LogFinanceiroResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/financeiro/logs - pageable: {}", pageable);
        try {
            return ResponseEntity.ok(service.listar(pageable));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar logs financeiros — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter log por ID", description = "Retorna um log financeiro específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Log encontrado",
                    content = @Content(schema = @Schema(implementation = LogFinanceiroResponse.class))),
            @ApiResponse(responseCode = "404", description = "Log não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<LogFinanceiroResponse> obterPorId(
            @Parameter(description = "ID do log", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/financeiro/logs/{}", id);
        try {
            return ResponseEntity.ok(service.obterPorId(id));
        } catch (NotFoundException ex) {
            log.warn("Log não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter log financeiro — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar log", description = "Atualiza um log financeiro existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Log atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = LogFinanceiroResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Log não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<LogFinanceiroResponse> atualizar(
            @Parameter(description = "ID do log", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody LogFinanceiroRequest request) {
        log.debug("REQUEST PUT /v1/financeiro/logs/{} - payload: {}", id, request);
        try {
            LogFinanceiroResponse response = service.atualizar(id, request);
            log.info("Log financeiro atualizado com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar log — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar log — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir log", description = "Exclui um log financeiro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Log excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Log não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do log", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/financeiro/logs/{}", id);
        try {
            service.excluir(id);
            log.info("Log financeiro excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Log não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir log — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/inativar")
    @Operation(summary = "Inativar log", description = "Inativa um log financeiro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Log inativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Log não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> inativar(
            @Parameter(description = "ID do log", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST PUT /v1/financeiro/logs/{}/inativar", id);
        try {
            service.inativar(id);
            log.info("Log financeiro inativado com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Log não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inativar log — ID: {}", id, ex);
            throw ex;
        }
    }
}

