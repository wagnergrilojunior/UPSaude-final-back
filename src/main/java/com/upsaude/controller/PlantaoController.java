package com.upsaude.controller;

import com.upsaude.api.request.PlantaoRequest;
import com.upsaude.api.response.PlantaoResponse;
import com.upsaude.enums.TipoPlantaoEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.PlantaoService;
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

@Slf4j
@RestController
@RequestMapping("/v1/plantoes")
@Tag(name = "Plantões", description = "API para gerenciamento de Plantões")
@RequiredArgsConstructor
public class PlantaoController {

    private final PlantaoService plantaoService;

    @PostMapping
    @Operation(summary = "Criar novo plantão", description = "Cria um novo plantão no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Plantão criado com sucesso",
            content = @Content(schema = @Schema(implementation = PlantaoResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<PlantaoResponse> criar(@Valid @RequestBody PlantaoRequest request) {
        log.debug("REQUEST POST /v1/plantoes - payload: {}", request);
        try {
            PlantaoResponse response = plantaoService.criar(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar plantão — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar plantão — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter plantão por ID", description = "Retorna um plantão específico pelo seu ID")
    public ResponseEntity<PlantaoResponse> obterPorId(@PathVariable UUID id) {
        log.debug("REQUEST GET /v1/plantoes/{}", id);
        try {
            return ResponseEntity.ok(plantaoService.obterPorId(id));
        } catch (NotFoundException ex) {
            log.warn("Plantão não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter plantão por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar plantões", description = "Lista plantões com filtros opcionais")
    public ResponseEntity<Page<PlantaoResponse>> listar(
        @Parameter(description = "Parâmetros de paginação (page, size, sort)") Pageable pageable,
        @RequestParam(required = false) UUID profissionalId,
        @RequestParam(required = false) UUID medicoId,
        @RequestParam(required = false) UUID estabelecimentoId,
        @RequestParam(required = false) TipoPlantaoEnum tipoPlantao,
        @RequestParam(required = false) OffsetDateTime dataInicio,
        @RequestParam(required = false) OffsetDateTime dataFim,
        @RequestParam(required = false) Boolean emAndamento
    ) {
        log.debug("REQUEST GET /v1/plantoes - pageable: {}, profissionalId: {}, medicoId: {}, estabelecimentoId: {}, tipo: {}, dataInicio: {}, dataFim: {}, emAndamento: {}",
            pageable, profissionalId, medicoId, estabelecimentoId, tipoPlantao, dataInicio, dataFim, emAndamento);
        return ResponseEntity.ok(
            plantaoService.listar(pageable, profissionalId, medicoId, estabelecimentoId, tipoPlantao, dataInicio, dataFim, emAndamento)
        );
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar plantão", description = "Atualiza um plantão existente")
    public ResponseEntity<PlantaoResponse> atualizar(@PathVariable UUID id, @Valid @RequestBody PlantaoRequest request) {
        log.debug("REQUEST PUT /v1/plantoes/{} - payload: {}", id, request);
        try {
            PlantaoResponse response = plantaoService.atualizar(id, request);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar plantão — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar plantão — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir plantão", description = "Exclui (inativa) um plantão do sistema")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/plantoes/{}", id);
        try {
            plantaoService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Plantão não encontrado para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir plantão — ID: {}", id, ex);
            throw ex;
        }
    }
}

