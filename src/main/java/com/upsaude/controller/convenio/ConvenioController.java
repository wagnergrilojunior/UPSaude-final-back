package com.upsaude.controller.convenio;

import com.upsaude.api.request.convenio.ConvenioRequest;
import com.upsaude.api.response.convenio.ConvenioResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.convenio.ConvenioService;
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
@RequestMapping("/v1/convenios")
@Tag(name = "Convênios", description = "API para gerenciamento de Convênios")
@RequiredArgsConstructor
@Slf4j
public class ConvenioController {

    private final ConvenioService convenioService;

    @PostMapping
    @Operation(summary = "Criar novo convênio", description = "Cria um novo convênio no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Convênio criado com sucesso",
                    content = @Content(schema = @Schema(implementation = ConvenioResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ConvenioResponse> criar(@Valid @RequestBody ConvenioRequest request) {
        log.debug("REQUEST POST /v1/convenios - payload: {}", request);
        try {
            ConvenioResponse response = convenioService.criar(request);
            log.info("Convênio criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar convênio — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar convênio — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar convênios", description = "Retorna uma lista paginada de convênios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de convênios retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ConvenioResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/convenios - pageable: {}", pageable);
        try {
            Page<ConvenioResponse> response = convenioService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar convênios — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter convênio por ID", description = "Retorna um convênio específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Convênio encontrado",
                    content = @Content(schema = @Schema(implementation = ConvenioResponse.class))),
            @ApiResponse(responseCode = "404", description = "Convênio não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ConvenioResponse> obterPorId(
            @Parameter(description = "ID do convênio", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/convenios/{}", id);
        try {
            ConvenioResponse response = convenioService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Convênio não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter convênio por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar convênio", description = "Atualiza um convênio existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Convênio atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = ConvenioResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Convênio não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ConvenioResponse> atualizar(
            @Parameter(description = "ID do convênio", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody ConvenioRequest request) {
        log.debug("REQUEST PUT /v1/convenios/{} - payload: {}", id, request);
        try {
            ConvenioResponse response = convenioService.atualizar(id, request);
            log.info("Convênio atualizado com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar convênio — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar convênio — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir convênio", description = "Exclui (desativa) um convênio do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Convênio excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Convênio não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do convênio", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/convenios/{}", id);
        try {
            convenioService.excluir(id);
            log.info("Convênio excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Convênio não encontrado para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir convênio — ID: {}", id, ex);
            throw ex;
        }
    }
}
