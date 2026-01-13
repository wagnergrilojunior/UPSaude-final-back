package com.upsaude.controller.api.financeiro;

import com.upsaude.api.request.financeiro.GuiaAtendimentoAmbulatorialRequest;
import com.upsaude.api.response.financeiro.GuiaAtendimentoAmbulatorialResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.financeiro.GuiaAtendimentoAmbulatorialService;
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
@RequestMapping("/v1/financeiro/guias-ambulatoriais")
@Tag(name = "Financeiro - Guias Ambulatoriais", description = "API para gerenciamento de Guia de Atendimento Ambulatorial (GAA)")
@RequiredArgsConstructor
@Slf4j
public class GuiaAtendimentoAmbulatorialController {

    private final GuiaAtendimentoAmbulatorialService service;

    @PostMapping
    @Operation(summary = "Criar guia ambulatorial", description = "Cria uma nova Guia de Atendimento Ambulatorial (GAA)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Guia criada com sucesso",
                    content = @Content(schema = @Schema(implementation = GuiaAtendimentoAmbulatorialResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<GuiaAtendimentoAmbulatorialResponse> criar(@Valid @RequestBody GuiaAtendimentoAmbulatorialRequest request) {
        log.debug("REQUEST POST /v1/financeiro/guias-ambulatoriais - payload: {}", request);
        try {
            GuiaAtendimentoAmbulatorialResponse response = service.criar(request);
            log.info("Guia ambulatorial criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar guia ambulatorial — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar guia ambulatorial — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar guias ambulatoriais", description = "Retorna uma lista paginada de guias ambulatoriais")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<GuiaAtendimentoAmbulatorialResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/financeiro/guias-ambulatoriais - pageable: {}", pageable);
        try {
            return ResponseEntity.ok(service.listar(pageable));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar guias ambulatoriais — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter guia por ID", description = "Retorna uma guia ambulatorial específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Guia encontrada",
                    content = @Content(schema = @Schema(implementation = GuiaAtendimentoAmbulatorialResponse.class))),
            @ApiResponse(responseCode = "404", description = "Guia não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<GuiaAtendimentoAmbulatorialResponse> obterPorId(
            @Parameter(description = "ID da guia", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/financeiro/guias-ambulatoriais/{}", id);
        try {
            return ResponseEntity.ok(service.obterPorId(id));
        } catch (NotFoundException ex) {
            log.warn("Guia não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter guia ambulatorial — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar guia", description = "Atualiza uma guia ambulatorial existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Guia atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = GuiaAtendimentoAmbulatorialResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Guia não encontrada"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<GuiaAtendimentoAmbulatorialResponse> atualizar(
            @Parameter(description = "ID da guia", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody GuiaAtendimentoAmbulatorialRequest request) {
        log.debug("REQUEST PUT /v1/financeiro/guias-ambulatoriais/{} - payload: {}", id, request);
        try {
            GuiaAtendimentoAmbulatorialResponse response = service.atualizar(id, request);
            log.info("Guia ambulatorial atualizada com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar guia ambulatorial — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar guia ambulatorial — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir guia", description = "Exclui uma guia ambulatorial")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Guia excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Guia não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da guia", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/financeiro/guias-ambulatoriais/{}", id);
        try {
            service.excluir(id);
            log.info("Guia ambulatorial excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Guia não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir guia ambulatorial — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/inativar")
    @Operation(summary = "Inativar guia", description = "Inativa uma guia ambulatorial")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Guia inativada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Guia não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> inativar(
            @Parameter(description = "ID da guia", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST PUT /v1/financeiro/guias-ambulatoriais/{}/inativar", id);
        try {
            service.inativar(id);
            log.info("Guia ambulatorial inativada com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Guia não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inativar guia ambulatorial — ID: {}", id, ex);
            throw ex;
        }
    }
}

