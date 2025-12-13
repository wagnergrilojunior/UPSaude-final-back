package com.upsaude.controller;

import com.upsaude.api.request.AtividadeProfissionalRequest;
import com.upsaude.api.response.AtividadeProfissionalResponse;
import com.upsaude.enums.TipoAtividadeProfissionalEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.AtividadeProfissionalService;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/v1/atividades-profissionais")
@Tag(name = "Atividades Profissionais", description = "API para gerenciamento de Atividades Profissionais")
@RequiredArgsConstructor
public class AtividadeProfissionalController {

    private final AtividadeProfissionalService atividadeProfissionalService;

    @PostMapping
    @Operation(summary = "Criar nova atividade profissional", description = "Cria uma nova atividade profissional no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Atividade profissional criada com sucesso",
            content = @Content(schema = @Schema(implementation = AtividadeProfissionalResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AtividadeProfissionalResponse> criar(@Valid @RequestBody AtividadeProfissionalRequest request) {
        log.debug("REQUEST POST /v1/atividades-profissionais - payload: {}", request);
        try {
            AtividadeProfissionalResponse response = atividadeProfissionalService.criar(request);
            log.info("Atividade profissional criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar atividade profissional — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar atividade profissional — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar atividades profissionais", description = "Retorna uma lista paginada de atividades profissionais com filtros opcionais")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de atividades profissionais retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<AtividadeProfissionalResponse>> listar(
        @Parameter(description = "Parâmetros de paginação (page, size, sort)")
        Pageable pageable,
        @RequestParam(required = false) UUID profissionalId,
        @RequestParam(required = false) UUID medicoId,
        @RequestParam(required = false) UUID estabelecimentoId,
        @RequestParam(required = false) TipoAtividadeProfissionalEnum tipoAtividade,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dataInicio,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dataFim
    ) {
        log.debug("REQUEST GET /v1/atividades-profissionais - pageable: {}, profissionalId: {}, medicoId: {}, estabelecimentoId: {}, tipoAtividade: {}, dataInicio: {}, dataFim: {}",
            pageable, profissionalId, medicoId, estabelecimentoId, tipoAtividade, dataInicio, dataFim);
        try {
            Page<AtividadeProfissionalResponse> response = atividadeProfissionalService.listar(
                pageable, profissionalId, medicoId, estabelecimentoId, tipoAtividade, dataInicio, dataFim
            );
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar atividades profissionais", ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter atividade profissional por ID", description = "Retorna uma atividade profissional específica pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Atividade profissional encontrada",
            content = @Content(schema = @Schema(implementation = AtividadeProfissionalResponse.class))),
        @ApiResponse(responseCode = "404", description = "Atividade profissional não encontrada"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AtividadeProfissionalResponse> obterPorId(
        @Parameter(description = "ID da atividade profissional", required = true)
        @PathVariable UUID id
    ) {
        log.debug("REQUEST GET /v1/atividades-profissionais/{}", id);
        try {
            AtividadeProfissionalResponse response = atividadeProfissionalService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Atividade profissional não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter atividade profissional por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar atividade profissional", description = "Atualiza uma atividade profissional existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Atividade profissional atualizada com sucesso",
            content = @Content(schema = @Schema(implementation = AtividadeProfissionalResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "404", description = "Atividade profissional não encontrada"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AtividadeProfissionalResponse> atualizar(
        @Parameter(description = "ID da atividade profissional", required = true)
        @PathVariable UUID id,
        @Valid @RequestBody AtividadeProfissionalRequest request
    ) {
        log.debug("REQUEST PUT /v1/atividades-profissionais/{} - payload: {}", id, request);
        try {
            AtividadeProfissionalResponse response = atividadeProfissionalService.atualizar(id, request);
            log.info("Atividade profissional atualizada com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar atividade profissional — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar atividade profissional — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir atividade profissional", description = "Exclui (desativa) uma atividade profissional do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Atividade profissional excluída com sucesso"),
        @ApiResponse(responseCode = "404", description = "Atividade profissional não encontrada"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
        @Parameter(description = "ID da atividade profissional", required = true)
        @PathVariable UUID id
    ) {
        log.debug("REQUEST DELETE /v1/atividades-profissionais/{}", id);
        try {
            atividadeProfissionalService.excluir(id);
            log.info("Atividade profissional excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Falha ao excluir atividade profissional — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir atividade profissional — ID: {}", id, ex);
            throw ex;
        }
    }
}

