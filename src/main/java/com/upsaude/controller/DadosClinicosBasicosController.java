package com.upsaude.controller;

import com.upsaude.api.request.DadosClinicosBasicosRequest;
import com.upsaude.api.response.DadosClinicosBasicosResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.DadosClinicosBasicosService;
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
@RequestMapping("/v1/dados-clinicos-basicos")
@Tag(name = "Dados Clínicos Básicos", description = "API para gerenciamento de Dados Clínicos Básicos")
@RequiredArgsConstructor
@Slf4j
public class DadosClinicosBasicosController {

    private final DadosClinicosBasicosService service;

    @PostMapping
    @Operation(summary = "Criar dados clínicos básicos", description = "Cria novos dados clínicos básicos para um paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dados clínicos básicos criados com sucesso",
                    content = @Content(schema = @Schema(implementation = DadosClinicosBasicosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DadosClinicosBasicosResponse> criar(@Valid @RequestBody DadosClinicosBasicosRequest request) {
        log.debug("REQUEST POST /v1/dados-clinicos-basicos - payload: {}", request);
        try {
            DadosClinicosBasicosResponse response = service.criar(request);
            log.info("Dados clínicos básicos criados com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar dados clínicos básicos — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar dados clínicos básicos — Path: /v1/dados-clinicos-basicos, Method: POST, payload: {}, Exception: {}",
                request, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar dados clínicos básicos", description = "Retorna uma lista paginada de dados clínicos básicos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<DadosClinicosBasicosResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/dados-clinicos-basicos - pageable: {}", pageable);
        try {
            Page<DadosClinicosBasicosResponse> response = service.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar dados clínicos básicos — Path: /v1/dados-clinicos-basicos, Method: GET, pageable: {}, Exception: {}",
                pageable != null ? pageable.toString() : "null", ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter por ID", description = "Retorna dados clínicos básicos específicos pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados encontrados",
                    content = @Content(schema = @Schema(implementation = DadosClinicosBasicosResponse.class))),
            @ApiResponse(responseCode = "404", description = "Dados não encontrados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DadosClinicosBasicosResponse> obterPorId(
            @Parameter(description = "ID dos dados clínicos básicos", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/dados-clinicos-basicos/{}", id);
        try {
            DadosClinicosBasicosResponse response = service.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Dados clínicos básicos não encontrados — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter dados clínicos básicos por ID — Path: /v1/dados-clinicos-basicos/{}, Method: GET, ID: {}, Exception: {}",
                id.toString(), id, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Obter por paciente ID", description = "Retorna dados clínicos básicos de um paciente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados encontrados",
                    content = @Content(schema = @Schema(implementation = DadosClinicosBasicosResponse.class))),
            @ApiResponse(responseCode = "404", description = "Dados não encontrados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DadosClinicosBasicosResponse> obterPorPacienteId(
            @Parameter(description = "ID do paciente", required = true)
            @PathVariable UUID pacienteId) {
        log.debug("REQUEST GET /v1/dados-clinicos-basicos/paciente/{}", pacienteId);
        try {
            DadosClinicosBasicosResponse response = service.obterPorPacienteId(pacienteId);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Dados clínicos básicos não encontrados para paciente — pacienteId: {}, mensagem: {}", pacienteId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter dados clínicos básicos por paciente ID — Path: /v1/dados-clinicos-basicos/paciente/{}, Method: GET, pacienteId: {}, Exception: {}",
                pacienteId.toString(), pacienteId, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados clínicos básicos", description = "Atualiza dados clínicos básicos existentes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados atualizados com sucesso",
                    content = @Content(schema = @Schema(implementation = DadosClinicosBasicosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Dados não encontrados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DadosClinicosBasicosResponse> atualizar(
            @Parameter(description = "ID dos dados clínicos básicos", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody DadosClinicosBasicosRequest request) {
        log.debug("REQUEST PUT /v1/dados-clinicos-basicos/{} - payload: {}", id, request);
        try {
            DadosClinicosBasicosResponse response = service.atualizar(id, request);
            log.info("Dados clínicos básicos atualizados com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar dados clínicos básicos — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar dados clínicos básicos — Path: /v1/dados-clinicos-basicos/{}, Method: PUT, ID: {}, payload: {}, Exception: {}",
                id.toString(), id, request, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir dados clínicos básicos", description = "Exclui (desativa) dados clínicos básicos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Dados excluídos com sucesso"),
            @ApiResponse(responseCode = "404", description = "Dados não encontrados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID dos dados clínicos básicos", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/dados-clinicos-basicos/{}", id);
        try {
            service.excluir(id);
            log.info("Dados clínicos básicos excluídos com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Dados clínicos básicos não encontrados para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir dados clínicos básicos — Path: /v1/dados-clinicos-basicos/{}, Method: DELETE, ID: {}, Exception: {}",
                id.toString(), id, ex.getClass().getName(), ex);
            throw ex;
        }
    }
}
