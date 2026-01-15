package com.upsaude.controller.api.financeiro;

import com.upsaude.api.request.financeiro.CentroCustoRequest;
import com.upsaude.api.response.financeiro.CentroCustoResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.financeiro.CentroCustoService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/financeiro/centros-custo")
@Tag(name = "Financeiro - Centros de Custo", description = "API para gerenciamento de Centro de Custo")
@RequiredArgsConstructor
@Slf4j
public class CentroCustoController {

    private final CentroCustoService service;

    @PostMapping
    @Operation(summary = "Criar centro de custo", description = "Cria um novo centro de custo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Centro de custo criado com sucesso",
                    content = @Content(schema = @Schema(implementation = CentroCustoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CentroCustoResponse> criar(@Valid @RequestBody CentroCustoRequest request) {
        log.debug("REQUEST POST /api/v1/financeiro/centros-custo - payload: {}", request);
        try {
            CentroCustoResponse response = service.criar(request);
            log.info("Centro de custo criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao criar centro de custo — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar centro de custo — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar centros de custo", description = "Retorna uma lista paginada de centros de custo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<CentroCustoResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /api/v1/financeiro/centros-custo - pageable: {}", pageable);
        try {
            Page<CentroCustoResponse> response = service.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar centros de custo — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter centro de custo por ID", description = "Retorna um centro de custo específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Centro de custo encontrado",
                    content = @Content(schema = @Schema(implementation = CentroCustoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Centro de custo não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CentroCustoResponse> obterPorId(
            @Parameter(description = "ID do centro de custo", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /api/v1/financeiro/centros-custo/{}", id);
        try {
            CentroCustoResponse response = service.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Centro de custo não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter centro de custo — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar centro de custo", description = "Atualiza um centro de custo existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Centro de custo atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = CentroCustoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Centro de custo não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CentroCustoResponse> atualizar(
            @Parameter(description = "ID do centro de custo", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody CentroCustoRequest request) {
        log.debug("REQUEST PUT /api/v1/financeiro/centros-custo/{} - payload: {}", id, request);
        try {
            CentroCustoResponse response = service.atualizar(id, request);
            log.info("Centro de custo atualizado com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar centro de custo — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar centro de custo — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir centro de custo", description = "Exclui um centro de custo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Centro de custo excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Centro de custo não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do centro de custo", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /api/v1/financeiro/centros-custo/{}", id);
        try {
            service.excluir(id);
            log.info("Centro de custo excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Centro de custo não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir centro de custo — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/inativar")
    @Operation(summary = "Inativar centro de custo", description = "Inativa um centro de custo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Centro de custo inativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Centro de custo não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> inativar(
            @Parameter(description = "ID do centro de custo", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST PUT /api/v1/financeiro/centros-custo/{}/inativar", id);
        try {
            service.inativar(id);
            log.info("Centro de custo inativado com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Centro de custo não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inativar centro de custo — ID: {}", id, ex);
            throw ex;
        }
    }
}

