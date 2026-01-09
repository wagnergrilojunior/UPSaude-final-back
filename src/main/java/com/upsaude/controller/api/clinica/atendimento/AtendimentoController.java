package com.upsaude.controller.api.clinica.atendimento;

import com.upsaude.api.request.clinica.atendimento.AtendimentoCreateRequest;
import com.upsaude.api.request.clinica.atendimento.AtendimentoTriagemRequest;
import com.upsaude.api.response.clinica.atendimento.AtendimentoResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.clinica.atendimento.AtendimentoService;
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
@RequestMapping("/v1/atendimentos")
@Tag(name = "Atendimentos", description = "API para gerenciamento de atendimentos (acolhimento/triagem/procedimentos)")
@RequiredArgsConstructor
@Slf4j
public class AtendimentoController {

    private final AtendimentoService atendimentoService;

    @PostMapping
    @Operation(summary = "Criar novo atendimento", description = "Cria um novo atendimento com status inicial AGENDADO ou EM_ATENDIMENTO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Atendimento criado com sucesso",
                    content = @Content(schema = @Schema(implementation = AtendimentoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AtendimentoResponse> criar(@Valid @RequestBody AtendimentoCreateRequest request) {
        log.debug("REQUEST POST /v1/atendimentos - payload: {}", request);
        try {
            AtendimentoResponse response = atendimentoService.criar(request);
            log.info("Atendimento criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao criar atendimento — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar atendimento — payload: {}", request, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/iniciar")
    @Operation(summary = "Iniciar atendimento", description = "Inicia um atendimento, alterando status para EM_ANDAMENTO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atendimento iniciado com sucesso",
                    content = @Content(schema = @Schema(implementation = AtendimentoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Atendimento não pode ser iniciado"),
            @ApiResponse(responseCode = "404", description = "Atendimento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AtendimentoResponse> iniciar(
            @Parameter(description = "ID do atendimento", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST PUT /v1/atendimentos/{}/iniciar", id);
        try {
            AtendimentoResponse response = atendimentoService.iniciar(id);
            log.info("Atendimento iniciado com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao iniciar atendimento — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao iniciar atendimento — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/triagem")
    @Operation(summary = "Atualizar triagem", description = "Atualiza dados de triagem (anamnese e classificação de risco) do atendimento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Triagem atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = AtendimentoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Atendimento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AtendimentoResponse> atualizarTriagem(
            @Parameter(description = "ID do atendimento", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody AtendimentoTriagemRequest request) {
        log.debug("REQUEST PUT /v1/atendimentos/{}/triagem - payload: {}", id, request);
        try {
            AtendimentoResponse response = atendimentoService.atualizarTriagem(id, request);
            log.info("Triagem do atendimento atualizada com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar triagem — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar triagem — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/classificacao-risco")
    @Operation(summary = "Atualizar classificação de risco", description = "Atualiza a classificação de risco do atendimento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Classificação de risco atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = AtendimentoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Atendimento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AtendimentoResponse> atualizarClassificacaoRisco(
            @Parameter(description = "ID do atendimento", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody AtendimentoTriagemRequest request) {
        log.debug("REQUEST PUT /v1/atendimentos/{}/classificacao-risco - payload: {}", id, request);
        try {
            AtendimentoResponse response = atendimentoService.atualizarClassificacaoRisco(id, request);
            log.info("Classificação de risco do atendimento atualizada com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar classificação de risco — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar classificação de risco — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/encerrar")
    @Operation(summary = "Encerrar atendimento", description = "Encerra um atendimento, alterando status para CONCLUIDO e criando registro no prontuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atendimento encerrado com sucesso",
                    content = @Content(schema = @Schema(implementation = AtendimentoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Atendimento não pode ser encerrado"),
            @ApiResponse(responseCode = "404", description = "Atendimento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AtendimentoResponse> encerrar(
            @Parameter(description = "ID do atendimento", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST PUT /v1/atendimentos/{}/encerrar", id);
        try {
            AtendimentoResponse response = atendimentoService.encerrar(id);
            log.info("Atendimento encerrado com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao encerrar atendimento — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao encerrar atendimento — ID: {}", id, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar atendimentos", description = "Retorna uma lista paginada de todos os atendimentos do tenant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de atendimentos retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<AtendimentoResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/atendimentos - pageable: {}", pageable);
        try {
            Page<AtendimentoResponse> response = atendimentoService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar atendimentos — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/pacientes/{pacienteId}/atendimentos")
    @Operation(summary = "Listar atendimentos do paciente", description = "Retorna uma lista paginada de atendimentos de um paciente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de atendimentos retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<AtendimentoResponse>> listarPorPaciente(
            @Parameter(description = "ID do paciente", required = true)
            @PathVariable UUID pacienteId,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/atendimentos/pacientes/{}/atendimentos - pageable: {}", pacienteId, pageable);
        try {
            Page<AtendimentoResponse> response = atendimentoService.listarPorPaciente(pacienteId, pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar atendimentos do paciente — pacienteId: {}, pageable: {}", pacienteId, pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter atendimento por ID", description = "Retorna um atendimento específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atendimento encontrado",
                    content = @Content(schema = @Schema(implementation = AtendimentoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Atendimento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<AtendimentoResponse> obterPorId(
            @Parameter(description = "ID do atendimento", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/atendimentos/{}", id);
        try {
            AtendimentoResponse response = atendimentoService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Atendimento não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter atendimento por ID — ID: {}", id, ex);
            throw ex;
        }
    }
}

