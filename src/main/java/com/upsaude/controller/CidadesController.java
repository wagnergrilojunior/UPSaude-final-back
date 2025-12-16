package com.upsaude.controller;

import com.upsaude.api.request.CidadesRequest;
import com.upsaude.api.response.CidadesResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.CidadesService;
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
@RequestMapping("/v1/cidades")
@Tag(name = "Cidades", description = "API para gerenciamento de Cidades")
@RequiredArgsConstructor
@Slf4j
public class CidadesController {

    private final CidadesService cidadesService;

    @PostMapping
    @Operation(summary = "Criar nova cidade", description = "Cria uma nova cidade no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cidade criada com sucesso",
                    content = @Content(schema = @Schema(implementation = CidadesResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CidadesResponse> criar(@Valid @RequestBody CidadesRequest request) {
        log.debug("REQUEST POST /v1/cidades - payload: {}", request);
        try {
            CidadesResponse response = cidadesService.criar(request);
            log.info("Cidade criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar cidade — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar cidade — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar cidades", description = "Retorna uma lista paginada de cidades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cidades retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<CidadesResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/cidades - pageable: {}", pageable);
        try {
            Page<CidadesResponse> response = cidadesService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar cidades — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/por-estado/{estadoId}")
    @Operation(summary = "Listar cidades por estado", description = "Retorna uma lista paginada de cidades de um estado específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cidades retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID do estado inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<CidadesResponse>> listarPorEstado(
            @Parameter(description = "ID do estado", required = true)
            @PathVariable UUID estadoId,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/cidades/por-estado/{} - pageable: {}", estadoId, pageable);
        try {
            Page<CidadesResponse> response = cidadesService.listarPorEstado(estadoId, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar cidades por estado — estadoId: {}, mensagem: {}", estadoId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar cidades por estado — estadoId: {}, pageable: {}", estadoId, pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter cidade por ID", description = "Retorna uma cidade específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cidade encontrada",
                    content = @Content(schema = @Schema(implementation = CidadesResponse.class))),
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CidadesResponse> obterPorId(
            @Parameter(description = "ID da cidade", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/cidades/{}", id);
        try {
            CidadesResponse response = cidadesService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Cidade não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter cidade por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cidade", description = "Atualiza uma cidade existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cidade atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = CidadesResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CidadesResponse> atualizar(
            @Parameter(description = "ID da cidade", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody CidadesRequest request) {
        log.debug("REQUEST PUT /v1/cidades/{} - payload: {}", id, request);
        try {
            CidadesResponse response = cidadesService.atualizar(id, request);
            log.info("Cidade atualizada com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar cidade — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar cidade — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir cidade", description = "Exclui (desativa) uma cidade do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cidade excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da cidade", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/cidades/{}", id);
        try {
            cidadesService.excluir(id);
            log.info("Cidade excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Cidade não encontrada para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir cidade — ID: {}", id, ex);
            throw ex;
        }
    }
}
