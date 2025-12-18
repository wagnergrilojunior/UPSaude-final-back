package com.upsaude.controller.referencia.geografico;

import com.upsaude.api.request.referencia.geografico.EstadosRequest;
import com.upsaude.api.response.referencia.geografico.EstadosResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.referencia.geografico.EstadosService;
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
@RequestMapping("/v1/estados")
@Tag(name = "Estados", description = "API para gerenciamento de Estados")
@RequiredArgsConstructor
@Slf4j
public class EstadosController {

    private final EstadosService estadosService;

    @PostMapping
    @Operation(summary = "Criar novo estado", description = "Cria um novo estado no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estado criado com sucesso",
                    content = @Content(schema = @Schema(implementation = EstadosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EstadosResponse> criar(@Valid @RequestBody EstadosRequest request) {
        log.debug("REQUEST POST /v1/estados - payload: {}", request);
        try {
            EstadosResponse response = estadosService.criar(request);
            log.info("Estado criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar estado — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar estado — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar estados", description = "Retorna uma lista paginada de estados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de estados retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<EstadosResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/estados - pageable: {}", pageable);
        try {
            Page<EstadosResponse> response = estadosService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar estados — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter estado por ID", description = "Retorna um estado específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado encontrado",
                    content = @Content(schema = @Schema(implementation = EstadosResponse.class))),
            @ApiResponse(responseCode = "404", description = "Estado não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EstadosResponse> obterPorId(
            @Parameter(description = "ID do estado", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/estados/{}", id);
        try {
            EstadosResponse response = estadosService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Estado não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter estado por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar estado", description = "Atualiza um estado existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = EstadosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Estado não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EstadosResponse> atualizar(
            @Parameter(description = "ID do estado", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody EstadosRequest request) {
        log.debug("REQUEST PUT /v1/estados/{} - payload: {}", id, request);
        try {
            EstadosResponse response = estadosService.atualizar(id, request);
            log.info("Estado atualizado com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar estado — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar estado — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir estado", description = "Exclui (desativa) um estado do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Estado excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estado não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do estado", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/estados/{}", id);
        try {
            estadosService.excluir(id);
            log.info("Estado excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Estado não encontrado para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir estado — ID: {}", id, ex);
            throw ex;
        }
    }
}
