package com.upsaude.controller.clinica.doencas;

import com.upsaude.api.request.clinica.doencas.DoencasRequest;
import com.upsaude.api.response.clinica.doencas.DoencasResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.clinica.doencas.DoencasService;
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
@RequestMapping("/v1/doencas")
@Tag(name = "Doenças", description = "API para gerenciamento de Doenças")
@RequiredArgsConstructor
@Slf4j
public class DoencasController {

    private final DoencasService doencasService;

    @PostMapping
    @Operation(summary = "Criar nova doença", description = "Cria uma nova doença no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Doença criada com sucesso",
                    content = @Content(schema = @Schema(implementation = DoencasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DoencasResponse> criar(@Valid @RequestBody DoencasRequest request) {
        log.debug("REQUEST POST /v1/doencas - payload: {}", request);
        try {
            DoencasResponse response = doencasService.criar(request);
            log.info("Doença criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar doença — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar doença — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar doenças", description = "Retorna uma lista paginada de doenças")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de doenças retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<DoencasResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/doencas - pageable: {}", pageable);
        try {
            Page<DoencasResponse> response = doencasService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar doenças — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar doenças por nome", description = "Retorna uma lista paginada de doenças que contêm o nome informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de doenças retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Nome inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<DoencasResponse>> listarPorNome(
            @Parameter(description = "Nome da doença para busca", required = true)
            @RequestParam String nome,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/doencas/buscar - nome: {}, pageable: {}", nome, pageable);
        try {
            Page<DoencasResponse> response = doencasService.listarPorNome(nome, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao buscar doenças por nome — nome: {}, mensagem: {}", nome, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao buscar doenças por nome — nome: {}, pageable: {}", nome, pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/cid/{codigoCid}")
    @Operation(summary = "Listar doenças por código CID", description = "Retorna uma lista paginada de doenças relacionadas a um código CID específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de doenças retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Código CID inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<DoencasResponse>> listarPorCodigoCid(
            @Parameter(description = "Código CID", required = true)
            @PathVariable String codigoCid,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/doencas/cid/{} - pageable: {}", codigoCid, pageable);

        if (codigoCid == null || codigoCid.trim().isEmpty()) {
            log.warn("Código CID vazio ou nulo recebido para busca de doenças");
            throw new BadRequestException("Código CID é obrigatório para busca");
        }

        try {
            Page<DoencasResponse> response = doencasService.listarPorCodigoCid(codigoCid, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar doenças por código CID — codigoCid: {}, mensagem: {}", codigoCid, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar doenças por código CID — codigoCid: {}, pageable: {}", codigoCid, pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter doença por ID", description = "Retorna uma doença específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doença encontrada",
                    content = @Content(schema = @Schema(implementation = DoencasResponse.class))),
            @ApiResponse(responseCode = "404", description = "Doença não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DoencasResponse> obterPorId(
            @Parameter(description = "ID da doença", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/doencas/{}", id);
        try {
            DoencasResponse response = doencasService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Doença não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter doença por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar doença", description = "Atualiza uma doença existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doença atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = DoencasResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Doença não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<DoencasResponse> atualizar(
            @Parameter(description = "ID da doença", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody DoencasRequest request) {
        log.debug("REQUEST PUT /v1/doencas/{} - payload: {}", id, request);
        try {
            DoencasResponse response = doencasService.atualizar(id, request);
            log.info("Doença atualizada com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar doença — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar doença — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir doença", description = "Exclui (desativa) uma doença do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Doença excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Doença não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da doença", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/doencas/{}", id);
        try {
            doencasService.excluir(id);
            log.info("Doença excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Doença não encontrada para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir doença — ID: {}", id, ex);
            throw ex;
        }
    }
}
