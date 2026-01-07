package com.upsaude.controller.api.clinica.cirurgia;

import com.upsaude.api.request.clinica.cirurgia.EquipeCirurgicaRequest;
import com.upsaude.api.response.clinica.cirurgia.EquipeCirurgicaResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.clinica.cirurgia.EquipeCirurgicaService;
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
@RequestMapping("/v1/equipes-cirurgicas")
@Tag(name = "Equipes Cirúrgicas", description = "API para gerenciamento de Equipes Cirúrgicas")
@RequiredArgsConstructor
@Slf4j
public class EquipeCirurgicaController {

    private final EquipeCirurgicaService equipeCirurgicaService;

    @PostMapping
    @Operation(summary = "Criar nova equipe cirúrgica", description = "Cria uma nova equipe cirúrgica no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Equipe cirúrgica criada com sucesso",
            content = @Content(schema = @Schema(implementation = EquipeCirurgicaResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EquipeCirurgicaResponse> criar(@Valid @RequestBody EquipeCirurgicaRequest request) {
        log.debug("REQUEST POST /v1/equipes-cirurgicas - payload: {}", request);
        try {
            EquipeCirurgicaResponse response = equipeCirurgicaService.criar(request);
            log.info("Equipe cirúrgica criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao criar equipe cirúrgica — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar equipe cirúrgica — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar equipes cirúrgicas", description = "Retorna uma lista paginada de equipes cirúrgicas (tenant-aware)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de equipes cirúrgicas retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<EquipeCirurgicaResponse>> listar(
        @Parameter(description = "Parâmetros de paginação (page, size, sort)")
        Pageable pageable) {
        log.debug("REQUEST GET /v1/equipes-cirurgicas - pageable: {}", pageable);
        try {
            return ResponseEntity.ok(equipeCirurgicaService.listar(pageable));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar equipes cirúrgicas — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/cirurgia/{cirurgiaId}")
    @Operation(summary = "Listar equipes cirúrgicas por cirurgia", description = "Retorna uma lista paginada de equipes cirúrgicas de uma cirurgia específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de equipes cirúrgicas retornada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<EquipeCirurgicaResponse>> listarPorCirurgia(
        @Parameter(description = "ID da cirurgia", required = true)
        @PathVariable UUID cirurgiaId,
        @Parameter(description = "Parâmetros de paginação (page, size, sort)")
        Pageable pageable) {
        log.debug("REQUEST GET /v1/equipes-cirurgicas/cirurgia/{} - pageable: {}", cirurgiaId, pageable);
        try {
            return ResponseEntity.ok(equipeCirurgicaService.listarPorCirurgia(cirurgiaId, pageable));
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar equipes cirúrgicas por cirurgia — cirurgiaId: {}, pageable: {}", cirurgiaId, pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter equipe cirúrgica por ID", description = "Retorna uma equipe cirúrgica específica pelo seu ID (tenant-aware)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Equipe cirúrgica encontrada",
            content = @Content(schema = @Schema(implementation = EquipeCirurgicaResponse.class))),
        @ApiResponse(responseCode = "404", description = "Equipe cirúrgica não encontrada"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EquipeCirurgicaResponse> obterPorId(
        @Parameter(description = "ID da equipe cirúrgica", required = true)
        @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/equipes-cirurgicas/{}", id);
        try {
            return ResponseEntity.ok(equipeCirurgicaService.obterPorId(id));
        } catch (NotFoundException ex) {
            log.warn("Equipe cirúrgica não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter equipe cirúrgica por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar equipe cirúrgica", description = "Atualiza uma equipe cirúrgica existente (tenant-aware)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Equipe cirúrgica atualizada com sucesso",
            content = @Content(schema = @Schema(implementation = EquipeCirurgicaResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "404", description = "Equipe cirúrgica não encontrada"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EquipeCirurgicaResponse> atualizar(
        @Parameter(description = "ID da equipe cirúrgica", required = true)
        @PathVariable UUID id,
        @Valid @RequestBody EquipeCirurgicaRequest request) {
        log.debug("REQUEST PUT /v1/equipes-cirurgicas/{} - payload: {}", id, request);
        try {
            EquipeCirurgicaResponse response = equipeCirurgicaService.atualizar(id, request);
            log.info("Equipe cirúrgica atualizada com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar equipe cirúrgica — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar equipe cirúrgica — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir equipe cirúrgica", description = "Exclui permanentemente uma equipe cirúrgica (tenant-aware)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Equipe cirúrgica excluída com sucesso"),
        @ApiResponse(responseCode = "404", description = "Equipe cirúrgica não encontrada"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
        @Parameter(description = "ID da equipe cirúrgica", required = true)
        @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/equipes-cirurgicas/{}", id);
        try {
            equipeCirurgicaService.excluir(id);
            log.info("Equipe cirúrgica excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Equipe cirúrgica não encontrada para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir equipe cirúrgica — ID: {}", id, ex);
            throw ex;
        }
    }

    @PatchMapping("/{id}/inativar")
    @Operation(summary = "Inativar equipe cirúrgica", description = "Inativa uma equipe cirúrgica (soft delete, tenant-aware)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Equipe cirúrgica inativada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Equipe cirúrgica não encontrada"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> inativar(
        @Parameter(description = "ID da equipe cirúrgica", required = true)
        @PathVariable UUID id) {
        log.debug("REQUEST PATCH /v1/equipes-cirurgicas/{}/inativar", id);
        try {
            equipeCirurgicaService.inativar(id);
            log.info("Equipe cirúrgica inativada com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Equipe cirúrgica não encontrada para inativação — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inativar equipe cirúrgica — ID: {}", id, ex);
            throw ex;
        }
    }
}

