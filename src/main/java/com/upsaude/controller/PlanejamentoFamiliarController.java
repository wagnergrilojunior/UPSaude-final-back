package com.upsaude.controller;

import com.upsaude.api.request.PlanejamentoFamiliarRequest;
import com.upsaude.api.response.PlanejamentoFamiliarResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.PlanejamentoFamiliarService;
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

import java.util.List;
import java.util.UUID;

/**
 * Controlador REST para operações relacionadas a Planejamento Familiar.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/planejamento-familiar")
@Tag(name = "Planejamento Familiar", description = "API para gerenciamento de planejamento familiar e reprodutivo")
@RequiredArgsConstructor
@Slf4j
public class PlanejamentoFamiliarController {

    private final PlanejamentoFamiliarService planejamentoFamiliarService;

    @PostMapping
    @Operation(summary = "Criar novo planejamento familiar", description = "Cria um novo acompanhamento de planejamento familiar no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Planejamento familiar criado com sucesso",
                    content = @Content(schema = @Schema(implementation = PlanejamentoFamiliarResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<PlanejamentoFamiliarResponse> criar(@Valid @RequestBody PlanejamentoFamiliarRequest request) {
        log.debug("REQUEST POST /v1/planejamento-familiar - payload: {}", request);
        try {
            PlanejamentoFamiliarResponse response = planejamentoFamiliarService.criar(request);
            log.info("Planejamento familiar criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar planejamento familiar — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar planejamento familiar — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar planejamentos familiares", description = "Retorna uma lista paginada de planejamentos familiares")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de planejamentos familiares retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<PlanejamentoFamiliarResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/planejamento-familiar - pageable: {}", pageable);
        try {
            Page<PlanejamentoFamiliarResponse> response = planejamentoFamiliarService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar planejamentos familiares — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter planejamento familiar por ID", description = "Retorna um planejamento familiar específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Planejamento familiar encontrado",
                    content = @Content(schema = @Schema(implementation = PlanejamentoFamiliarResponse.class))),
            @ApiResponse(responseCode = "404", description = "Planejamento familiar não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<PlanejamentoFamiliarResponse> obterPorId(
            @Parameter(description = "ID do planejamento familiar", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/planejamento-familiar/{}", id);
        try {
            PlanejamentoFamiliarResponse response = planejamentoFamiliarService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Planejamento familiar não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter planejamento familiar por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @GetMapping("/estabelecimento/{estabelecimentoId}")
    @Operation(summary = "Listar planejamentos familiares por estabelecimento", description = "Retorna uma lista paginada de planejamentos familiares por estabelecimento")
    public ResponseEntity<Page<PlanejamentoFamiliarResponse>> listarPorEstabelecimento(
            @PathVariable UUID estabelecimentoId,
            Pageable pageable) {
        log.debug("REQUEST GET /v1/planejamento-familiar/estabelecimento/{} - pageable: {}", estabelecimentoId, pageable);
        try {
            Page<PlanejamentoFamiliarResponse> response = planejamentoFamiliarService.listarPorEstabelecimento(estabelecimentoId, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar planejamentos familiares por estabelecimento — estabelecimentoId: {}, mensagem: {}", estabelecimentoId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar planejamentos familiares por estabelecimento — estabelecimentoId: {}, pageable: {}", estabelecimentoId, pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Listar planejamentos familiares por paciente", description = "Retorna uma lista de planejamentos familiares por paciente")
    public ResponseEntity<List<PlanejamentoFamiliarResponse>> listarPorPaciente(
            @PathVariable UUID pacienteId) {
        log.debug("REQUEST GET /v1/planejamento-familiar/paciente/{}", pacienteId);
        try {
            List<PlanejamentoFamiliarResponse> response = planejamentoFamiliarService.listarPorPaciente(pacienteId);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar planejamentos familiares por paciente — pacienteId: {}, mensagem: {}", pacienteId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar planejamentos familiares por paciente — pacienteId: {}", pacienteId, ex);
            throw ex;
        }
    }

    @GetMapping("/ativos/{estabelecimentoId}")
    @Operation(summary = "Listar planejamentos familiares ativos", description = "Retorna uma lista paginada de planejamentos familiares com acompanhamento ativo")
    public ResponseEntity<Page<PlanejamentoFamiliarResponse>> listarAtivos(
            @PathVariable UUID estabelecimentoId,
            Pageable pageable) {
        log.debug("REQUEST GET /v1/planejamento-familiar/ativos/{} - pageable: {}", estabelecimentoId, pageable);
        try {
            Page<PlanejamentoFamiliarResponse> response = planejamentoFamiliarService.listarAtivos(estabelecimentoId, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar planejamentos familiares ativos — estabelecimentoId: {}, mensagem: {}", estabelecimentoId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar planejamentos familiares ativos — estabelecimentoId: {}, pageable: {}", estabelecimentoId, pageable, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar planejamento familiar", description = "Atualiza um planejamento familiar existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Planejamento familiar atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = PlanejamentoFamiliarResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Planejamento familiar não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<PlanejamentoFamiliarResponse> atualizar(
            @Parameter(description = "ID do planejamento familiar", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody PlanejamentoFamiliarRequest request) {
        log.debug("REQUEST PUT /v1/planejamento-familiar/{} - payload: {}", id, request);
        try {
            PlanejamentoFamiliarResponse response = planejamentoFamiliarService.atualizar(id, request);
            log.info("Planejamento familiar atualizado com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar planejamento familiar — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar planejamento familiar — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir planejamento familiar", description = "Exclui (desativa) um planejamento familiar do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Planejamento familiar excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Planejamento familiar não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do planejamento familiar", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/planejamento-familiar/{}", id);
        try {
            planejamentoFamiliarService.excluir(id);
            log.info("Planejamento familiar excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Planejamento familiar não encontrado para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir planejamento familiar — ID: {}", id, ex);
            throw ex;
        }
    }
}
