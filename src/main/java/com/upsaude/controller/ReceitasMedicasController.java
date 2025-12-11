package com.upsaude.controller;

import com.upsaude.api.request.ReceitasMedicasRequest;
import com.upsaude.api.response.ReceitasMedicasResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.ReceitasMedicasService;
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
@RequestMapping("/v1/receitas-medicas")
@Tag(name = "Receitas Médicas", description = "API para gerenciamento de Receitas Médicas")
@RequiredArgsConstructor
@Slf4j
public class ReceitasMedicasController {

    private final ReceitasMedicasService receitasMedicasService;

    @PostMapping
    @Operation(summary = "Criar nova receita médica", description = "Cria uma nova receita médica no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Receita médica criada com sucesso",
                    content = @Content(schema = @Schema(implementation = ReceitasMedicasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ReceitasMedicasResponse> criar(@Valid @RequestBody ReceitasMedicasRequest request) {
        log.debug("REQUEST POST /v1/receitas-medicas - payload: {}", request);
        try {
            ReceitasMedicasResponse response = receitasMedicasService.criar(request);
            log.info("Receita médica criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar receita médica — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar receita médica — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar receitas médicas", description = "Retorna uma lista paginada de receitas médicas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de receitas médicas retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ReceitasMedicasResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/receitas-medicas - pageable: {}", pageable);
        try {
            Page<ReceitasMedicasResponse> response = receitasMedicasService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar receitas médicas — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter receita médica por ID", description = "Retorna uma receita médica específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receita médica encontrada",
                    content = @Content(schema = @Schema(implementation = ReceitasMedicasResponse.class))),
            @ApiResponse(responseCode = "404", description = "Receita médica não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ReceitasMedicasResponse> obterPorId(
            @Parameter(description = "ID da receita médica", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/receitas-medicas/{}", id);
        try {
            ReceitasMedicasResponse response = receitasMedicasService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Receita médica não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter receita médica por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar receita médica", description = "Atualiza uma receita médica existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receita médica atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = ReceitasMedicasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Receita médica não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ReceitasMedicasResponse> atualizar(
            @Parameter(description = "ID da receita médica", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody ReceitasMedicasRequest request) {
        log.debug("REQUEST PUT /v1/receitas-medicas/{} - payload: {}", id, request);
        try {
            ReceitasMedicasResponse response = receitasMedicasService.atualizar(id, request);
            log.info("Receita médica atualizada com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar receita médica — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar receita médica — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir receita médica", description = "Exclui (desativa) uma receita médica do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Receita médica excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Receita médica não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da receita médica", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/receitas-medicas/{}", id);
        try {
            receitasMedicasService.excluir(id);
            log.info("Receita médica excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Receita médica não encontrada para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir receita médica — ID: {}", id, ex);
            throw ex;
        }
    }
}
