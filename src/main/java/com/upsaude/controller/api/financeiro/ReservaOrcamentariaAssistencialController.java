package com.upsaude.controller.api.financeiro;

import com.upsaude.api.request.financeiro.ReservaOrcamentariaAssistencialRequest;
import com.upsaude.api.response.financeiro.ReservaOrcamentariaAssistencialResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.financeiro.ReservaOrcamentariaAssistencialService;
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
@RequestMapping("/v1/financeiro/reservas-orcamentarias")
@Tag(name = "Financeiro - Reservas Orçamentárias", description = "API para gerenciamento de Reserva Orçamentária Assistencial")
@RequiredArgsConstructor
@Slf4j
public class ReservaOrcamentariaAssistencialController {

    private final ReservaOrcamentariaAssistencialService service;

    @PostMapping
    @Operation(summary = "Criar reserva orçamentária", description = "Cria uma nova reserva orçamentária assistencial")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reserva criada com sucesso",
                    content = @Content(schema = @Schema(implementation = ReservaOrcamentariaAssistencialResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ReservaOrcamentariaAssistencialResponse> criar(@Valid @RequestBody ReservaOrcamentariaAssistencialRequest request) {
        log.debug("REQUEST POST /v1/financeiro/reservas-orcamentarias - payload: {}", request);
        try {
            ReservaOrcamentariaAssistencialResponse response = service.criar(request);
            log.info("Reserva orçamentária criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar reserva orçamentária — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar reserva orçamentária — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar reservas orçamentárias", description = "Retorna uma lista paginada de reservas orçamentárias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ReservaOrcamentariaAssistencialResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/financeiro/reservas-orcamentarias - pageable: {}", pageable);
        try {
            return ResponseEntity.ok(service.listar(pageable));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar reservas orçamentárias — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter reserva por ID", description = "Retorna uma reserva orçamentária específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva encontrada",
                    content = @Content(schema = @Schema(implementation = ReservaOrcamentariaAssistencialResponse.class))),
            @ApiResponse(responseCode = "404", description = "Reserva não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ReservaOrcamentariaAssistencialResponse> obterPorId(
            @Parameter(description = "ID da reserva", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/financeiro/reservas-orcamentarias/{}", id);
        try {
            return ResponseEntity.ok(service.obterPorId(id));
        } catch (NotFoundException ex) {
            log.warn("Reserva não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter reserva — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar reserva", description = "Atualiza uma reserva orçamentária existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = ReservaOrcamentariaAssistencialResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Reserva não encontrada"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ReservaOrcamentariaAssistencialResponse> atualizar(
            @Parameter(description = "ID da reserva", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody ReservaOrcamentariaAssistencialRequest request) {
        log.debug("REQUEST PUT /v1/financeiro/reservas-orcamentarias/{} - payload: {}", id, request);
        try {
            ReservaOrcamentariaAssistencialResponse response = service.atualizar(id, request);
            log.info("Reserva orçamentária atualizada com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar reserva — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar reserva — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir reserva", description = "Exclui uma reserva orçamentária")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reserva excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Reserva não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da reserva", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/financeiro/reservas-orcamentarias/{}", id);
        try {
            service.excluir(id);
            log.info("Reserva orçamentária excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Reserva não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir reserva — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/inativar")
    @Operation(summary = "Inativar reserva", description = "Inativa uma reserva orçamentária")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reserva inativada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Reserva não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> inativar(
            @Parameter(description = "ID da reserva", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST PUT /v1/financeiro/reservas-orcamentarias/{}/inativar", id);
        try {
            service.inativar(id);
            log.info("Reserva orçamentária inativada com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Reserva não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inativar reserva — ID: {}", id, ex);
            throw ex;
        }
    }
}

