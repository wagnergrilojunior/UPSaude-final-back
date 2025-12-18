package com.upsaude.controller.vacina;

import com.upsaude.api.request.vacina.VacinasRequest;
import com.upsaude.api.response.vacina.VacinasResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.vacina.VacinasService;
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
@RequestMapping("/v1/vacinas")
@Tag(name = "Vacinas", description = "API para gerenciamento de Vacinas")
@RequiredArgsConstructor
@Slf4j
public class VacinasController {

    private final VacinasService vacinasService;

    @PostMapping
    @Operation(summary = "Criar nova vacina", description = "Cria uma nova vacina no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vacina criada com sucesso",
                    content = @Content(schema = @Schema(implementation = VacinasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<VacinasResponse> criar(@Valid @RequestBody VacinasRequest request) {
        log.debug("REQUEST POST /v1/vacinas - payload: {}", request);
        try {
            VacinasResponse response = vacinasService.criar(request);
            log.info("Vacina criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar vacina — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar vacina — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar vacinas", description = "Retorna uma lista paginada de vacinas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de vacinas retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<VacinasResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/vacinas - pageable: {}", pageable);
        try {
            Page<VacinasResponse> response = vacinasService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar vacinas — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter vacina por ID", description = "Retorna uma vacina específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vacina encontrada",
                    content = @Content(schema = @Schema(implementation = VacinasResponse.class))),
            @ApiResponse(responseCode = "404", description = "Vacina não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<VacinasResponse> obterPorId(
            @Parameter(description = "ID da vacina", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/vacinas/{}", id);
        try {
            VacinasResponse response = vacinasService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Vacina não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter vacina por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar vacina", description = "Atualiza uma vacina existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vacina atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = VacinasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Vacina não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<VacinasResponse> atualizar(
            @Parameter(description = "ID da vacina", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody VacinasRequest request) {
        log.debug("REQUEST PUT /v1/vacinas/{} - payload: {}", id, request);
        try {
            VacinasResponse response = vacinasService.atualizar(id, request);
            log.info("Vacina atualizada com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar vacina — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar vacina — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir vacina", description = "Exclui (desativa) uma vacina do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vacina excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Vacina não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da vacina", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/vacinas/{}", id);
        try {
            vacinasService.excluir(id);
            log.info("Vacina excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Vacina não encontrada para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir vacina — ID: {}", id, ex);
            throw ex;
        }
    }
}
