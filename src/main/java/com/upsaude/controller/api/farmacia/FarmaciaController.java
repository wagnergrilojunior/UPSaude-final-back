package com.upsaude.controller.api.farmacia;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upsaude.api.request.farmacia.DispensacaoRequest;
import com.upsaude.api.request.farmacia.FarmaciaRequest;
import com.upsaude.api.response.farmacia.DispensacaoResponse;
import com.upsaude.api.response.farmacia.FarmaciaResponse;
import com.upsaude.api.response.farmacia.ReceitaResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.farmacia.DispensacaoService;
import com.upsaude.service.api.farmacia.FarmaciaService;

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

@RestController
@RequestMapping("/v1/farmacias")
@Tag(name = "Farmácias", description = "API para operações de farmácia (dispensação de medicamentos)")
@RequiredArgsConstructor
@Slf4j
public class FarmaciaController {

    private final DispensacaoService dispensacaoService;
    private final FarmaciaService farmaciaService;

    @GetMapping("/{farmaciaId}/receitas-pendentes")
    @Operation(summary = "Listar receitas pendentes", description = "Retorna uma lista paginada de receitas pendentes de dispensação para a farmácia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de receitas pendentes retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Farmácia não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ReceitaResponse>> listarReceitasPendentes(
            @Parameter(description = "ID da farmácia", required = true)
            @PathVariable UUID farmaciaId,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/farmacias/{}/receitas-pendentes - pageable: {}", farmaciaId, pageable);
        try {
            Page<ReceitaResponse> response = dispensacaoService.listarReceitasPendentes(farmaciaId, pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar receitas pendentes — Path: /v1/farmacias/{}/receitas-pendentes, Method: GET, pageable: {}, Exception: {}",
                farmaciaId, pageable, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @PostMapping("/{farmaciaId}/dispensacoes")
    @Operation(summary = "Registrar dispensação", description = "Registra uma nova dispensação de medicamentos na farmácia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dispensação registrada com sucesso",
                    content = @Content(schema = @Schema(implementation = DispensacaoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Farmácia, paciente ou receita não encontrados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DispensacaoResponse> registrarDispensacao(
            @Parameter(description = "ID da farmácia", required = true)
            @PathVariable UUID farmaciaId,
            @Valid @RequestBody DispensacaoRequest request) {
        log.debug("REQUEST POST /v1/farmacias/{}/dispensacoes - payload: {}", farmaciaId, request);
        try {
            DispensacaoResponse response = dispensacaoService.registrarDispensacao(farmaciaId, request);
            log.info("Dispensação registrada com sucesso. ID: {}, Farmácia: {}", response.getId(), farmaciaId);
            return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao registrar dispensação — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao registrar dispensação — Path: /v1/farmacias/{}/dispensacoes, Method: POST, payload: {}, Exception: {}",
                farmaciaId, request, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @GetMapping("/{farmaciaId}/dispensacoes")
    @Operation(summary = "Listar dispensações", description = "Retorna uma lista paginada de dispensações realizadas na farmácia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de dispensações retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Farmácia não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<DispensacaoResponse>> listarDispensacoes(
            @Parameter(description = "ID da farmácia", required = true)
            @PathVariable UUID farmaciaId,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/farmacias/{}/dispensacoes - pageable: {}", farmaciaId, pageable);
        try {
            Page<DispensacaoResponse> response = dispensacaoService.listarDispensacoes(farmaciaId, pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar dispensações — Path: /v1/farmacias/{}/dispensacoes, Method: GET, pageable: {}, Exception: {}",
                farmaciaId, pageable, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @PostMapping
    @Operation(summary = "Criar nova farmácia", description = "Cria uma nova farmácia no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Farmácia criada com sucesso",
                    content = @Content(schema = @Schema(implementation = FarmaciaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<FarmaciaResponse> criar(@Valid @RequestBody FarmaciaRequest request) {
        log.debug("REQUEST POST /v1/farmacias - payload: {}", request);
        try {
            FarmaciaResponse response = farmaciaService.criar(request);
            log.info("Farmácia criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar farmácia — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar farmácia — Path: /v1/farmacias, Method: POST, payload: {}, Exception: {}",
                request, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @GetMapping({"", "/"})
    @Operation(summary = "Listar farmácias", description = "Retorna uma lista paginada de farmácias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de farmácias retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<FarmaciaResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/farmacias - pageable: {}", pageable);
        try {
            Page<FarmaciaResponse> response = farmaciaService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar farmácias — Path: /v1/farmacias, Method: GET, pageable: {}, Exception: {}",
                pageable, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter farmácia por ID", description = "Retorna uma farmácia específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Farmácia encontrada",
                    content = @Content(schema = @Schema(implementation = FarmaciaResponse.class))),
            @ApiResponse(responseCode = "404", description = "Farmácia não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<FarmaciaResponse> obterPorId(
            @Parameter(description = "ID da farmácia", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/farmacias/{}", id);
        try {
            FarmaciaResponse response = farmaciaService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Farmácia não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter farmácia por ID — Path: /v1/farmacias/{}, Method: GET, ID: {}, Exception: {}",
                id, id, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar farmácia", description = "Atualiza uma farmácia existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Farmácia atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = FarmaciaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Farmácia não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<FarmaciaResponse> atualizar(
            @Parameter(description = "ID da farmácia", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody FarmaciaRequest request) {
        log.debug("REQUEST PUT /v1/farmacias/{} - payload: {}", id, request);
        try {
            FarmaciaResponse response = farmaciaService.atualizar(id, request);
            log.info("Farmácia atualizada com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar farmácia — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar farmácia — Path: /v1/farmacias/{}, Method: PUT, ID: {}, payload: {}, Exception: {}",
                id, id, request, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir farmácia", description = "Remove permanentemente uma farmácia do banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Farmácia excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Farmácia não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da farmácia", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/farmacias/{}", id);
        try {
            farmaciaService.excluir(id);
            log.info("Farmácia excluída permanentemente com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Farmácia não encontrada para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir farmácia — Path: /v1/farmacias/{}, Method: DELETE, ID: {}, Exception: {}",
                id, id, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @PatchMapping("/{id}/inativar")
    @Operation(summary = "Inativar farmácia", description = "Inativa uma farmácia no sistema alterando seu status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Farmácia inativada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Farmácia não encontrada"),
            @ApiResponse(responseCode = "400", description = "Farmácia já está inativa"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> inativar(
            @Parameter(description = "ID da farmácia", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST PATCH /v1/farmacias/{}/inativar", id);
        try {
            farmaciaService.inativar(id);
            log.info("Farmácia inativada com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Farmácia não encontrada para inativação — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inativar farmácia — Path: /v1/farmacias/{}/inativar, Method: PATCH, ID: {}, Exception: {}",
                id, id, ex.getClass().getName(), ex);
            throw ex;
        }
    }
}

