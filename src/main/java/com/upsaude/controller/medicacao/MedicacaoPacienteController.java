package com.upsaude.controller.medicacao;

import com.upsaude.entity.paciente.Paciente;

import com.upsaude.api.request.medicacao.MedicacaoPacienteRequest;
import com.upsaude.api.request.MedicacaoPacienteSimplificadoRequest;
import com.upsaude.api.response.medicacao.MedicacaoPacienteResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.medicacao.MedicacaoPacienteService;
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
@RequestMapping("/v1/medicacoes-paciente")
@Tag(name = "Medicações de Paciente", description = "API para gerenciamento de Medicações de Paciente")
@RequiredArgsConstructor
@Slf4j
public class MedicacaoPacienteController {

    private final MedicacaoPacienteService medicacaoPacienteService;

    @PostMapping
    @Operation(summary = "Criar nova ligação paciente-medicação", description = "Cria uma nova ligação entre paciente e medicação no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ligação paciente-medicação criada com sucesso",
                    content = @Content(schema = @Schema(implementation = MedicacaoPacienteResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MedicacaoPacienteResponse> criar(@Valid @RequestBody MedicacaoPacienteRequest request) {
        log.debug("REQUEST POST /v1/medicacoes-paciente - payload: {}", request);
        try {
            MedicacaoPacienteResponse response = medicacaoPacienteService.criar(request);
            log.info("Ligação paciente-medicação criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar ligação paciente-medicação — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar ligação paciente-medicação — payload: {}", request, ex);
            throw ex;
        }
    }

    @PostMapping("/simplificado")
    @Operation(summary = "Criar ligação paciente-medicação simplificada", description = "Cria uma nova ligação entre paciente e medicação informando apenas paciente, tenant e medicação. Os demais campos são criados com valores padrão")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ligação paciente-medicação criada com sucesso",
                    content = @Content(schema = @Schema(implementation = MedicacaoPacienteResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Paciente, tenant ou medicação não encontrados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MedicacaoPacienteResponse> criarSimplificado(@Valid @RequestBody MedicacaoPacienteSimplificadoRequest request) {
        log.debug("REQUEST POST /v1/medicacoes-paciente/simplificado - payload: {}", request);
        try {
            MedicacaoPacienteResponse response = medicacaoPacienteService.criarSimplificado(request);
            log.info("Ligação paciente-medicação criada com sucesso (simplificado). ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao criar ligação paciente-medicação simplificada — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar ligação paciente-medicação simplificada — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar ligações paciente-medicação", description = "Retorna uma lista paginada de ligações paciente-medicação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ligações paciente-medicação retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<MedicacaoPacienteResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/medicacoes-paciente - pageable: {}", pageable);
        try {
            Page<MedicacaoPacienteResponse> response = medicacaoPacienteService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar ligações paciente-medicação — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter ligação paciente-medicação por ID", description = "Retorna uma ligação paciente-medicação específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ligação paciente-medicação encontrada",
                    content = @Content(schema = @Schema(implementation = MedicacaoPacienteResponse.class))),
            @ApiResponse(responseCode = "404", description = "Ligação paciente-medicação não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MedicacaoPacienteResponse> obterPorId(
            @Parameter(description = "ID da ligação paciente-medicação", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/medicacoes-paciente/{}", id);
        try {
            MedicacaoPacienteResponse response = medicacaoPacienteService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Ligação paciente-medicação não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter ligação paciente-medicação por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar ligação paciente-medicação", description = "Atualiza uma ligação paciente-medicação existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ligação paciente-medicação atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = MedicacaoPacienteResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Ligação paciente-medicação não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<MedicacaoPacienteResponse> atualizar(
            @Parameter(description = "ID da ligação paciente-medicação", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody MedicacaoPacienteRequest request) {
        log.debug("REQUEST PUT /v1/medicacoes-paciente/{} - payload: {}", id, request);
        try {
            MedicacaoPacienteResponse response = medicacaoPacienteService.atualizar(id, request);
            log.info("Ligação paciente-medicação atualizada com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar ligação paciente-medicação — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar ligação paciente-medicação — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir ligação paciente-medicação", description = "Exclui (desativa) uma ligação paciente-medicação do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ligação paciente-medicação excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ligação paciente-medicação não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da ligação paciente-medicação", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/medicacoes-paciente/{}", id);
        try {
            medicacaoPacienteService.excluir(id);
            log.info("Ligação paciente-medicação excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Ligação paciente-medicação não encontrada para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir ligação paciente-medicação — ID: {}", id, ex);
            throw ex;
        }
    }
}
