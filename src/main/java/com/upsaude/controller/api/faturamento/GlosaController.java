package com.upsaude.controller.api.faturamento;

import com.upsaude.api.request.faturamento.GlosaRequest;
import com.upsaude.api.response.faturamento.GlosaResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.faturamento.GlosaService;
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
@RequestMapping("/v1/faturamento/glosas")
@Tag(name = "Faturamento - Glosas", description = "API para gerenciamento de Glosas")
@RequiredArgsConstructor
@Slf4j
public class GlosaController {

    private final GlosaService service;

    @PostMapping
    @Operation(summary = "Criar glosa", description = "Cria uma nova glosa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Glosa criada com sucesso",
                    content = @Content(schema = @Schema(implementation = GlosaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<GlosaResponse> criar(@Valid @RequestBody GlosaRequest request) {
        log.debug("REQUEST POST /v1/faturamento/glosas - payload: {}", request);
        try {
            GlosaResponse response = service.criar(request);
            log.info("Glosa criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar glosa — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar glosa — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar glosas", description = "Retorna uma lista paginada de glosas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<GlosaResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/faturamento/glosas - pageable: {}", pageable);
        try {
            return ResponseEntity.ok(service.listar(pageable));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar glosas — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter glosa por ID", description = "Retorna uma glosa específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Glosa encontrada",
                    content = @Content(schema = @Schema(implementation = GlosaResponse.class))),
            @ApiResponse(responseCode = "404", description = "Glosa não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<GlosaResponse> obterPorId(
            @Parameter(description = "ID da glosa", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/faturamento/glosas/{}", id);
        try {
            return ResponseEntity.ok(service.obterPorId(id));
        } catch (NotFoundException ex) {
            log.warn("Glosa não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter glosa — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar glosa", description = "Atualiza uma glosa existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Glosa atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = GlosaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Glosa não encontrada"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<GlosaResponse> atualizar(
            @Parameter(description = "ID da glosa", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody GlosaRequest request) {
        log.debug("REQUEST PUT /v1/faturamento/glosas/{} - payload: {}", id, request);
        try {
            GlosaResponse response = service.atualizar(id, request);
            log.info("Glosa atualizada com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar glosa — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar glosa — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir glosa", description = "Exclui uma glosa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Glosa excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Glosa não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da glosa", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/faturamento/glosas/{}", id);
        try {
            service.excluir(id);
            log.info("Glosa excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Glosa não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir glosa — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/inativar")
    @Operation(summary = "Inativar glosa", description = "Inativa uma glosa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Glosa inativada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Glosa não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> inativar(
            @Parameter(description = "ID da glosa", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST PUT /v1/faturamento/glosas/{}/inativar", id);
        try {
            service.inativar(id);
            log.info("Glosa inativada com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Glosa não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inativar glosa — ID: {}", id, ex);
            throw ex;
        }
    }
}

