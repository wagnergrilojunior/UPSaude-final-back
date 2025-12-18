package com.upsaude.controller.profissional;

import com.upsaude.api.request.profissional.EspecialidadesMedicasRequest;
import com.upsaude.api.response.profissional.EspecialidadesMedicasResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.profissional.EspecialidadesMedicasService;
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
@RequestMapping("/v1/especialidades-medicas")
@Tag(name = "Especialidades Médicas", description = "API para gerenciamento de Especialidades Médicas")
@RequiredArgsConstructor
@Slf4j
public class EspecialidadesMedicasController {

    private final EspecialidadesMedicasService especialidadesMedicasService;

    @PostMapping
    @Operation(summary = "Criar nova especialidade médica", description = "Cria uma nova especialidade médica no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Especialidade médica criada com sucesso",
                    content = @Content(schema = @Schema(implementation = EspecialidadesMedicasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EspecialidadesMedicasResponse> criar(@Valid @RequestBody EspecialidadesMedicasRequest request) {
        log.debug("REQUEST POST /v1/especialidades-medicas - payload: {}", request);
        try {
            EspecialidadesMedicasResponse response = especialidadesMedicasService.criar(request);
            log.info("Especialidade médica criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar especialidade médica — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar especialidade médica — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar especialidades médicas", description = "Retorna uma lista paginada de especialidades médicas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de especialidades médicas retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<EspecialidadesMedicasResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/especialidades-medicas - pageable: {}", pageable);
        try {
            Page<EspecialidadesMedicasResponse> response = especialidadesMedicasService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar especialidades médicas — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter especialidade médica por ID", description = "Retorna uma especialidade médica específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Especialidade médica encontrada",
                    content = @Content(schema = @Schema(implementation = EspecialidadesMedicasResponse.class))),
            @ApiResponse(responseCode = "404", description = "Especialidade médica não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EspecialidadesMedicasResponse> obterPorId(
            @Parameter(description = "ID da especialidade médica", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/especialidades-medicas/{}", id);
        try {
            EspecialidadesMedicasResponse response = especialidadesMedicasService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Especialidade médica não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter especialidade médica por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar especialidade médica", description = "Atualiza uma especialidade médica existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Especialidade médica atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = EspecialidadesMedicasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Especialidade médica não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EspecialidadesMedicasResponse> atualizar(
            @Parameter(description = "ID da especialidade médica", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody EspecialidadesMedicasRequest request) {
        log.debug("REQUEST PUT /v1/especialidades-medicas/{} - payload: {}", id, request);
        try {
            EspecialidadesMedicasResponse response = especialidadesMedicasService.atualizar(id, request);
            log.info("Especialidade médica atualizada com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar especialidade médica — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar especialidade médica — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir especialidade médica", description = "Exclui (desativa) uma especialidade médica do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Especialidade médica excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Especialidade médica não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da especialidade médica", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/especialidades-medicas/{}", id);
        try {
            especialidadesMedicasService.excluir(id);
            log.info("Especialidade médica excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Especialidade médica não encontrada para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir especialidade médica — ID: {}", id, ex);
            throw ex;
        }
    }
}
