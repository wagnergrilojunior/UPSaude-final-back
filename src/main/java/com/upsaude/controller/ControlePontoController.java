package com.upsaude.controller;

import com.upsaude.api.request.ControlePontoRequest;
import com.upsaude.api.response.ControlePontoResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.ControlePontoService;
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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Controlador REST para operações relacionadas a ControlePonto.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/controle-ponto")
@Tag(name = "Controle de Ponto", description = "API para gerenciamento de Controle de Ponto")
@RequiredArgsConstructor
@Slf4j
public class ControlePontoController {

    private final ControlePontoService controlePontoService;

    @PostMapping
    @Operation(summary = "Criar novo registro de ponto", description = "Cria um novo registro de ponto no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registro de ponto criado com sucesso",
                    content = @Content(schema = @Schema(implementation = ControlePontoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ControlePontoResponse> criar(@Valid @RequestBody ControlePontoRequest request) {
        log.debug("REQUEST POST /v1/controle-ponto - payload: {}", request);
        try {
            ControlePontoResponse response = controlePontoService.criar(request);
            log.info("Registro de ponto criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar registro de ponto — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar registro de ponto — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar registros de ponto", description = "Retorna uma lista paginada de registros de ponto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de registros de ponto retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ControlePontoResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/controle-ponto - pageable: {}", pageable);
        try {
            Page<ControlePontoResponse> response = controlePontoService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar registros de ponto — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/profissional/{profissionalId}")
    @Operation(summary = "Listar registros de ponto por profissional", description = "Retorna uma lista paginada de registros de ponto de um profissional específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de registros de ponto do profissional retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID do profissional inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ControlePontoResponse>> listarPorProfissional(
            @Parameter(description = "ID do profissional de saúde", required = true)
            @PathVariable UUID profissionalId,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/controle-ponto/profissional/{} - pageable: {}", profissionalId, pageable);
        try {
            Page<ControlePontoResponse> response = controlePontoService.listarPorProfissional(profissionalId, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar registros de ponto por profissional — profissionalId: {}, mensagem: {}", profissionalId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar registros de ponto por profissional — profissionalId: {}, pageable: {}", profissionalId, pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/medico/{medicoId}")
    @Operation(summary = "Listar registros de ponto por médico", description = "Retorna uma lista paginada de registros de ponto de um médico específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de registros de ponto do médico retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID do médico inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ControlePontoResponse>> listarPorMedico(
            @Parameter(description = "ID do médico", required = true)
            @PathVariable UUID medicoId,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/controle-ponto/medico/{} - pageable: {}", medicoId, pageable);
        try {
            Page<ControlePontoResponse> response = controlePontoService.listarPorMedico(medicoId, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar registros de ponto por médico — medicoId: {}, mensagem: {}", medicoId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar registros de ponto por médico — medicoId: {}, pageable: {}", medicoId, pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/estabelecimento/{estabelecimentoId}")
    @Operation(summary = "Listar registros de ponto por estabelecimento", description = "Retorna uma lista paginada de registros de ponto de um estabelecimento específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de registros de ponto do estabelecimento retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID do estabelecimento inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ControlePontoResponse>> listarPorEstabelecimento(
            @Parameter(description = "ID do estabelecimento", required = true)
            @PathVariable UUID estabelecimentoId,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/controle-ponto/estabelecimento/{} - pageable: {}", estabelecimentoId, pageable);
        try {
            Page<ControlePontoResponse> response = controlePontoService.listarPorEstabelecimento(estabelecimentoId, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar registros de ponto por estabelecimento — estabelecimentoId: {}, mensagem: {}", estabelecimentoId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar registros de ponto por estabelecimento — estabelecimentoId: {}, pageable: {}", estabelecimentoId, pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/profissional/{profissionalId}/data/{data}")
    @Operation(summary = "Listar registros de ponto por profissional e data", description = "Retorna uma lista paginada de registros de ponto de um profissional em uma data específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de registros de ponto retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ControlePontoResponse>> listarPorProfissionalEData(
            @Parameter(description = "ID do profissional de saúde", required = true)
            @PathVariable UUID profissionalId,
            @Parameter(description = "Data do ponto", required = true)
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/controle-ponto/profissional/{}/data/{} - pageable: {}", profissionalId, data, pageable);
        try {
            Page<ControlePontoResponse> response = controlePontoService.listarPorProfissionalEData(profissionalId, data, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar registros de ponto por profissional e data — profissionalId: {}, data: {}, mensagem: {}", profissionalId, data, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar registros de ponto por profissional e data — profissionalId: {}, data: {}, pageable: {}", profissionalId, data, pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/profissional/{profissionalId}/periodo")
    @Operation(summary = "Listar registros de ponto por profissional e período", description = "Retorna uma lista paginada de registros de ponto de um profissional em um período")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de registros de ponto retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ControlePontoResponse>> listarPorProfissionalEPeriodo(
            @Parameter(description = "ID do profissional de saúde", required = true)
            @PathVariable UUID profissionalId,
            @Parameter(description = "Data de início do período", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @Parameter(description = "Data fim do período", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/controle-ponto/profissional/{}/periodo - dataInicio: {}, dataFim: {}, pageable: {}", profissionalId, dataInicio, dataFim, pageable);
        try {
            Page<ControlePontoResponse> response = controlePontoService.listarPorProfissionalEPeriodo(profissionalId, dataInicio, dataFim, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar registros de ponto por profissional e período — profissionalId: {}, dataInicio: {}, dataFim: {}, mensagem: {}", profissionalId, dataInicio, dataFim, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar registros de ponto por profissional e período — profissionalId: {}, dataInicio: {}, dataFim: {}, pageable: {}", profissionalId, dataInicio, dataFim, pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter registro de ponto por ID", description = "Retorna um registro de ponto específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro de ponto encontrado",
                    content = @Content(schema = @Schema(implementation = ControlePontoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Registro de ponto não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ControlePontoResponse> obterPorId(
            @Parameter(description = "ID do registro de ponto", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/controle-ponto/{}", id);
        try {
            ControlePontoResponse response = controlePontoService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Registro de ponto não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter registro de ponto por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar registro de ponto", description = "Atualiza um registro de ponto existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro de ponto atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = ControlePontoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Registro de ponto não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ControlePontoResponse> atualizar(
            @Parameter(description = "ID do registro de ponto", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody ControlePontoRequest request) {
        log.debug("REQUEST PUT /v1/controle-ponto/{} - payload: {}", id, request);
        try {
            ControlePontoResponse response = controlePontoService.atualizar(id, request);
            log.info("Registro de ponto atualizado com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar registro de ponto — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar registro de ponto — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir registro de ponto", description = "Exclui (desativa) um registro de ponto do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Registro de ponto excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Registro de ponto não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do registro de ponto", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/controle-ponto/{}", id);
        try {
            controlePontoService.excluir(id);
            log.info("Registro de ponto excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Registro de ponto não encontrado para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir registro de ponto — ID: {}", id, ex);
            throw ex;
        }
    }
}
