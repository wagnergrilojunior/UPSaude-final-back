package com.upsaude.controller.api.sistema.integracao;

import com.upsaude.api.request.sistema.integracao.IntegracaoGovRequest;
import com.upsaude.api.response.sistema.integracao.IntegracaoGovResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.sistema.integracao.IntegracaoGovService;
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

@Slf4j
@RestController
@RequestMapping("/v1/integracao-gov")
@Tag(name = "Integração Governamental", description = "API para gerenciamento de Integração Governamental")
@RequiredArgsConstructor
public class IntegracaoGovController {

    private final IntegracaoGovService service;

    @PostMapping
    @Operation(summary = "Criar integração gov", description = "Cria nova integração governamental para um paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Integração gov criada com sucesso",
                    content = @Content(schema = @Schema(implementation = IntegracaoGovResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<IntegracaoGovResponse> criar(@Valid @RequestBody IntegracaoGovRequest request) {
        log.debug("REQUEST POST /v1/integracao-gov - payload: {}", request);
        try {
            IntegracaoGovResponse response = service.criar(request);
            log.info("Integração governamental criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar integração governamental — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar integração governamental — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar integrações gov", description = "Retorna uma lista paginada de integrações governamentais")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<IntegracaoGovResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/integracao-gov - pageable: {}", pageable);
        try {
            Page<IntegracaoGovResponse> response = service.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar integrações governamentais — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter por ID", description = "Retorna integração gov específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Integração encontrada",
                    content = @Content(schema = @Schema(implementation = IntegracaoGovResponse.class))),
            @ApiResponse(responseCode = "404", description = "Integração não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<IntegracaoGovResponse> obterPorId(
            @Parameter(description = "ID da integração gov", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/integracao-gov/{}", id);
        try {
            IntegracaoGovResponse response = service.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Integração governamental não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter integração governamental por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Obter por paciente ID", description = "Retorna integração gov de um paciente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Integração encontrada",
                    content = @Content(schema = @Schema(implementation = IntegracaoGovResponse.class))),
            @ApiResponse(responseCode = "404", description = "Integração não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<IntegracaoGovResponse> obterPorPacienteId(
            @Parameter(description = "ID do paciente", required = true)
            @PathVariable UUID pacienteId) {
        log.debug("REQUEST GET /v1/integracao-gov/paciente/{}", pacienteId);
        try {
            IntegracaoGovResponse response = service.obterPorPacienteId(pacienteId);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Integração governamental não encontrada para paciente — pacienteId: {}, mensagem: {}", pacienteId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter integração governamental por paciente ID — pacienteId: {}", pacienteId, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar integração gov", description = "Atualiza integração governamental existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Integração atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = IntegracaoGovResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Integração não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<IntegracaoGovResponse> atualizar(
            @Parameter(description = "ID da integração gov", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody IntegracaoGovRequest request) {
        log.debug("REQUEST PUT /v1/integracao-gov/{} - payload: {}", id, request);
        try {
            IntegracaoGovResponse response = service.atualizar(id, request);
            log.info("Integração governamental atualizada com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar integração governamental — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar integração governamental — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir integração gov", description = "Exclui (desativa) integração governamental")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Integração excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Integração não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da integração gov", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/integracao-gov/{}", id);
        try {
            service.excluir(id);
            log.info("Integração governamental excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Falha ao excluir integração governamental — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir integração governamental — ID: {}", id, ex);
            throw ex;
        }
    }
}
