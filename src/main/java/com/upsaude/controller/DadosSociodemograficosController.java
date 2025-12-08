package com.upsaude.controller;

import com.upsaude.api.request.DadosSociodemograficosRequest;
import com.upsaude.api.response.DadosSociodemograficosResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.DadosSociodemograficosService;
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
@RequestMapping("/v1/dados-sociodemograficos")
@Tag(name = "Dados Sociodemográficos", description = "API para gerenciamento de Dados Sociodemográficos")
@RequiredArgsConstructor
public class DadosSociodemograficosController {

    private final DadosSociodemograficosService service;

    @PostMapping
    @Operation(summary = "Criar dados sociodemográficos", description = "Cria novos dados sociodemográficos para um paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dados sociodemográficos criados com sucesso",
                    content = @Content(schema = @Schema(implementation = DadosSociodemograficosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DadosSociodemograficosResponse> criar(@Valid @RequestBody DadosSociodemograficosRequest request) {
        log.debug("REQUEST POST /v1/dados-sociodemograficos - payload: {}", request);
        try {
            DadosSociodemograficosResponse response = service.criar(request);
            log.info("Dados sociodemográficos criados com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar dados sociodemográficos — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar dados sociodemográficos — Path: /v1/dados-sociodemograficos, Method: POST, payload: {}, Exception: {}",
                request, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar dados sociodemográficos", description = "Retorna uma lista paginada de dados sociodemográficos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<DadosSociodemograficosResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/dados-sociodemograficos - pageable: {}", pageable);
        try {
            Page<DadosSociodemograficosResponse> response = service.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar dados sociodemográficos — Path: /v1/dados-sociodemograficos, Method: GET, pageable: {}, Exception: {}",
                pageable != null ? pageable.toString() : "null", ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter por ID", description = "Retorna dados sociodemográficos específicos pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados encontrados",
                    content = @Content(schema = @Schema(implementation = DadosSociodemograficosResponse.class))),
            @ApiResponse(responseCode = "404", description = "Dados não encontrados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DadosSociodemograficosResponse> obterPorId(
            @Parameter(description = "ID dos dados sociodemográficos", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/dados-sociodemograficos/{}", id);
        try {
            DadosSociodemograficosResponse response = service.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Dados sociodemográficos não encontrados — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter dados sociodemográficos por ID — Path: /v1/dados-sociodemograficos/{}, Method: GET, ID: {}, Exception: {}",
                id.toString(), id, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Obter por paciente ID", description = "Retorna dados sociodemográficos de um paciente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados encontrados",
                    content = @Content(schema = @Schema(implementation = DadosSociodemograficosResponse.class))),
            @ApiResponse(responseCode = "404", description = "Dados não encontrados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DadosSociodemograficosResponse> obterPorPacienteId(
            @Parameter(description = "ID do paciente", required = true)
            @PathVariable UUID pacienteId) {
        log.debug("REQUEST GET /v1/dados-sociodemograficos/paciente/{}", pacienteId);
        try {
            DadosSociodemograficosResponse response = service.obterPorPacienteId(pacienteId);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Dados sociodemográficos não encontrados para paciente — pacienteId: {}, mensagem: {}", pacienteId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter dados sociodemográficos por paciente ID — Path: /v1/dados-sociodemograficos/paciente/{}, Method: GET, pacienteId: {}, Exception: {}",
                pacienteId.toString(), pacienteId, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados sociodemográficos", description = "Atualiza dados sociodemográficos existentes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados atualizados com sucesso",
                    content = @Content(schema = @Schema(implementation = DadosSociodemograficosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Dados não encontrados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DadosSociodemograficosResponse> atualizar(
            @Parameter(description = "ID dos dados sociodemográficos", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody DadosSociodemograficosRequest request) {
        log.debug("REQUEST PUT /v1/dados-sociodemograficos/{} - payload: {}", id, request);
        try {
            DadosSociodemograficosResponse response = service.atualizar(id, request);
            log.info("Dados sociodemográficos atualizados com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar dados sociodemográficos — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar dados sociodemográficos — Path: /v1/dados-sociodemograficos/{}, Method: PUT, ID: {}, payload: {}, Exception: {}",
                id.toString(), id, request, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir dados sociodemográficos", description = "Exclui (desativa) dados sociodemográficos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Dados excluídos com sucesso"),
            @ApiResponse(responseCode = "404", description = "Dados não encontrados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID dos dados sociodemográficos", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/dados-sociodemograficos/{}", id);
        try {
            service.excluir(id);
            log.info("Dados sociodemográficos excluídos com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Falha ao excluir dados sociodemográficos — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir dados sociodemográficos — Path: /v1/dados-sociodemograficos/{}, Method: DELETE, ID: {}, Exception: {}",
                id.toString(), id, ex.getClass().getName(), ex);
            throw ex;
        }
    }
}

