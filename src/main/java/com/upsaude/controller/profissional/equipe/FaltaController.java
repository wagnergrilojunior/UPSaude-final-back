package com.upsaude.controller.profissional.equipe;

import com.upsaude.api.request.profissional.equipe.FaltaRequest;
import com.upsaude.api.response.profissional.equipe.FaltaResponse;
import com.upsaude.enums.TipoFaltaEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.profissional.equipe.FaltaService;
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

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/v1/faltas")
@Tag(name = "Faltas", description = "API para gerenciamento de Faltas")
@RequiredArgsConstructor
public class FaltaController {

    private final FaltaService faltaService;

    @PostMapping
    @Operation(summary = "Criar nova falta", description = "Cria uma nova falta no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Falta criada com sucesso",
            content = @Content(schema = @Schema(implementation = FaltaResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<FaltaResponse> criar(@Valid @RequestBody FaltaRequest request) {
        log.debug("REQUEST POST /v1/faltas - payload: {}", request);
        try {
            FaltaResponse response = faltaService.criar(request);
            log.info("Falta criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar falta — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar falta — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar faltas", description = "Retorna uma lista paginada de faltas com filtros opcionais")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de faltas retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<FaltaResponse>> listar(
        @Parameter(description = "Parâmetros de paginação (page, size, sort)")
        Pageable pageable,
        @RequestParam(required = false) UUID profissionalId,
        @RequestParam(required = false) UUID medicoId,
        @RequestParam(required = false) UUID estabelecimentoId,
        @RequestParam(required = false) TipoFaltaEnum tipoFalta,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim
    ) {
        log.debug("REQUEST GET /v1/faltas - pageable: {}, profissionalId: {}, medicoId: {}, estabelecimentoId: {}, tipoFalta: {}, dataInicio: {}, dataFim: {}",
            pageable, profissionalId, medicoId, estabelecimentoId, tipoFalta, dataInicio, dataFim);
        try {
            Page<FaltaResponse> response = faltaService.listar(pageable, profissionalId, medicoId, estabelecimentoId, tipoFalta, dataInicio, dataFim);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar faltas", ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter falta por ID", description = "Retorna uma falta específica pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Falta encontrada",
            content = @Content(schema = @Schema(implementation = FaltaResponse.class))),
        @ApiResponse(responseCode = "404", description = "Falta não encontrada"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<FaltaResponse> obterPorId(
        @Parameter(description = "ID da falta", required = true)
        @PathVariable UUID id
    ) {
        log.debug("REQUEST GET /v1/faltas/{}", id);
        try {
            FaltaResponse response = faltaService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Falta não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter falta por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar falta", description = "Atualiza uma falta existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Falta atualizada com sucesso",
            content = @Content(schema = @Schema(implementation = FaltaResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "404", description = "Falta não encontrada"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<FaltaResponse> atualizar(
        @Parameter(description = "ID da falta", required = true)
        @PathVariable UUID id,
        @Valid @RequestBody FaltaRequest request
    ) {
        log.debug("REQUEST PUT /v1/faltas/{} - payload: {}", id, request);
        try {
            FaltaResponse response = faltaService.atualizar(id, request);
            log.info("Falta atualizada com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar falta — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar falta — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir falta", description = "Exclui (desativa) uma falta do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Falta excluída com sucesso"),
        @ApiResponse(responseCode = "404", description = "Falta não encontrada"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
        @Parameter(description = "ID da falta", required = true)
        @PathVariable UUID id
    ) {
        log.debug("REQUEST DELETE /v1/faltas/{}", id);
        try {
            faltaService.excluir(id);
            log.info("Falta excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Falha ao excluir falta — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir falta — ID: {}", id, ex);
            throw ex;
        }
    }
}

