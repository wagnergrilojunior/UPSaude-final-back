package com.upsaude.controller.saude_publica.puericultura;

import com.upsaude.api.request.saude_publica.puericultura.PuericulturaRequest;
import com.upsaude.api.response.saude_publica.puericultura.PuericulturaResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.saude_publica.puericultura.PuericulturaService;
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

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/puericultura")
@Tag(name = "Puericultura", description = "API para gerenciamento de acompanhamento de saúde da criança")
@RequiredArgsConstructor
@Slf4j
public class PuericulturaController {

    private final PuericulturaService puericulturaService;

    @PostMapping
    @Operation(summary = "Criar nova puericultura", description = "Cria um novo acompanhamento de puericultura no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Puericultura criada com sucesso",
                    content = @Content(schema = @Schema(implementation = PuericulturaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<PuericulturaResponse> criar(@Valid @RequestBody PuericulturaRequest request) {
        log.debug("REQUEST POST /v1/puericultura - payload: {}", request);
        try {
            PuericulturaResponse response = puericulturaService.criar(request);
            log.info("Puericultura criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar puericultura — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar puericultura — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar puericulturas", description = "Retorna uma lista paginada de puericulturas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de puericulturas retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<PuericulturaResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/puericultura - pageable: {}", pageable);
        try {
            Page<PuericulturaResponse> response = puericulturaService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar puericulturas — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter puericultura por ID", description = "Retorna uma puericultura específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Puericultura encontrada",
                    content = @Content(schema = @Schema(implementation = PuericulturaResponse.class))),
            @ApiResponse(responseCode = "404", description = "Puericultura não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<PuericulturaResponse> obterPorId(
            @Parameter(description = "ID da puericultura", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/puericultura/{}", id);
        try {
            PuericulturaResponse response = puericulturaService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Puericultura não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter puericultura por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @GetMapping("/estabelecimento/{estabelecimentoId}")
    @Operation(summary = "Listar puericulturas por estabelecimento", description = "Retorna uma lista paginada de puericulturas por estabelecimento")
    public ResponseEntity<Page<PuericulturaResponse>> listarPorEstabelecimento(
            @PathVariable UUID estabelecimentoId,
            Pageable pageable) {
        log.debug("REQUEST GET /v1/puericultura/estabelecimento/{} - pageable: {}", estabelecimentoId, pageable);
        try {
            Page<PuericulturaResponse> response = puericulturaService.listarPorEstabelecimento(estabelecimentoId, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar puericulturas por estabelecimento — estabelecimentoId: {}, mensagem: {}", estabelecimentoId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar puericulturas por estabelecimento — estabelecimentoId: {}, pageable: {}", estabelecimentoId, pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Listar puericulturas por paciente", description = "Retorna uma lista de puericulturas por paciente")
    public ResponseEntity<List<PuericulturaResponse>> listarPorPaciente(
            @PathVariable UUID pacienteId) {
        log.debug("REQUEST GET /v1/puericultura/paciente/{}", pacienteId);
        try {
            List<PuericulturaResponse> response = puericulturaService.listarPorPaciente(pacienteId);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar puericulturas por paciente — pacienteId: {}, mensagem: {}", pacienteId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar puericulturas por paciente — pacienteId: {}", pacienteId, ex);
            throw ex;
        }
    }

    @GetMapping("/paciente/{pacienteId}/ativo")
    @Operation(summary = "Obter puericultura ativa por paciente", description = "Retorna o acompanhamento de puericultura ativo de um paciente")
    public ResponseEntity<PuericulturaResponse> obterAtivoPorPaciente(@PathVariable UUID pacienteId) {
        log.debug("REQUEST GET /v1/puericultura/paciente/{}/ativo", pacienteId);
        PuericulturaResponse response = puericulturaService.obterAtivoPorPaciente(pacienteId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ativos/{estabelecimentoId}")
    @Operation(summary = "Listar puericulturas ativas", description = "Retorna uma lista paginada de puericulturas com acompanhamento ativo")
    public ResponseEntity<Page<PuericulturaResponse>> listarAtivos(
            @PathVariable UUID estabelecimentoId,
            Pageable pageable) {
        log.debug("REQUEST GET /v1/puericultura/ativos/{} - pageable: {}", estabelecimentoId, pageable);
        try {
            Page<PuericulturaResponse> response = puericulturaService.listarAtivos(estabelecimentoId, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar puericulturas ativas — estabelecimentoId: {}, mensagem: {}", estabelecimentoId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar puericulturas ativas — estabelecimentoId: {}, pageable: {}", estabelecimentoId, pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/estabelecimento/{estabelecimentoId}/periodo")
    @Operation(summary = "Listar puericulturas por período", description = "Retorna uma lista paginada de puericulturas por período (dataInicioAcompanhamento)")
    public ResponseEntity<Page<PuericulturaResponse>> listarPorPeriodo(
        @PathVariable UUID estabelecimentoId,
        @RequestParam LocalDate inicio,
        @RequestParam LocalDate fim,
        Pageable pageable) {
        log.debug("REQUEST GET /v1/puericultura/estabelecimento/{}/periodo - inicio: {}, fim: {}, pageable: {}",
            estabelecimentoId, inicio, fim, pageable);
        Page<PuericulturaResponse> response = puericulturaService.listarPorPeriodo(estabelecimentoId, inicio, fim, pageable);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar puericultura", description = "Atualiza uma puericultura existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Puericultura atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = PuericulturaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Puericultura não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<PuericulturaResponse> atualizar(
            @Parameter(description = "ID da puericultura", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody PuericulturaRequest request) {
        log.debug("REQUEST PUT /v1/puericultura/{} - payload: {}", id, request);
        try {
            PuericulturaResponse response = puericulturaService.atualizar(id, request);
            log.info("Puericultura atualizada com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar puericultura — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar puericultura — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir puericultura", description = "Exclui (desativa) uma puericultura do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Puericultura excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Puericultura não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da puericultura", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/puericultura/{}", id);
        try {
            puericulturaService.excluir(id);
            log.info("Puericultura excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Puericultura não encontrada para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir puericultura — ID: {}", id, ex);
            throw ex;
        }
    }
}
