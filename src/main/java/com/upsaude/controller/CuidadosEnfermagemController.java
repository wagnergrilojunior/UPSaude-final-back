package com.upsaude.controller;

import com.upsaude.api.request.CuidadosEnfermagemRequest;
import com.upsaude.api.response.CuidadosEnfermagemResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.CuidadosEnfermagemService;
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

/**
 * Controlador REST para operações relacionadas a Cuidados de Enfermagem.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/v1/cuidados-enfermagem")
@Tag(name = "Cuidados de Enfermagem", description = "API para gerenciamento de cuidados e procedimentos de enfermagem")
@RequiredArgsConstructor
@Slf4j
public class CuidadosEnfermagemController {

    private final CuidadosEnfermagemService cuidadosEnfermagemService;

    @PostMapping
    @Operation(summary = "Criar novo cuidado de enfermagem", description = "Cria um novo registro de cuidado de enfermagem no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cuidado de enfermagem criado com sucesso",
                    content = @Content(schema = @Schema(implementation = CuidadosEnfermagemResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CuidadosEnfermagemResponse> criar(@Valid @RequestBody CuidadosEnfermagemRequest request) {
        log.debug("REQUEST POST /v1/cuidados-enfermagem - payload: {}", request);
        try {
            CuidadosEnfermagemResponse response = cuidadosEnfermagemService.criar(request);
            log.info("Cuidado de enfermagem criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar cuidado de enfermagem — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar cuidado de enfermagem — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar cuidados de enfermagem", description = "Retorna uma lista paginada de cuidados de enfermagem")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cuidados de enfermagem retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<CuidadosEnfermagemResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/cuidados-enfermagem - pageable: {}", pageable);
        try {
            Page<CuidadosEnfermagemResponse> response = cuidadosEnfermagemService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar cuidados de enfermagem — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter cuidado de enfermagem por ID", description = "Retorna um cuidado de enfermagem específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuidado de enfermagem encontrado",
                    content = @Content(schema = @Schema(implementation = CuidadosEnfermagemResponse.class))),
            @ApiResponse(responseCode = "404", description = "Cuidado de enfermagem não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CuidadosEnfermagemResponse> obterPorId(
            @Parameter(description = "ID do cuidado de enfermagem", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/cuidados-enfermagem/{}", id);
        try {
            CuidadosEnfermagemResponse response = cuidadosEnfermagemService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Cuidado de enfermagem não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter cuidado de enfermagem por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @GetMapping("/estabelecimento/{estabelecimentoId}")
    @Operation(summary = "Listar cuidados de enfermagem por estabelecimento", description = "Retorna uma lista paginada de cuidados de enfermagem por estabelecimento")
    public ResponseEntity<Page<CuidadosEnfermagemResponse>> listarPorEstabelecimento(
            @PathVariable UUID estabelecimentoId,
            Pageable pageable) {
        log.debug("REQUEST GET /v1/cuidados-enfermagem/estabelecimento/{} - pageable: {}", estabelecimentoId, pageable);
        try {
            Page<CuidadosEnfermagemResponse> response = cuidadosEnfermagemService.listarPorEstabelecimento(estabelecimentoId, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar cuidados de enfermagem por estabelecimento — estabelecimentoId: {}, mensagem: {}", estabelecimentoId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar cuidados de enfermagem por estabelecimento — estabelecimentoId: {}, pageable: {}", estabelecimentoId, pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Listar cuidados de enfermagem por paciente", description = "Retorna uma lista paginada de cuidados de enfermagem por paciente")
    public ResponseEntity<Page<CuidadosEnfermagemResponse>> listarPorPaciente(
            @PathVariable UUID pacienteId,
            Pageable pageable) {
        log.debug("REQUEST GET /v1/cuidados-enfermagem/paciente/{} - pageable: {}", pacienteId, pageable);
        try {
            Page<CuidadosEnfermagemResponse> response = cuidadosEnfermagemService.listarPorPaciente(pacienteId, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar cuidados de enfermagem por paciente — pacienteId: {}, mensagem: {}", pacienteId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar cuidados de enfermagem por paciente — pacienteId: {}, pageable: {}", pacienteId, pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/profissional/{profissionalId}")
    @Operation(summary = "Listar cuidados de enfermagem por profissional", description = "Retorna uma lista paginada de cuidados de enfermagem por profissional")
    public ResponseEntity<Page<CuidadosEnfermagemResponse>> listarPorProfissional(
            @PathVariable UUID profissionalId,
            Pageable pageable) {
        log.debug("REQUEST GET /v1/cuidados-enfermagem/profissional/{} - pageable: {}", profissionalId, pageable);
        try {
            Page<CuidadosEnfermagemResponse> response = cuidadosEnfermagemService.listarPorProfissional(profissionalId, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar cuidados de enfermagem por profissional — profissionalId: {}, mensagem: {}", profissionalId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar cuidados de enfermagem por profissional — profissionalId: {}, pageable: {}", profissionalId, pageable, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cuidado de enfermagem", description = "Atualiza um cuidado de enfermagem existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuidado de enfermagem atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = CuidadosEnfermagemResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Cuidado de enfermagem não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CuidadosEnfermagemResponse> atualizar(
            @Parameter(description = "ID do cuidado de enfermagem", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody CuidadosEnfermagemRequest request) {
        log.debug("REQUEST PUT /v1/cuidados-enfermagem/{} - payload: {}", id, request);
        try {
            CuidadosEnfermagemResponse response = cuidadosEnfermagemService.atualizar(id, request);
            log.info("Cuidado de enfermagem atualizado com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar cuidado de enfermagem — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar cuidado de enfermagem — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir cuidado de enfermagem", description = "Exclui (desativa) um cuidado de enfermagem do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cuidado de enfermagem excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cuidado de enfermagem não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do cuidado de enfermagem", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/cuidados-enfermagem/{}", id);
        try {
            cuidadosEnfermagemService.excluir(id);
            log.info("Cuidado de enfermagem excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Cuidado de enfermagem não encontrado para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir cuidado de enfermagem — ID: {}", id, ex);
            throw ex;
        }
    }
}
