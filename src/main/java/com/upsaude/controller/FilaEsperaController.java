package com.upsaude.controller;

import com.upsaude.api.request.FilaEsperaRequest;
import com.upsaude.api.response.FilaEsperaResponse;
import com.upsaude.enums.PrioridadeAtendimentoEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.FilaEsperaService;
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
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/v1/fila-espera")
@Tag(name = "Fila de Espera", description = "API para gerenciamento da Fila de Espera")
@RequiredArgsConstructor
public class FilaEsperaController {

    private final FilaEsperaService filaEsperaService;

    @PostMapping
    @Operation(summary = "Criar item na fila de espera", description = "Cria um novo item na fila de espera")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Item criado com sucesso",
            content = @Content(schema = @Schema(implementation = FilaEsperaResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<FilaEsperaResponse> criar(@Valid @RequestBody FilaEsperaRequest request) {
        log.debug("REQUEST POST /v1/fila-espera - payload: {}", request);
        try {
            FilaEsperaResponse response = filaEsperaService.criar(request);
            log.info("Item da fila de espera criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar item da fila de espera — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar item da fila de espera — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar fila de espera", description = "Retorna uma lista paginada de itens da fila de espera com filtros opcionais")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<FilaEsperaResponse>> listar(
        @Parameter(description = "Parâmetros de paginação (page, size, sort)")
        Pageable pageable,
        @RequestParam(required = false) UUID pacienteId,
        @RequestParam(required = false) UUID profissionalId,
        @RequestParam(required = false) UUID estabelecimentoId,
        @RequestParam(required = false) UUID especialidadeId,
        @RequestParam(required = false) PrioridadeAtendimentoEnum prioridade,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dataInicio,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dataFim
    ) {
        log.debug("REQUEST GET /v1/fila-espera - pageable: {}, pacienteId: {}, profissionalId: {}, estabelecimentoId: {}, especialidadeId: {}, prioridade: {}, dataInicio: {}, dataFim: {}",
            pageable, pacienteId, profissionalId, estabelecimentoId, especialidadeId, prioridade, dataInicio, dataFim);
        try {
            Page<FilaEsperaResponse> response = filaEsperaService.listar(pageable, pacienteId, profissionalId, estabelecimentoId, especialidadeId, prioridade, dataInicio, dataFim);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar fila de espera", ex);
            throw ex;
        }
    }

    @GetMapping("/pendentes")
    @Operation(summary = "Listar pendentes sem agendamento", description = "Lista itens ativos sem agendamento para um estabelecimento (ordenado por prioridade/dataEntrada)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<List<FilaEsperaResponse>> listarPendentesSemAgendamento(
        @RequestParam UUID estabelecimentoId
    ) {
        log.debug("REQUEST GET /v1/fila-espera/pendentes - estabelecimentoId: {}", estabelecimentoId);
        try {
            List<FilaEsperaResponse> response = filaEsperaService.listarPendentesSemAgendamento(estabelecimentoId);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar pendentes sem agendamento — mensagem: {}, estabelecimentoId: {}", ex.getMessage(), estabelecimentoId);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar pendentes sem agendamento — estabelecimentoId: {}", estabelecimentoId, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter item por ID", description = "Retorna um item específico da fila de espera pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Item encontrado",
            content = @Content(schema = @Schema(implementation = FilaEsperaResponse.class))),
        @ApiResponse(responseCode = "404", description = "Item não encontrado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<FilaEsperaResponse> obterPorId(
        @Parameter(description = "ID do item", required = true)
        @PathVariable UUID id
    ) {
        log.debug("REQUEST GET /v1/fila-espera/{}", id);
        try {
            FilaEsperaResponse response = filaEsperaService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Item da fila de espera não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter item da fila de espera por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar item", description = "Atualiza um item existente da fila de espera")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = FilaEsperaResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "404", description = "Item não encontrado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<FilaEsperaResponse> atualizar(
        @Parameter(description = "ID do item", required = true)
        @PathVariable UUID id,
        @Valid @RequestBody FilaEsperaRequest request
    ) {
        log.debug("REQUEST PUT /v1/fila-espera/{} - payload: {}", id, request);
        try {
            FilaEsperaResponse response = filaEsperaService.atualizar(id, request);
            log.info("Item da fila de espera atualizado com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar item da fila de espera — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar item da fila de espera — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir item", description = "Exclui (desativa) um item da fila de espera")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Item excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Item não encontrado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
        @Parameter(description = "ID do item", required = true)
        @PathVariable UUID id
    ) {
        log.debug("REQUEST DELETE /v1/fila-espera/{}", id);
        try {
            filaEsperaService.excluir(id);
            log.info("Item da fila de espera excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Falha ao excluir item da fila de espera — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir item da fila de espera — ID: {}", id, ex);
            throw ex;
        }
    }
}

