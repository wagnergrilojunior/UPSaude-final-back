package com.upsaude.controller.saude_publica.visita;

import com.upsaude.api.request.saude_publica.visita.VisitasDomiciliaresRequest;
import com.upsaude.api.response.saude_publica.visita.VisitasDomiciliaresResponse;
import com.upsaude.enums.TipoVisitaDomiciliarEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.saude_publica.visita.VisitasDomiciliaresService;
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

import java.time.OffsetDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/v1/visitas-domiciliares")
@Tag(name = "Visitas Domiciliares", description = "API para gerenciamento de Visitas Domiciliares")
@RequiredArgsConstructor
@Slf4j
public class VisitasDomiciliaresController {

    private final VisitasDomiciliaresService visitasDomiciliaresService;

    @PostMapping
    @Operation(summary = "Criar nova visita domiciliar", description = "Cria uma nova visita domiciliar no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Visita domiciliar criada com sucesso",
                    content = @Content(schema = @Schema(implementation = VisitasDomiciliaresResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<VisitasDomiciliaresResponse> criar(@Valid @RequestBody VisitasDomiciliaresRequest request) {
        log.debug("REQUEST POST /v1/visitas-domiciliares - payload: {}", request);
        try {
            VisitasDomiciliaresResponse response = visitasDomiciliaresService.criar(request);
            log.info("Visita domiciliar criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar visita domiciliar — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar visita domiciliar — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar visitas domiciliares", description = "Retorna uma lista paginada de visitas domiciliares")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de visitas domiciliares retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<VisitasDomiciliaresResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable,
            @RequestParam(required = false) UUID estabelecimentoId,
            @RequestParam(required = false) UUID pacienteId,
            @RequestParam(required = false) UUID profissionalId,
            @RequestParam(required = false) TipoVisitaDomiciliarEnum tipoVisita,
            @RequestParam(required = false) OffsetDateTime inicio,
            @RequestParam(required = false) OffsetDateTime fim) {
        log.debug("REQUEST GET /v1/visitas-domiciliares - pageable: {}, estabelecimentoId: {}, pacienteId: {}, profissionalId: {}, tipoVisita: {}, inicio: {}, fim: {}",
            pageable, estabelecimentoId, pacienteId, profissionalId, tipoVisita, inicio, fim);
        try {
            Page<VisitasDomiciliaresResponse> response = visitasDomiciliaresService.listar(pageable, estabelecimentoId, pacienteId, profissionalId, tipoVisita, inicio, fim);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar visitas domiciliares — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter visita domiciliar por ID", description = "Retorna uma visita domiciliar específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Visita domiciliar encontrada",
                    content = @Content(schema = @Schema(implementation = VisitasDomiciliaresResponse.class))),
            @ApiResponse(responseCode = "404", description = "Visita domiciliar não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<VisitasDomiciliaresResponse> obterPorId(
            @Parameter(description = "ID da visita domiciliar", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/visitas-domiciliares/{}", id);
        try {
            VisitasDomiciliaresResponse response = visitasDomiciliaresService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Visita domiciliar não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter visita domiciliar por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar visita domiciliar", description = "Atualiza uma visita domiciliar existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Visita domiciliar atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = VisitasDomiciliaresResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Visita domiciliar não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<VisitasDomiciliaresResponse> atualizar(
            @Parameter(description = "ID da visita domiciliar", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody VisitasDomiciliaresRequest request) {
        log.debug("REQUEST PUT /v1/visitas-domiciliares/{} - payload: {}", id, request);
        try {
            VisitasDomiciliaresResponse response = visitasDomiciliaresService.atualizar(id, request);
            log.info("Visita domiciliar atualizada com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar visita domiciliar — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar visita domiciliar — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir visita domiciliar", description = "Exclui (desativa) uma visita domiciliar do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Visita domiciliar excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Visita domiciliar não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da visita domiciliar", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/visitas-domiciliares/{}", id);
        try {
            visitasDomiciliaresService.excluir(id);
            log.info("Visita domiciliar excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Visita domiciliar não encontrada para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir visita domiciliar — ID: {}", id, ex);
            throw ex;
        }
    }
}
