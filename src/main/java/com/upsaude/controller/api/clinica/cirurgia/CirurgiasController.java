package com.upsaude.controller.api.clinica.cirurgia;

import com.upsaude.api.request.clinica.cirurgia.CirurgiaRequest;
import com.upsaude.api.response.clinica.cirurgia.CirurgiaResponse;
import com.upsaude.enums.StatusCirurgiaEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.clinica.cirurgia.CirurgiaService;
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

import java.time.OffsetDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/v1/cirurgias")
@Tag(name = "Cirurgias", description = "API para gerenciamento de Cirurgias")
@RequiredArgsConstructor
@Slf4j
public class CirurgiasController {

    private final CirurgiaService cirurgiaService;

    @PostMapping
    @Operation(summary = "Criar nova cirurgia", description = "Cria uma nova cirurgia no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cirurgia criada com sucesso",
            content = @Content(schema = @Schema(implementation = CirurgiaResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CirurgiaResponse> criar(@Valid @RequestBody CirurgiaRequest request) {
        log.debug("REQUEST POST /v1/cirurgias - payload: {}", request);
        try {
            CirurgiaResponse response = cirurgiaService.criar(request);
            log.info("Cirurgia criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao criar cirurgia — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar cirurgia — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar cirurgias", description = "Retorna uma lista paginada de cirurgias (tenant-aware)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de cirurgias retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<CirurgiaResponse>> listar(
        @Parameter(description = "Parâmetros de paginação (page, size, sort)")
        Pageable pageable) {
        log.debug("REQUEST GET /v1/cirurgias - pageable: {}", pageable);
        try {
            return ResponseEntity.ok(cirurgiaService.listar(pageable));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar cirurgias — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter cirurgia por ID", description = "Retorna uma cirurgia específica pelo seu ID (tenant-aware)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cirurgia encontrada",
            content = @Content(schema = @Schema(implementation = CirurgiaResponse.class))),
        @ApiResponse(responseCode = "404", description = "Cirurgia não encontrada"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CirurgiaResponse> obterPorId(
        @Parameter(description = "ID da cirurgia", required = true)
        @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/cirurgias/{}", id);
        try {
            return ResponseEntity.ok(cirurgiaService.obterPorId(id));
        } catch (NotFoundException ex) {
            log.warn("Cirurgia não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter cirurgia por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @GetMapping("/por-paciente/{pacienteId}")
    @Operation(summary = "Listar cirurgias por paciente", description = "Retorna uma lista paginada de cirurgias por paciente (tenant-aware)")
    public ResponseEntity<Page<CirurgiaResponse>> listarPorPaciente(
        @Parameter(description = "ID do paciente", required = true)
        @PathVariable UUID pacienteId,
        @Parameter(description = "Parâmetros de paginação (page, size, sort)")
        Pageable pageable) {
        log.debug("REQUEST GET /v1/cirurgias/por-paciente/{} - pageable: {}", pacienteId, pageable);
        return ResponseEntity.ok(cirurgiaService.listarPorPaciente(pacienteId, pageable));
    }

    @GetMapping("/por-cirurgiao-principal/{cirurgiaoPrincipalId}")
    @Operation(summary = "Listar cirurgias por cirurgião principal", description = "Retorna uma lista paginada de cirurgias por cirurgião principal (tenant-aware)")
    public ResponseEntity<Page<CirurgiaResponse>> listarPorCirurgiaoPrincipal(
        @Parameter(description = "ID do cirurgião principal", required = true)
        @PathVariable UUID cirurgiaoPrincipalId,
        @Parameter(description = "Parâmetros de paginação (page, size, sort)")
        Pageable pageable) {
        log.debug("REQUEST GET /v1/cirurgias/por-cirurgiao-principal/{} - pageable: {}", cirurgiaoPrincipalId, pageable);
        return ResponseEntity.ok(cirurgiaService.listarPorCirurgiaoPrincipal(cirurgiaoPrincipalId, pageable));
    }

    @GetMapping("/por-estabelecimento/{estabelecimentoId}")
    @Operation(summary = "Listar cirurgias por estabelecimento", description = "Retorna uma lista paginada de cirurgias por estabelecimento (tenant-aware)")
    public ResponseEntity<Page<CirurgiaResponse>> listarPorEstabelecimento(
        @Parameter(description = "ID do estabelecimento", required = true)
        @PathVariable UUID estabelecimentoId,
        @Parameter(description = "Parâmetros de paginação (page, size, sort)")
        Pageable pageable) {
        log.debug("REQUEST GET /v1/cirurgias/por-estabelecimento/{} - pageable: {}", estabelecimentoId, pageable);
        return ResponseEntity.ok(cirurgiaService.listarPorEstabelecimento(estabelecimentoId, pageable));
    }

    @GetMapping("/por-status/{status}")
    @Operation(summary = "Listar cirurgias por status", description = "Retorna uma lista paginada de cirurgias por status (tenant-aware)")
    public ResponseEntity<Page<CirurgiaResponse>> listarPorStatus(
        @Parameter(description = "Status da cirurgia", required = true)
        @PathVariable StatusCirurgiaEnum status,
        @Parameter(description = "Parâmetros de paginação (page, size, sort)")
        Pageable pageable) {
        log.debug("REQUEST GET /v1/cirurgias/por-status/{} - pageable: {}", status, pageable);
        return ResponseEntity.ok(cirurgiaService.listarPorStatus(status, pageable));
    }

    @GetMapping("/por-periodo")
    @Operation(summary = "Listar cirurgias por período", description = "Retorna uma lista paginada de cirurgias por período (tenant-aware)")
    public ResponseEntity<Page<CirurgiaResponse>> listarPorPeriodo(
        @Parameter(description = "Data/hora início (ISO-8601)", required = true)
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime inicio,
        @Parameter(description = "Data/hora fim (ISO-8601)", required = true)
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime fim,
        @Parameter(description = "Parâmetros de paginação (page, size, sort)")
        Pageable pageable) {
        log.debug("REQUEST GET /v1/cirurgias/por-periodo - inicio: {}, fim: {}, pageable: {}", inicio, fim, pageable);
        return ResponseEntity.ok(cirurgiaService.listarPorPeriodo(inicio, fim, pageable));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cirurgia", description = "Atualiza uma cirurgia existente (tenant-aware)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cirurgia atualizada com sucesso",
            content = @Content(schema = @Schema(implementation = CirurgiaResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "404", description = "Cirurgia não encontrada"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CirurgiaResponse> atualizar(
        @Parameter(description = "ID da cirurgia", required = true)
        @PathVariable UUID id,
        @Valid @RequestBody CirurgiaRequest request) {
        log.debug("REQUEST PUT /v1/cirurgias/{} - payload: {}", id, request);
        try {
            CirurgiaResponse response = cirurgiaService.atualizar(id, request);
            log.info("Cirurgia atualizada com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar cirurgia — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar cirurgia — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir cirurgia", description = "Exclui (desativa) uma cirurgia do sistema (tenant-aware)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cirurgia excluída com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cirurgia não encontrada"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
        @Parameter(description = "ID da cirurgia", required = true)
        @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/cirurgias/{}", id);
        try {
            cirurgiaService.excluir(id);
            log.info("Cirurgia excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Cirurgia não encontrada para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir cirurgia — ID: {}", id, ex);
            throw ex;
        }
    }
}

