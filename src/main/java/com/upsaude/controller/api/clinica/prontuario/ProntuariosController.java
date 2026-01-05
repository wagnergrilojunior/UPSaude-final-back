package com.upsaude.controller.api.clinica.prontuario;

import com.upsaude.api.request.clinica.prontuario.ProntuariosRequest;
import com.upsaude.api.response.clinica.prontuario.ProntuariosResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.clinica.prontuario.ProntuariosService;
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
@RequestMapping("/v1/prontuarios")
@Tag(name = "Prontuários", description = "API para gerenciamento de Prontuários")
@RequiredArgsConstructor
@Slf4j
public class ProntuariosController {

    private final ProntuariosService prontuariosService;

    @PostMapping
    @Operation(summary = "Criar novo prontuário", description = "Cria um novo prontuário no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Prontuário criado com sucesso",
                    content = @Content(schema = @Schema(implementation = ProntuariosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ProntuariosResponse> criar(@Valid @RequestBody ProntuariosRequest request) {
        log.debug("REQUEST POST /v1/prontuarios - payload: {}", request);
        try {
            ProntuariosResponse response = prontuariosService.criar(request);
            log.info("Prontuário criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar prontuário — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar prontuário — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar prontuários", description = "Retorna uma lista paginada de prontuários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de prontuários retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ProntuariosResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable,
            @RequestParam(required = false) UUID pacienteId,
            @RequestParam(required = false) UUID estabelecimentoId,
            @RequestParam(required = false) String tipoRegistro,
            @RequestParam(required = false) UUID criadoPor) {
        log.debug("REQUEST GET /v1/prontuarios - pageable: {}, pacienteId: {}, estabelecimentoId: {}, tipoRegistro: {}, criadoPor: {}",
            pageable, pacienteId, estabelecimentoId, tipoRegistro, criadoPor);
        try {
            Page<ProntuariosResponse> response = prontuariosService.listar(pageable, pacienteId, estabelecimentoId, tipoRegistro, criadoPor);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar prontuários — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter prontuário por ID", description = "Retorna um prontuário específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prontuário encontrado",
                    content = @Content(schema = @Schema(implementation = ProntuariosResponse.class))),
            @ApiResponse(responseCode = "404", description = "Prontuário não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ProntuariosResponse> obterPorId(
            @Parameter(description = "ID do prontuário", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/prontuarios/{}", id);
        try {
            ProntuariosResponse response = prontuariosService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Prontuário não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter prontuário por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar prontuário", description = "Atualiza um prontuário existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prontuário atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = ProntuariosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Prontuário não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ProntuariosResponse> atualizar(
            @Parameter(description = "ID do prontuário", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody ProntuariosRequest request) {
        log.debug("REQUEST PUT /v1/prontuarios/{} - payload: {}", id, request);
        try {
            ProntuariosResponse response = prontuariosService.atualizar(id, request);
            log.info("Prontuário atualizado com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar prontuário — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar prontuário — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir prontuário", description = "Remove permanentemente um prontuário do banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Prontuário excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Prontuário não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do prontuário", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/prontuarios/{}", id);
        try {
            prontuariosService.excluir(id);
            log.info("Prontuário excluído permanentemente com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Prontuário não encontrado para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir prontuário — ID: {}", id, ex);
            throw ex;
        }
    }

    @PatchMapping("/{id}/inativar")
    @Operation(summary = "Inativar prontuário", description = "Inativa um prontuário no sistema alterando seu status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Prontuário inativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Prontuário não encontrado"),
            @ApiResponse(responseCode = "400", description = "Prontuário já está inativo"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> inativar(
            @Parameter(description = "ID do prontuário", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST PATCH /v1/prontuarios/{}/inativar", id);
        try {
            prontuariosService.inativar(id);
            log.info("Prontuário inativado com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Prontuário não encontrado para inativação — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao inativar prontuário — ID: {}", id, ex);
            throw ex;
        }
    }
}
