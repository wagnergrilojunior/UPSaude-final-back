package com.upsaude.controller;

import com.upsaude.api.request.CidDoencasRequest;
import com.upsaude.api.response.CidDoencasResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.CidDoencasService;
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
@RequestMapping("/v1/cid-doencas")
@Tag(name = "CID Doenças", description = "API para gerenciamento de CID Doenças")
@RequiredArgsConstructor
@Slf4j
public class CidDoencasController {

    private final CidDoencasService cidDoencasService;

    @PostMapping
    @Operation(summary = "Criar nova CID doença", description = "Cria uma nova CID doença no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CID doença criada com sucesso",
                    content = @Content(schema = @Schema(implementation = CidDoencasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CidDoencasResponse> criar(@Valid @RequestBody CidDoencasRequest request) {
        log.debug("REQUEST POST /v1/cid-doencas - payload: {}", request);
        try {
            CidDoencasResponse response = cidDoencasService.criar(request);
            log.info("CID doença criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar CID doença — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar CID doença — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar CID doenças", description = "Retorna uma lista paginada de CID doenças")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de CID doenças retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<CidDoencasResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/cid-doencas - pageable: {}", pageable);
        try {
            Page<CidDoencasResponse> response = cidDoencasService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar CID doenças — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter CID doença por ID", description = "Retorna uma CID doença específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CID doença encontrada",
                    content = @Content(schema = @Schema(implementation = CidDoencasResponse.class))),
            @ApiResponse(responseCode = "404", description = "CID doença não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CidDoencasResponse> obterPorId(
            @Parameter(description = "ID da CID doença", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/cid-doencas/{}", id);
        try {
            CidDoencasResponse response = cidDoencasService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("CID doença não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter CID doença por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar CID doença", description = "Atualiza uma CID doença existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CID doença atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = CidDoencasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "CID doença não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CidDoencasResponse> atualizar(
            @Parameter(description = "ID da CID doença", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody CidDoencasRequest request) {
        log.debug("REQUEST PUT /v1/cid-doencas/{} - payload: {}", id, request);
        try {
            CidDoencasResponse response = cidDoencasService.atualizar(id, request);
            log.info("CID doença atualizada com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar CID doença — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar CID doença — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir CID doença", description = "Exclui (desativa) uma CID doença do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "CID doença excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "CID doença não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da CID doença", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/cid-doencas/{}", id);
        try {
            cidDoencasService.excluir(id);
            log.info("CID doença excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("CID doença não encontrada para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir CID doença — ID: {}", id, ex);
            throw ex;
        }
    }
}
