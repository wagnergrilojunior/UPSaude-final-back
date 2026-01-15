package com.upsaude.controller.api.financeiro;

import com.upsaude.api.request.financeiro.ExtratoBancarioImportadoRequest;
import com.upsaude.api.response.financeiro.ExtratoBancarioImportadoResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.financeiro.ExtratoBancarioImportadoService;
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
@RequestMapping("/api/v1/financeiro/extratos-importados")
@Tag(name = "Financeiro - Extratos Importados", description = "API para gerenciamento de Extratos Bancários Importados")
@RequiredArgsConstructor
@Slf4j
public class ExtratoBancarioImportadoController {

    private final ExtratoBancarioImportadoService service;

    @PostMapping
    @Operation(summary = "Criar extrato importado", description = "Cria um novo extrato bancário importado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Extrato criado com sucesso",
                    content = @Content(schema = @Schema(implementation = ExtratoBancarioImportadoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ExtratoBancarioImportadoResponse> criar(@Valid @RequestBody ExtratoBancarioImportadoRequest request) {
        log.debug("REQUEST POST /api/v1/financeiro/extratos-importados - payload: {}", request);
        try {
            ExtratoBancarioImportadoResponse response = service.criar(request);
            log.info("Extrato importado criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar extrato importado — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar extrato importado — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar extratos importados", description = "Retorna uma lista paginada de extratos importados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ExtratoBancarioImportadoResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /api/v1/financeiro/extratos-importados - pageable: {}", pageable);
        try {
            return ResponseEntity.ok(service.listar(pageable));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar extratos importados — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter extrato por ID", description = "Retorna um extrato importado específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Extrato encontrado",
                    content = @Content(schema = @Schema(implementation = ExtratoBancarioImportadoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Extrato não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ExtratoBancarioImportadoResponse> obterPorId(
            @Parameter(description = "ID do extrato", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /api/v1/financeiro/extratos-importados/{}", id);
        try {
            return ResponseEntity.ok(service.obterPorId(id));
        } catch (NotFoundException ex) {
            log.warn("Extrato não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter extrato importado — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar extrato", description = "Atualiza um extrato importado existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Extrato atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = ExtratoBancarioImportadoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Extrato não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ExtratoBancarioImportadoResponse> atualizar(
            @Parameter(description = "ID do extrato", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody ExtratoBancarioImportadoRequest request) {
        log.debug("REQUEST PUT /api/v1/financeiro/extratos-importados/{} - payload: {}", id, request);
        try {
            ExtratoBancarioImportadoResponse response = service.atualizar(id, request);
            log.info("Extrato importado atualizado com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar extrato — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar extrato — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir extrato", description = "Exclui um extrato importado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Extrato excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Extrato não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do extrato", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /api/v1/financeiro/extratos-importados/{}", id);
        try {
            service.excluir(id);
            log.info("Extrato importado excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Extrato não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir extrato — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/inativar")
    @Operation(summary = "Inativar extrato", description = "Inativa um extrato importado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Extrato inativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Extrato não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> inativar(
            @Parameter(description = "ID do extrato", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST PUT /api/v1/financeiro/extratos-importados/{}/inativar", id);
        try {
            service.inativar(id);
            log.info("Extrato importado inativado com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Extrato não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inativar extrato — ID: {}", id, ex);
            throw ex;
        }
    }
}

