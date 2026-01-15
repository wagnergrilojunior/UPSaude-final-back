package com.upsaude.controller.api.financeiro;

import com.upsaude.api.request.financeiro.TituloReceberRequest;
import com.upsaude.api.response.financeiro.TituloReceberResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.financeiro.TituloReceberService;
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
@RequestMapping("/api/v1/financeiro/titulos-receber")
@Tag(name = "Financeiro - Títulos a Receber", description = "API para gerenciamento de Títulos a Receber")
@RequiredArgsConstructor
@Slf4j
public class TituloReceberController {

    private final TituloReceberService service;

    @PostMapping
    @Operation(summary = "Criar título a receber", description = "Cria um novo título a receber")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Título criado com sucesso",
                    content = @Content(schema = @Schema(implementation = TituloReceberResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<TituloReceberResponse> criar(@Valid @RequestBody TituloReceberRequest request) {
        log.debug("REQUEST POST /api/v1/financeiro/titulos-receber - payload: {}", request);
        try {
            TituloReceberResponse response = service.criar(request);
            log.info("Título a receber criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar título a receber — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar título a receber — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar títulos a receber", description = "Retorna uma lista paginada de títulos a receber")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<TituloReceberResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /api/v1/financeiro/titulos-receber - pageable: {}", pageable);
        try {
            return ResponseEntity.ok(service.listar(pageable));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar títulos a receber — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter título por ID", description = "Retorna um título a receber específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Título encontrado",
                    content = @Content(schema = @Schema(implementation = TituloReceberResponse.class))),
            @ApiResponse(responseCode = "404", description = "Título não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<TituloReceberResponse> obterPorId(
            @Parameter(description = "ID do título", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /api/v1/financeiro/titulos-receber/{}", id);
        try {
            return ResponseEntity.ok(service.obterPorId(id));
        } catch (NotFoundException ex) {
            log.warn("Título não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter título a receber — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar título", description = "Atualiza um título a receber existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Título atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = TituloReceberResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Título não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<TituloReceberResponse> atualizar(
            @Parameter(description = "ID do título", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody TituloReceberRequest request) {
        log.debug("REQUEST PUT /api/v1/financeiro/titulos-receber/{} - payload: {}", id, request);
        try {
            TituloReceberResponse response = service.atualizar(id, request);
            log.info("Título a receber atualizado com sucesso. ID: {}", id);
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
    @Operation(summary = "Excluir título", description = "Exclui um título a receber")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Título excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Título não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do título", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /api/v1/financeiro/titulos-receber/{}", id);
        try {
            service.excluir(id);
            log.info("Título a receber excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Título não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir título — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/inativar")
    @Operation(summary = "Inativar título", description = "Inativa um título a receber")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Título inativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Título não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> inativar(
            @Parameter(description = "ID do título", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST PUT /api/v1/financeiro/titulos-receber/{}/inativar", id);
        try {
            service.inativar(id);
            log.info("Título a receber inativado com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Título não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inativar título — ID: {}", id, ex);
            throw ex;
        }
    }
}

