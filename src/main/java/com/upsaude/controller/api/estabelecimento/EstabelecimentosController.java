package com.upsaude.controller.api.estabelecimento;

import com.upsaude.entity.estabelecimento.Estabelecimentos;

import com.upsaude.api.request.estabelecimento.EstabelecimentosRequest;
import com.upsaude.api.response.estabelecimento.EstabelecimentosResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.estabelecimento.EstabelecimentosService;
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
@RequestMapping("/v1/estabelecimentos")
@Tag(name = "Estabelecimentos", description = "API para gerenciamento de Estabelecimentos")
@RequiredArgsConstructor
@Slf4j
public class EstabelecimentosController {

    private final EstabelecimentosService estabelecimentosService;

    @PostMapping
    @Operation(summary = "Criar novo estabelecimento", description = "Cria um novo estabelecimento no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estabelecimento criado com sucesso",
                    content = @Content(schema = @Schema(implementation = EstabelecimentosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EstabelecimentosResponse> criar(@Valid @RequestBody EstabelecimentosRequest request) {
        log.debug("REQUEST POST /v1/estabelecimentos - payload: {}", request);
        try {
            EstabelecimentosResponse response = estabelecimentosService.criar(request);
            log.info("Estabelecimento criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar estabelecimento — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar estabelecimento — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar estabelecimentos", description = "Retorna uma lista paginada de estabelecimentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de estabelecimentos retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<EstabelecimentosResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/estabelecimentos - pageable: {}", pageable);
        try {
            Page<EstabelecimentosResponse> response = estabelecimentosService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar estabelecimentos — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter estabelecimento por ID", description = "Retorna um estabelecimento específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estabelecimento encontrado",
                    content = @Content(schema = @Schema(implementation = EstabelecimentosResponse.class))),
            @ApiResponse(responseCode = "404", description = "Estabelecimento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EstabelecimentosResponse> obterPorId(
            @Parameter(description = "ID do estabelecimento", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/estabelecimentos/{}", id);
        try {
            EstabelecimentosResponse response = estabelecimentosService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Estabelecimento não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter estabelecimento por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar estabelecimento", description = "Atualiza um estabelecimento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estabelecimento atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = EstabelecimentosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Estabelecimento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EstabelecimentosResponse> atualizar(
            @Parameter(description = "ID do estabelecimento", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody EstabelecimentosRequest request) {
        log.debug("REQUEST PUT /v1/estabelecimentos/{} - payload: {}", id, request);
        try {
            EstabelecimentosResponse response = estabelecimentosService.atualizar(id, request);
            log.info("Estabelecimento atualizado com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar estabelecimento — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar estabelecimento — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir estabelecimento", description = "Exclui (desativa) um estabelecimento do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Estabelecimento excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estabelecimento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do estabelecimento", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/estabelecimentos/{}", id);
        try {
            estabelecimentosService.excluir(id);
            log.info("Estabelecimento excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Estabelecimento não encontrado para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir estabelecimento — ID: {}", id, ex);
            throw ex;
        }
    }
}
