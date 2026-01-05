package com.upsaude.controller.api.estabelecimento.equipamento;

import com.upsaude.api.request.estabelecimento.equipamento.EquipamentosRequest;
import com.upsaude.api.response.estabelecimento.equipamento.EquipamentosResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.estabelecimento.equipamento.EquipamentosService;
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
@RequestMapping("/v1/equipamentos")
@Tag(name = "Equipamentos", description = "API para gerenciamento de equipamentos")
@RequiredArgsConstructor
@Slf4j
public class EquipamentosController {

    private final EquipamentosService service;

    @PostMapping
    @Operation(summary = "Criar novo equipamento", description = "Cria um novo equipamento no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Equipamento criado com sucesso",
            content = @Content(schema = @Schema(implementation = EquipamentosResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EquipamentosResponse> criar(@Valid @RequestBody EquipamentosRequest request) {
        log.debug("REQUEST POST /v1/equipamentos - payload: {}", request);
        try {
            EquipamentosResponse response = service.criar(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar equipamento — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar equipamentos", description = "Retorna uma lista paginada de equipamentos")
    public ResponseEntity<Page<EquipamentosResponse>> listar(
        @Parameter(description = "Parâmetros de paginação (page, size, sort)") Pageable pageable) {
        log.debug("REQUEST GET /v1/equipamentos - pageable: {}", pageable);
        Page<EquipamentosResponse> response = service.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter equipamento por ID", description = "Retorna um equipamento específico pelo seu ID")
    public ResponseEntity<EquipamentosResponse> obterPorId(@PathVariable UUID id) {
        log.debug("REQUEST GET /v1/equipamentos/{}", id);
        try {
            EquipamentosResponse response = service.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Equipamento não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar equipamento", description = "Atualiza um equipamento existente")
    public ResponseEntity<EquipamentosResponse> atualizar(@PathVariable UUID id, @Valid @RequestBody EquipamentosRequest request) {
        log.debug("REQUEST PUT /v1/equipamentos/{} - payload: {}", id, request);
        EquipamentosResponse response = service.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir equipamento", description = "Remove permanentemente um equipamento do banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Equipamento excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Equipamento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/equipamentos/{}", id);
        try {
            service.excluir(id);
            log.info("Equipamento excluído permanentemente com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Equipamento não encontrado para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir equipamento — ID: {}", id, ex);
            throw ex;
        }
    }

    @PatchMapping("/{id}/inativar")
    @Operation(summary = "Inativar equipamento", description = "Inativa um equipamento no sistema alterando seu status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Equipamento inativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Equipamento não encontrado"),
            @ApiResponse(responseCode = "400", description = "Equipamento já está inativo"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> inativar(@PathVariable UUID id) {
        log.debug("REQUEST PATCH /v1/equipamentos/{}/inativar", id);
        try {
            service.inativar(id);
            log.info("Equipamento inativado com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Equipamento não encontrado para inativação — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inativar equipamento — ID: {}", id, ex);
            throw ex;
        }
    }
}
