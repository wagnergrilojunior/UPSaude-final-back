package com.upsaude.controller.educacao;

import com.upsaude.api.request.educacao.EducacaoSaudeRequest;
import com.upsaude.api.response.educacao.EducacaoSaudeResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.educacao.EducacaoSaudeService;
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
@RequestMapping("/v1/educacao-saude")
@Tag(name = "Educação em Saúde", description = "API para gerenciamento de ações de educação em saúde")
@RequiredArgsConstructor
@Slf4j
public class EducacaoSaudeController {

    private final EducacaoSaudeService educacaoSaudeService;

    @PostMapping
    @Operation(summary = "Criar nova ação de educação em saúde", description = "Cria uma nova ação de educação em saúde no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Educação em saúde criada com sucesso",
                    content = @Content(schema = @Schema(implementation = EducacaoSaudeResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EducacaoSaudeResponse> criar(@Valid @RequestBody EducacaoSaudeRequest request) {
        log.debug("REQUEST POST /v1/educacao-saude - payload: {}", request);
        try {
            EducacaoSaudeResponse response = educacaoSaudeService.criar(request);
            log.info("Educação em saúde criada com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar educação em saúde — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar educação em saúde — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar ações de educação em saúde", description = "Retorna uma lista paginada de ações de educação em saúde")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ações de educação em saúde retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<EducacaoSaudeResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/educacao-saude - pageable: {}", pageable);
        try {
            Page<EducacaoSaudeResponse> response = educacaoSaudeService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar ações de educação em saúde — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter ação de educação em saúde por ID", description = "Retorna uma ação de educação em saúde específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Educação em saúde encontrada",
                    content = @Content(schema = @Schema(implementation = EducacaoSaudeResponse.class))),
            @ApiResponse(responseCode = "404", description = "Educação em saúde não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EducacaoSaudeResponse> obterPorId(
            @Parameter(description = "ID da ação de educação em saúde", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/educacao-saude/{}", id);
        try {
            EducacaoSaudeResponse response = educacaoSaudeService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Educação em saúde não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter educação em saúde por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @GetMapping("/estabelecimento/{estabelecimentoId}")
    @Operation(summary = "Listar ações de educação em saúde por estabelecimento", description = "Retorna uma lista paginada de ações de educação em saúde por estabelecimento")
    public ResponseEntity<Page<EducacaoSaudeResponse>> listarPorEstabelecimento(
            @PathVariable UUID estabelecimentoId,
            Pageable pageable) {
        log.debug("REQUEST GET /v1/educacao-saude/estabelecimento/{} - pageable: {}", estabelecimentoId, pageable);
        try {
            Page<EducacaoSaudeResponse> response = educacaoSaudeService.listarPorEstabelecimento(estabelecimentoId, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar ações de educação em saúde por estabelecimento — estabelecimentoId: {}, mensagem: {}", estabelecimentoId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar ações de educação em saúde por estabelecimento — estabelecimentoId: {}, pageable: {}", estabelecimentoId, pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/profissional/{profissionalId}")
    @Operation(summary = "Listar ações de educação em saúde por profissional", description = "Retorna uma lista paginada de ações de educação em saúde por profissional responsável")
    public ResponseEntity<Page<EducacaoSaudeResponse>> listarPorProfissionalResponsavel(
            @PathVariable UUID profissionalId,
            Pageable pageable) {
        log.debug("REQUEST GET /v1/educacao-saude/profissional/{} - pageable: {}", profissionalId, pageable);
        try {
            Page<EducacaoSaudeResponse> response = educacaoSaudeService.listarPorProfissionalResponsavel(profissionalId, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar ações de educação em saúde por profissional — profissionalId: {}, mensagem: {}", profissionalId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar ações de educação em saúde por profissional — profissionalId: {}, pageable: {}", profissionalId, pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/realizadas/{estabelecimentoId}")
    @Operation(summary = "Listar ações de educação em saúde realizadas", description = "Retorna uma lista paginada de ações de educação em saúde já realizadas")
    public ResponseEntity<Page<EducacaoSaudeResponse>> listarRealizadas(
            @PathVariable UUID estabelecimentoId,
            Pageable pageable) {
        log.debug("REQUEST GET /v1/educacao-saude/realizadas/{} - pageable: {}", estabelecimentoId, pageable);
        try {
            Page<EducacaoSaudeResponse> response = educacaoSaudeService.listarRealizadas(estabelecimentoId, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar ações de educação em saúde realizadas — estabelecimentoId: {}, mensagem: {}", estabelecimentoId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar ações de educação em saúde realizadas — estabelecimentoId: {}, pageable: {}", estabelecimentoId, pageable, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar ação de educação em saúde", description = "Atualiza uma ação de educação em saúde existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Educação em saúde atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = EducacaoSaudeResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Educação em saúde não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<EducacaoSaudeResponse> atualizar(
            @Parameter(description = "ID da ação de educação em saúde", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody EducacaoSaudeRequest request) {
        log.debug("REQUEST PUT /v1/educacao-saude/{} - payload: {}", id, request);
        try {
            EducacaoSaudeResponse response = educacaoSaudeService.atualizar(id, request);
            log.info("Educação em saúde atualizada com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar educação em saúde — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar educação em saúde — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir ação de educação em saúde", description = "Exclui (desativa) uma ação de educação em saúde do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Educação em saúde excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Educação em saúde não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da ação de educação em saúde", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/educacao-saude/{}", id);
        try {
            educacaoSaudeService.excluir(id);
            log.info("Educação em saúde excluída com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Educação em saúde não encontrada para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir educação em saúde — ID: {}", id, ex);
            throw ex;
        }
    }
}
