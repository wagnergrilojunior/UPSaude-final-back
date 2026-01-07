package com.upsaude.controller.api.clinica.atendimento;

import com.upsaude.api.request.clinica.atendimento.CheckInAtendimentoRequest;
import com.upsaude.api.response.clinica.atendimento.CheckInAtendimentoResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.clinica.atendimento.CheckInAtendimentoService;
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
@RequestMapping("/v1/checkin-atendimento")
@Tag(name = "Check-In Atendimento", description = "API para gerenciamento de check-in de atendimentos")
@RequiredArgsConstructor
@Slf4j
public class CheckInAtendimentoController {

    private final CheckInAtendimentoService checkInAtendimentoService;

    @PostMapping
    @Operation(summary = "Criar novo check-in", description = "Cria um novo check-in de atendimento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Check-in criado com sucesso",
                    content = @Content(schema = @Schema(implementation = CheckInAtendimentoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CheckInAtendimentoResponse> criar(@Valid @RequestBody CheckInAtendimentoRequest request) {
        log.debug("REQUEST POST /v1/checkin-atendimento - payload: {}", request);
        try {
            CheckInAtendimentoResponse response = checkInAtendimentoService.criar(request);
            log.info("Check-in criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao criar check-in — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar check-in — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar check-ins", description = "Retorna uma lista paginada de todos os check-ins")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de check-ins retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<CheckInAtendimentoResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/checkin-atendimento - pageable: {}", pageable);
        try {
            Page<CheckInAtendimentoResponse> response = checkInAtendimentoService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar check-ins — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter check-in por ID", description = "Retorna um check-in específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Check-in encontrado",
                    content = @Content(schema = @Schema(implementation = CheckInAtendimentoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Check-in não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CheckInAtendimentoResponse> obterPorId(
            @Parameter(description = "ID do check-in", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/checkin-atendimento/{}", id);
        try {
            CheckInAtendimentoResponse response = checkInAtendimentoService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Check-in não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter check-in por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar check-in", description = "Atualiza um check-in existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Check-in atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = CheckInAtendimentoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Check-in não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CheckInAtendimentoResponse> atualizar(
            @Parameter(description = "ID do check-in", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody CheckInAtendimentoRequest request) {
        log.debug("REQUEST PUT /v1/checkin-atendimento/{} - payload: {}", id, request);
        try {
            CheckInAtendimentoResponse response = checkInAtendimentoService.atualizar(id, request);
            log.info("Check-in atualizado com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar check-in — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar check-in — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir check-in", description = "Exclui um check-in")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Check-in excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Check-in não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do check-in", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/checkin-atendimento/{}", id);
        try {
            checkInAtendimentoService.excluir(id);
            log.info("Check-in excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Check-in não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir check-in — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/inativar")
    @Operation(summary = "Inativar check-in", description = "Inativa um check-in")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Check-in inativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Check-in não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> inativar(
            @Parameter(description = "ID do check-in", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST PUT /v1/checkin-atendimento/{}/inativar", id);
        try {
            checkInAtendimentoService.inativar(id);
            log.info("Check-in inativado com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Check-in não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inativar check-in — ID: {}", id, ex);
            throw ex;
        }
    }
}

