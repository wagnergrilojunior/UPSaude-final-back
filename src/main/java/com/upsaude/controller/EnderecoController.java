package com.upsaude.controller;

import com.upsaude.api.request.EnderecoRequest;
import com.upsaude.api.response.EnderecoResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.EnderecoService;
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
@RequestMapping("/v1/enderecos")
@Tag(name = "Endereços", description = "API para gerenciamento de Endereços")
@RequiredArgsConstructor
@Slf4j
public class EnderecoController {

    private final EnderecoService enderecoService;

    @PostMapping
    @Operation(summary = "Criar novo endereço", description = "Cria um novo endereço no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Endereço criado com sucesso",
                    content = @Content(schema = @Schema(implementation = EnderecoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EnderecoResponse> criar(@Valid @RequestBody EnderecoRequest request) {
        log.debug("REQUEST POST /v1/enderecos - payload: {}", request);
        try {
            EnderecoResponse response = enderecoService.criar(request);
            log.info("Endereço criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar endereço — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar endereço — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar endereços", description = "Retorna uma lista paginada de endereços")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de endereços retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<EnderecoResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/enderecos - pageable: {}", pageable);
        try {
            Page<EnderecoResponse> response = enderecoService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar endereços — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter endereço por ID", description = "Retorna um endereço específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço encontrado",
                    content = @Content(schema = @Schema(implementation = EnderecoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EnderecoResponse> obterPorId(
            @Parameter(description = "ID do endereço", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/enderecos/{}", id);
        try {
            EnderecoResponse response = enderecoService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Endereço não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter endereço por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar endereço", description = "Atualiza um endereço existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = EnderecoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EnderecoResponse> atualizar(
            @Parameter(description = "ID do endereço", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody EnderecoRequest request) {
        log.debug("REQUEST PUT /v1/enderecos/{} - payload: {}", id, request);
        try {
            EnderecoResponse response = enderecoService.atualizar(id, request);
            log.info("Endereço atualizado com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar endereço — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar endereço — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir endereço", description = "Exclui (desativa) um endereço do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Endereço excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do endereço", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/enderecos/{}", id);
        try {
            enderecoService.excluir(id);
            log.info("Endereço excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Endereço não encontrado para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir endereço — ID: {}", id, ex);
            throw ex;
        }
    }
}
