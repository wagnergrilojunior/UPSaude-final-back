package com.upsaude.controller.api.financeiro;

import com.upsaude.api.request.financeiro.TituloPagarRequest;
import com.upsaude.api.response.financeiro.TituloPagarResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.financeiro.TituloPagarService;
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
@RequestMapping("/api/v1/financeiro/titulos-pagar")
@Tag(name = "Financeiro - Títulos a Pagar", description = "API para gerenciamento de Títulos a Pagar")
@RequiredArgsConstructor
@Slf4j
public class TituloPagarController {

    private final TituloPagarService service;

    @PostMapping
    @Operation(summary = "Criar título a pagar", description = "Cria um novo título a pagar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Título criado com sucesso",
                    content = @Content(schema = @Schema(implementation = TituloPagarResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<TituloPagarResponse> criar(@Valid @RequestBody TituloPagarRequest request) {
        log.debug("REQUEST POST /api/v1/financeiro/titulos-pagar - payload: {}", request);
        try {
            TituloPagarResponse response = service.criar(request);
            log.info("Título a pagar criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar título a pagar — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar título a pagar — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar títulos a pagar", description = "Retorna uma lista paginada de títulos a pagar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<TituloPagarResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /api/v1/financeiro/titulos-pagar - pageable: {}", pageable);
        try {
            return ResponseEntity.ok(service.listar(pageable));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar títulos a pagar — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter título por ID", description = "Retorna um título a pagar específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Título encontrado",
                    content = @Content(schema = @Schema(implementation = TituloPagarResponse.class))),
            @ApiResponse(responseCode = "404", description = "Título não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<TituloPagarResponse> obterPorId(
            @Parameter(description = "ID do título", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /api/v1/financeiro/titulos-pagar/{}", id);
        try {
            return ResponseEntity.ok(service.obterPorId(id));
        } catch (NotFoundException ex) {
            log.warn("Título não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter título a pagar — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar título", description = "Atualiza um título a pagar existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Título atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = TituloPagarResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Título não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<TituloPagarResponse> atualizar(
            @Parameter(description = "ID do título", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody TituloPagarRequest request) {
        log.debug("REQUEST PUT /api/v1/financeiro/titulos-pagar/{} - payload: {}", id, request);
        try {
            TituloPagarResponse response = service.atualizar(id, request);
            log.info("Título a pagar atualizado com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar título — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar título — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir título", description = "Exclui um título a pagar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Título excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Título não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do título", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /api/v1/financeiro/titulos-pagar/{}", id);
        try {
            service.excluir(id);
            log.info("Título a pagar excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Título não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir título a pagar — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/inativar")
    @Operation(summary = "Inativar título", description = "Inativa um título a pagar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Título inativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Título não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> inativar(
            @Parameter(description = "ID do título", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST PUT /api/v1/financeiro/titulos-pagar/{}/inativar", id);
        try {
            service.inativar(id);
            log.info("Título a pagar inativado com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Título não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inativar título a pagar — ID: {}", id, ex);
            throw ex;
        }
    }
}

