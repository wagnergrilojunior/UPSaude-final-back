package com.upsaude.controller.api.faturamento;

import com.upsaude.api.request.faturamento.DocumentoFaturamentoRequest;
import com.upsaude.api.response.faturamento.DocumentoFaturamentoResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.faturamento.DocumentoFaturamentoService;
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
@RequestMapping("/v1/faturamento/documentos")
@Tag(name = "Faturamento - Documentos", description = "API para gerenciamento de Documentos de Faturamento")
@RequiredArgsConstructor
@Slf4j
public class DocumentoFaturamentoController {

    private final DocumentoFaturamentoService service;

    @PostMapping
    @Operation(summary = "Criar documento", description = "Cria um novo documento de faturamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Documento criado com sucesso",
                    content = @Content(schema = @Schema(implementation = DocumentoFaturamentoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DocumentoFaturamentoResponse> criar(@Valid @RequestBody DocumentoFaturamentoRequest request) {
        log.debug("REQUEST POST /v1/faturamento/documentos - payload: {}", request);
        try {
            DocumentoFaturamentoResponse response = service.criar(request);
            log.info("Documento de faturamento criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar documento de faturamento — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar documento de faturamento — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar documentos", description = "Retorna uma lista paginada de documentos de faturamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<DocumentoFaturamentoResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/faturamento/documentos - pageable: {}", pageable);
        try {
            return ResponseEntity.ok(service.listar(pageable));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar documentos de faturamento — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter documento por ID", description = "Retorna um documento de faturamento específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documento encontrado",
                    content = @Content(schema = @Schema(implementation = DocumentoFaturamentoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Documento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DocumentoFaturamentoResponse> obterPorId(
            @Parameter(description = "ID do documento", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/faturamento/documentos/{}", id);
        try {
            return ResponseEntity.ok(service.obterPorId(id));
        } catch (NotFoundException ex) {
            log.warn("Documento não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter documento — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar documento", description = "Atualiza um documento de faturamento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documento atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = DocumentoFaturamentoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Documento não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DocumentoFaturamentoResponse> atualizar(
            @Parameter(description = "ID do documento", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody DocumentoFaturamentoRequest request) {
        log.debug("REQUEST PUT /v1/faturamento/documentos/{} - payload: {}", id, request);
        try {
            DocumentoFaturamentoResponse response = service.atualizar(id, request);
            log.info("Documento de faturamento atualizado com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar documento — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar documento — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir documento", description = "Exclui um documento de faturamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Documento excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Documento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do documento", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/faturamento/documentos/{}", id);
        try {
            service.excluir(id);
            log.info("Documento de faturamento excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Documento não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir documento — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/inativar")
    @Operation(summary = "Inativar documento", description = "Inativa um documento de faturamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Documento inativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Documento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> inativar(
            @Parameter(description = "ID do documento", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST PUT /v1/faturamento/documentos/{}/inativar", id);
        try {
            service.inativar(id);
            log.info("Documento de faturamento inativado com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Documento não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inativar documento — ID: {}", id, ex);
            throw ex;
        }
    }
}

