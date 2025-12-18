package com.upsaude.controller.clinica.medicacao;

import com.upsaude.api.request.clinica.medicacao.DispensacoesMedicamentosRequest;
import com.upsaude.api.response.clinica.medicacao.DispensacoesMedicamentosResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.clinica.medicacao.DispensacoesMedicamentosService;
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
@RequestMapping("/v1/dispensacoes-medicamentos")
@Tag(name = "Dispensações de Medicamentos", description = "API para gerenciamento de Dispensações de Medicamentos")
@RequiredArgsConstructor
@Slf4j
public class DispensacoesMedicamentosController {

    private final DispensacoesMedicamentosService dispensacoesMedicamentosService;

    @PostMapping
    @Operation(summary = "Criar nova dispensação de medicamento", description = "Cria uma nova dispensação de medicamento no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dispensação de medicamento criada com sucesso",
                    content = @Content(schema = @Schema(implementation = DispensacoesMedicamentosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DispensacoesMedicamentosResponse> criar(@Valid @RequestBody DispensacoesMedicamentosRequest request) {
        log.debug("REQUEST POST /v1/dispensacoes-medicamentos - payload: {}", request);
        try {
            DispensacoesMedicamentosResponse response = dispensacoesMedicamentosService.criar(request);
            log.info("Dispensação de medicamento criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar dispensação de medicamento — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar dispensação de medicamento — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar dispensações de medicamentos", description = "Retorna uma lista paginada de dispensações de medicamentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de dispensações de medicamentos retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<DispensacoesMedicamentosResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/dispensacoes-medicamentos - pageable: {}", pageable);
        try {
            Page<DispensacoesMedicamentosResponse> response = dispensacoesMedicamentosService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar dispensações de medicamentos — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter dispensação de medicamento por ID", description = "Retorna uma dispensação de medicamento específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dispensação de medicamento encontrada",
                    content = @Content(schema = @Schema(implementation = DispensacoesMedicamentosResponse.class))),
            @ApiResponse(responseCode = "404", description = "Dispensação de medicamento não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DispensacoesMedicamentosResponse> obterPorId(
            @Parameter(description = "ID da dispensação de medicamento", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/dispensacoes-medicamentos/{}", id);
        try {
            DispensacoesMedicamentosResponse response = dispensacoesMedicamentosService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Dispensação de medicamento não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter dispensação de medicamento por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dispensação de medicamento", description = "Atualiza uma dispensação de medicamento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dispensação de medicamento atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = DispensacoesMedicamentosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Dispensação de medicamento não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DispensacoesMedicamentosResponse> atualizar(
            @Parameter(description = "ID da dispensação de medicamento", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody DispensacoesMedicamentosRequest request) {
        log.debug("REQUEST PUT /v1/dispensacoes-medicamentos/{} - payload: {}", id, request);
        try {
            DispensacoesMedicamentosResponse response = dispensacoesMedicamentosService.atualizar(id, request);
            log.info("Dispensação de medicamento atualizada com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar dispensação de medicamento — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar dispensação de medicamento — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir dispensação de medicamento", description = "Exclui (desativa) uma dispensação de medicamento do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Dispensação de medicamento excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Dispensação de medicamento não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da dispensação de medicamento", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/dispensacoes-medicamentos/{}", id);
        try {
            dispensacoesMedicamentosService.excluir(id);
            log.info("Dispensação de medicamento excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Dispensação de medicamento não encontrada para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir dispensação de medicamento — ID: {}", id, ex);
            throw ex;
        }
    }
}
