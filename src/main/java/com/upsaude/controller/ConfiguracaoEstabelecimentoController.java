package com.upsaude.controller;

import com.upsaude.api.request.ConfiguracaoEstabelecimentoRequest;
import com.upsaude.api.response.ConfiguracaoEstabelecimentoResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.ConfiguracaoEstabelecimentoService;
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
@RequestMapping("/v1/configuracoes-estabelecimento")
@Tag(name = "Configurações do Estabelecimento", description = "API para gerenciamento de Configuração de Estabelecimento")
@RequiredArgsConstructor
@Slf4j
public class ConfiguracaoEstabelecimentoController {

    private final ConfiguracaoEstabelecimentoService service;

    @PostMapping
    @Operation(summary = "Criar configuração do estabelecimento", description = "Cria uma nova configuração do estabelecimento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Configuração criada com sucesso",
            content = @Content(schema = @Schema(implementation = ConfiguracaoEstabelecimentoResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "409", description = "Conflito: configuração já existente")
    })
    public ResponseEntity<ConfiguracaoEstabelecimentoResponse> criar(@Valid @RequestBody ConfiguracaoEstabelecimentoRequest request) {
        log.debug("REQUEST POST /v1/configuracoes-estabelecimento - payload: {}", request);
        try {
            ConfiguracaoEstabelecimentoResponse response = service.criar(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar configuração — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar configurações do estabelecimento", description = "Retorna uma lista paginada de configurações; pode filtrar por estabelecimentoId")
    public ResponseEntity<Page<ConfiguracaoEstabelecimentoResponse>> listar(
        @Parameter(description = "Parâmetros de paginação (page, size, sort)") Pageable pageable,
        @RequestParam(required = false) UUID estabelecimentoId) {
        log.debug("REQUEST GET /v1/configuracoes-estabelecimento - pageable: {}, estabelecimentoId: {}", pageable, estabelecimentoId);
        return ResponseEntity.ok(service.listar(pageable, estabelecimentoId));
    }

    @GetMapping("/estabelecimento/{id}")
    @Operation(summary = "Obter configuração por estabelecimento", description = "Retorna a configuração única do estabelecimento")
    public ResponseEntity<ConfiguracaoEstabelecimentoResponse> obterPorEstabelecimento(@PathVariable("id") UUID estabelecimentoId) {
        log.debug("REQUEST GET /v1/configuracoes-estabelecimento/estabelecimento/{}", estabelecimentoId);
        try {
            return ResponseEntity.ok(service.obterPorEstabelecimento(estabelecimentoId));
        } catch (NotFoundException ex) {
            log.warn("Configuração não encontrada — estabelecimentoId: {}, mensagem: {}", estabelecimentoId, ex.getMessage());
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter configuração por ID", description = "Retorna uma configuração pelo ID")
    public ResponseEntity<ConfiguracaoEstabelecimentoResponse> obterPorId(@PathVariable UUID id) {
        log.debug("REQUEST GET /v1/configuracoes-estabelecimento/{}", id);
        return ResponseEntity.ok(service.obterPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar configuração", description = "Atualiza uma configuração do estabelecimento existente")
    public ResponseEntity<ConfiguracaoEstabelecimentoResponse> atualizar(@PathVariable UUID id, @Valid @RequestBody ConfiguracaoEstabelecimentoRequest request) {
        log.debug("REQUEST PUT /v1/configuracoes-estabelecimento/{} - payload: {}", id, request);
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir configuração", description = "Exclui (desativa) uma configuração do estabelecimento")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/configuracoes-estabelecimento/{}", id);
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

