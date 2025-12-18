package com.upsaude.controller.estabelecimento;

import com.upsaude.api.request.estabelecimento.ProfissionalEstabelecimentoRequest;
import com.upsaude.api.response.estabelecimento.ProfissionalEstabelecimentoResponse;
import com.upsaude.enums.TipoVinculoProfissionalEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.estabelecimento.ProfissionalEstabelecimentoService;
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
@RequestMapping("/v1/profissionais-estabelecimentos")
@Tag(name = "Vínculos Profissional-Estabelecimento", description = "API para gerenciamento de vínculos entre profissionais e estabelecimentos")
@RequiredArgsConstructor
@Slf4j
public class ProfissionalEstabelecimentoController {

    private final ProfissionalEstabelecimentoService profissionalEstabelecimentoService;

    @PostMapping
    @Operation(summary = "Criar novo vínculo", description = "Cria um novo vínculo entre um profissional e um estabelecimento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vínculo criado com sucesso",
                    content = @Content(schema = @Schema(implementation = ProfissionalEstabelecimentoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ProfissionalEstabelecimentoResponse> criar(@Valid @RequestBody ProfissionalEstabelecimentoRequest request) {
        log.debug("REQUEST POST /v1/profissionais-estabelecimentos - payload: {}", request);
        try {
            ProfissionalEstabelecimentoResponse response = profissionalEstabelecimentoService.criar(request);
            log.info("Vínculo profissional-estabelecimento criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar vínculo profissional-estabelecimento — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar vínculo profissional-estabelecimento — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar vínculos", description = "Retorna uma lista paginada de vínculos entre profissionais e estabelecimentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de vínculos retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ProfissionalEstabelecimentoResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/profissionais-estabelecimentos - pageable: {}", pageable);
        try {
            Page<ProfissionalEstabelecimentoResponse> response = profissionalEstabelecimentoService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar vínculos profissional-estabelecimento — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/profissional/{profissionalId}")
    @Operation(summary = "Listar vínculos por profissional", description = "Retorna uma lista paginada de vínculos de um profissional")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de vínculos retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID do profissional inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ProfissionalEstabelecimentoResponse>> listarPorProfissional(
            @Parameter(description = "ID do profissional", required = true)
            @PathVariable UUID profissionalId,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/profissionais-estabelecimentos/profissional/{} - pageable: {}", profissionalId, pageable);
        try {
            Page<ProfissionalEstabelecimentoResponse> response = profissionalEstabelecimentoService.listarPorProfissional(profissionalId, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar vínculos por profissional — profissionalId: {}, mensagem: {}", profissionalId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar vínculos por profissional — profissionalId: {}, pageable: {}", profissionalId, pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/estabelecimento/{estabelecimentoId}")
    @Operation(summary = "Listar vínculos por estabelecimento", description = "Retorna uma lista paginada de vínculos de um estabelecimento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de vínculos retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID do estabelecimento inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ProfissionalEstabelecimentoResponse>> listarPorEstabelecimento(
            @Parameter(description = "ID do estabelecimento", required = true)
            @PathVariable UUID estabelecimentoId,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/profissionais-estabelecimentos/estabelecimento/{} - pageable: {}", estabelecimentoId, pageable);
        try {
            Page<ProfissionalEstabelecimentoResponse> response = profissionalEstabelecimentoService.listarPorEstabelecimento(estabelecimentoId, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar vínculos por estabelecimento — estabelecimentoId: {}, mensagem: {}", estabelecimentoId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar vínculos por estabelecimento — estabelecimentoId: {}, pageable: {}", estabelecimentoId, pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/estabelecimento/{estabelecimentoId}/tipo-vinculo/{tipoVinculo}")
    @Operation(summary = "Listar vínculos por tipo e estabelecimento", description = "Retorna uma lista paginada de vínculos filtrados por tipo e estabelecimento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de vínculos retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<ProfissionalEstabelecimentoResponse>> listarPorTipoVinculo(
            @Parameter(description = "ID do estabelecimento", required = true)
            @PathVariable UUID estabelecimentoId,
            @Parameter(description = "Tipo de vínculo (EFETIVO, CONTRATO, TEMPORARIO, etc.)", required = true)
            @PathVariable TipoVinculoProfissionalEnum tipoVinculo,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/profissionais-estabelecimentos/estabelecimento/{}/tipo-vinculo/{} - pageable: {}", estabelecimentoId, tipoVinculo, pageable);
        try {
            Page<ProfissionalEstabelecimentoResponse> response = profissionalEstabelecimentoService.listarPorTipoVinculo(tipoVinculo, estabelecimentoId, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar vínculos por tipo e estabelecimento — estabelecimentoId: {}, tipoVinculo: {}, mensagem: {}", estabelecimentoId, tipoVinculo, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar vínculos por tipo e estabelecimento — estabelecimentoId: {}, tipoVinculo: {}, pageable: {}", estabelecimentoId, tipoVinculo, pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter vínculo por ID", description = "Retorna um vínculo específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vínculo encontrado",
                    content = @Content(schema = @Schema(implementation = ProfissionalEstabelecimentoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Vínculo não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ProfissionalEstabelecimentoResponse> obterPorId(
            @Parameter(description = "ID do vínculo", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/profissionais-estabelecimentos/{}", id);
        try {
            ProfissionalEstabelecimentoResponse response = profissionalEstabelecimentoService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Vínculo profissional-estabelecimento não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter vínculo profissional-estabelecimento por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar vínculo", description = "Atualiza um vínculo existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vínculo atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = ProfissionalEstabelecimentoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Vínculo não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ProfissionalEstabelecimentoResponse> atualizar(
            @Parameter(description = "ID do vínculo", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody ProfissionalEstabelecimentoRequest request) {
        log.debug("REQUEST PUT /v1/profissionais-estabelecimentos/{} - payload: {}", id, request);
        try {
            ProfissionalEstabelecimentoResponse response = profissionalEstabelecimentoService.atualizar(id, request);
            log.info("Vínculo profissional-estabelecimento atualizado com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar vínculo profissional-estabelecimento — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar vínculo profissional-estabelecimento — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir vínculo", description = "Exclui (desativa) um vínculo do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vínculo excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Vínculo não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do vínculo", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/profissionais-estabelecimentos/{}", id);
        try {
            profissionalEstabelecimentoService.excluir(id);
            log.info("Vínculo profissional-estabelecimento excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Vínculo profissional-estabelecimento não encontrado para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir vínculo profissional-estabelecimento — ID: {}", id, ex);
            throw ex;
        }
    }
}
