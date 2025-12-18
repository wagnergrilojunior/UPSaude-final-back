package com.upsaude.controller.estabelecimento.equipamento;

import com.upsaude.api.request.estabelecimento.equipamento.FabricantesEquipamentoRequest;
import com.upsaude.api.response.estabelecimento.equipamento.FabricantesEquipamentoResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.estabelecimento.equipamento.FabricantesEquipamentoService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/v1/fabricantes-equipamento")
@Tag(name = "Fabricantes de Equipamento", description = "API para gerenciamento de Fabricantes de Equipamento")
@RequiredArgsConstructor
public class FabricantesEquipamentoController {

    private final FabricantesEquipamentoService fabricantesEquipamentoService;

    @PostMapping
    @Operation(summary = "Criar novo fabricante de equipamento", description = "Cria um novo fabricante de equipamento no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Fabricante de equipamento criado com sucesso",
            content = @Content(schema = @Schema(implementation = FabricantesEquipamentoResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<FabricantesEquipamentoResponse> criar(@Valid @RequestBody FabricantesEquipamentoRequest request) {
        log.debug("REQUEST POST /v1/fabricantes-equipamento - payload: {}", request);
        try {
            FabricantesEquipamentoResponse response = fabricantesEquipamentoService.criar(request);
            log.info("Fabricante de equipamento criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar fabricante de equipamento — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar fabricante de equipamento — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar fabricantes de equipamento", description = "Retorna uma lista paginada de fabricantes de equipamento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de fabricantes de equipamento retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<FabricantesEquipamentoResponse>> listar(
        @Parameter(description = "Parâmetros de paginação (page, size, sort)")
        Pageable pageable
    ) {
        log.debug("REQUEST GET /v1/fabricantes-equipamento - pageable: {}", pageable);
        try {
            Page<FabricantesEquipamentoResponse> response = fabricantesEquipamentoService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar fabricantes de equipamento — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter fabricante de equipamento por ID", description = "Retorna um fabricante de equipamento específico pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fabricante de equipamento encontrado",
            content = @Content(schema = @Schema(implementation = FabricantesEquipamentoResponse.class))),
        @ApiResponse(responseCode = "404", description = "Fabricante de equipamento não encontrado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<FabricantesEquipamentoResponse> obterPorId(
        @Parameter(description = "ID do fabricante de equipamento", required = true)
        @PathVariable UUID id
    ) {
        log.debug("REQUEST GET /v1/fabricantes-equipamento/{}", id);
        try {
            FabricantesEquipamentoResponse response = fabricantesEquipamentoService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Fabricante de equipamento não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter fabricante de equipamento por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar fabricante de equipamento", description = "Atualiza um fabricante de equipamento existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fabricante de equipamento atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = FabricantesEquipamentoResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "404", description = "Fabricante de equipamento não encontrado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<FabricantesEquipamentoResponse> atualizar(
        @Parameter(description = "ID do fabricante de equipamento", required = true)
        @PathVariable UUID id,
        @Valid @RequestBody FabricantesEquipamentoRequest request
    ) {
        log.debug("REQUEST PUT /v1/fabricantes-equipamento/{} - payload: {}", id, request);
        try {
            FabricantesEquipamentoResponse response = fabricantesEquipamentoService.atualizar(id, request);
            log.info("Fabricante de equipamento atualizado com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar fabricante de equipamento — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar fabricante de equipamento — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir fabricante de equipamento", description = "Exclui (desativa) um fabricante de equipamento do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Fabricante de equipamento excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Fabricante de equipamento não encontrado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
        @Parameter(description = "ID do fabricante de equipamento", required = true)
        @PathVariable UUID id
    ) {
        log.debug("REQUEST DELETE /v1/fabricantes-equipamento/{}", id);
        try {
            fabricantesEquipamentoService.excluir(id);
            log.info("Fabricante de equipamento excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Falha ao excluir fabricante de equipamento — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir fabricante de equipamento — ID: {}", id, ex);
            throw ex;
        }
    }
}

