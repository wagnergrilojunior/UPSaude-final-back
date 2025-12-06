package com.upsaude.controller;

import com.upsaude.api.request.ConselhosProfissionaisRequest;
import com.upsaude.api.response.ConselhosProfissionaisResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.ConselhosProfissionaisService;
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

/**
 * Controlador REST para operações relacionadas a Conselhos Profissionais.
 *
 * @author UPSaúde
 */
@Slf4j
@RestController
@RequestMapping("/v1/conselhos-profissionais")
@Tag(name = "Conselhos Profissionais", description = "API para gerenciamento de Conselhos Profissionais")
@RequiredArgsConstructor
public class ConselhosProfissionaisController {

    private final ConselhosProfissionaisService conselhosProfissionaisService;

    @PostMapping
    @Operation(summary = "Criar novo conselho profissional", description = "Cria um novo conselho profissional no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Conselho profissional criado com sucesso",
                    content = @Content(schema = @Schema(implementation = ConselhosProfissionaisResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ConselhosProfissionaisResponse> criar(@Valid @RequestBody ConselhosProfissionaisRequest request) {
        log.debug("REQUEST POST /v1/conselhos-profissionais - payload: {}", request);
        try {
            ConselhosProfissionaisResponse response = conselhosProfissionaisService.criar(request);
            log.info("Conselho profissional criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar conselho profissional — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar conselho profissional — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar conselhos profissionais", description = "Retorna uma lista paginada de conselhos profissionais")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de conselhos profissionais retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ConselhosProfissionaisResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/conselhos-profissionais - pageable: {}", pageable);
        try {
            Page<ConselhosProfissionaisResponse> response = conselhosProfissionaisService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar conselhos profissionais — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter conselho profissional por ID", description = "Retorna um conselho profissional específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conselho profissional encontrado",
                    content = @Content(schema = @Schema(implementation = ConselhosProfissionaisResponse.class))),
            @ApiResponse(responseCode = "404", description = "Conselho profissional não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ConselhosProfissionaisResponse> obterPorId(
            @Parameter(description = "ID do conselho profissional", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/conselhos-profissionais/{}", id);
        try {
            ConselhosProfissionaisResponse response = conselhosProfissionaisService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Conselho profissional não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter conselho profissional por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar conselho profissional", description = "Atualiza um conselho profissional existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conselho profissional atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = ConselhosProfissionaisResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Conselho profissional não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ConselhosProfissionaisResponse> atualizar(
            @Parameter(description = "ID do conselho profissional", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody ConselhosProfissionaisRequest request) {
        log.debug("REQUEST PUT /v1/conselhos-profissionais/{} - payload: {}", id, request);
        try {
            ConselhosProfissionaisResponse response = conselhosProfissionaisService.atualizar(id, request);
            log.info("Conselho profissional atualizado com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar conselho profissional — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar conselho profissional — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir conselho profissional", description = "Exclui (desativa) um conselho profissional do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Conselho profissional excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Conselho profissional não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do conselho profissional", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/conselhos-profissionais/{}", id);
        try {
            conselhosProfissionaisService.excluir(id);
            log.info("Conselho profissional excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Falha ao excluir conselho profissional — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir conselho profissional — ID: {}", id, ex);
            throw ex;
        }
    }
}

