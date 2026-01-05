package com.upsaude.controller.api.estabelecimento;

import com.upsaude.api.request.estabelecimento.EquipamentosEstabelecimentoRequest;
import com.upsaude.api.response.estabelecimento.EquipamentosEstabelecimentoResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.estabelecimento.EquipamentosEstabelecimentoService;
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
@RequestMapping("/v1/equipamentos-estabelecimento")
@Tag(name = "Equipamentos do Estabelecimento", description = "API para gerenciamento de equipamentos por estabelecimento")
@RequiredArgsConstructor
@Slf4j
public class EquipamentosEstabelecimentoController {

    private final EquipamentosEstabelecimentoService service;

    @PostMapping
    @Operation(summary = "Criar vínculo de equipamento", description = "Cria um vínculo de equipamento com estabelecimento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Vínculo criado com sucesso",
            content = @Content(schema = @Schema(implementation = EquipamentosEstabelecimentoResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EquipamentosEstabelecimentoResponse> criar(@Valid @RequestBody EquipamentosEstabelecimentoRequest request) {
        log.debug("REQUEST POST /v1/equipamentos-estabelecimento - payload: {}", request);
        try {
            EquipamentosEstabelecimentoResponse response = service.criar(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar vínculo de equipamento — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar vínculos de equipamentos", description = "Retorna uma lista paginada de equipamentos vinculados")
    public ResponseEntity<Page<EquipamentosEstabelecimentoResponse>> listar(
        @Parameter(description = "Parâmetros de paginação (page, size, sort)") Pageable pageable) {
        log.debug("REQUEST GET /v1/equipamentos-estabelecimento - pageable: {}", pageable);
        Page<EquipamentosEstabelecimentoResponse> response = service.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter vínculo por ID", description = "Retorna um vínculo específico pelo seu ID")
    public ResponseEntity<EquipamentosEstabelecimentoResponse> obterPorId(@PathVariable UUID id) {
        log.debug("REQUEST GET /v1/equipamentos-estabelecimento/{}", id);
        try {
            EquipamentosEstabelecimentoResponse response = service.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Vínculo não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar vínculo", description = "Atualiza um vínculo de equipamento existente")
    public ResponseEntity<EquipamentosEstabelecimentoResponse> atualizar(@PathVariable UUID id, @Valid @RequestBody EquipamentosEstabelecimentoRequest request) {
        log.debug("REQUEST PUT /v1/equipamentos-estabelecimento/{} - payload: {}", id, request);
        EquipamentosEstabelecimentoResponse response = service.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir vínculo", description = "Exclui permanentemente um vínculo de equipamento do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Vínculo excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Vínculo não encontrado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do vínculo", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/equipamentos-estabelecimento/{}", id);
        try {
            service.excluir(id);
            log.info("Vínculo excluído permanentemente com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Vínculo não encontrado para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir vínculo — ID: {}", id, ex);
            throw ex;
        }
    }

    @PatchMapping("/{id}/inativar")
    @Operation(summary = "Inativar vínculo", description = "Inativa um vínculo de equipamento do sistema alterando seu status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Vínculo inativado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Vínculo não encontrado"),
        @ApiResponse(responseCode = "400", description = "Vínculo já está inativo"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> inativar(
            @Parameter(description = "ID do vínculo", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST PATCH /v1/equipamentos-estabelecimento/{}/inativar", id);
        try {
            service.inativar(id);
            log.info("Vínculo inativado com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Vínculo não encontrado para inativação — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inativar vínculo — ID: {}", id, ex);
            throw ex;
        }
    }
}
