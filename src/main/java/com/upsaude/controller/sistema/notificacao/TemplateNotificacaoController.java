package com.upsaude.controller.sistema.notificacao;

import com.upsaude.api.request.sistema.notificacao.TemplateNotificacaoRequest;
import com.upsaude.api.response.sistema.notificacao.TemplateNotificacaoResponse;
import com.upsaude.enums.CanalNotificacaoEnum;
import com.upsaude.enums.TipoNotificacaoEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.sistema.notificacao.TemplateNotificacaoService;
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
@RequestMapping("/v1/templates-notificacao")
@Tag(name = "Templates de Notificação", description = "API para gerenciamento de Templates de Notificação")
@RequiredArgsConstructor
@Slf4j
public class TemplateNotificacaoController {

    private final TemplateNotificacaoService service;

    @PostMapping
    @Operation(summary = "Criar template de notificação", description = "Cria um novo template de notificação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Template criado com sucesso",
            content = @Content(schema = @Schema(implementation = TemplateNotificacaoResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<TemplateNotificacaoResponse> criar(@Valid @RequestBody TemplateNotificacaoRequest request) {
        log.debug("REQUEST POST /v1/templates-notificacao - payload: {}", request);
        try {
            TemplateNotificacaoResponse response = service.criar(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar template de notificação — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar templates de notificação", description = "Retorna uma lista paginada de templates de notificação")
    public ResponseEntity<Page<TemplateNotificacaoResponse>> listar(
        @Parameter(description = "Parâmetros de paginação (page, size, sort)") Pageable pageable,
        @RequestParam(required = false) UUID estabelecimentoId,
        @RequestParam(required = false) TipoNotificacaoEnum tipoNotificacao,
        @RequestParam(required = false) CanalNotificacaoEnum canal,
        @RequestParam(required = false) String nome) {
        log.debug("REQUEST GET /v1/templates-notificacao - pageable: {}, estabelecimentoId: {}, tipo: {}, canal: {}, nome: {}",
            pageable, estabelecimentoId, tipoNotificacao, canal, nome);
        Page<TemplateNotificacaoResponse> response = service.listar(pageable, estabelecimentoId, tipoNotificacao, canal, nome);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter template por ID", description = "Retorna um template de notificação pelo ID")
    public ResponseEntity<TemplateNotificacaoResponse> obterPorId(@PathVariable UUID id) {
        log.debug("REQUEST GET /v1/templates-notificacao/{}", id);
        try {
            TemplateNotificacaoResponse response = service.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Template de notificação não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar template", description = "Atualiza um template de notificação existente")
    public ResponseEntity<TemplateNotificacaoResponse> atualizar(@PathVariable UUID id, @Valid @RequestBody TemplateNotificacaoRequest request) {
        log.debug("REQUEST PUT /v1/templates-notificacao/{} - payload: {}", id, request);
        TemplateNotificacaoResponse response = service.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir template", description = "Exclui (desativa) um template de notificação")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/templates-notificacao/{}", id);
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

