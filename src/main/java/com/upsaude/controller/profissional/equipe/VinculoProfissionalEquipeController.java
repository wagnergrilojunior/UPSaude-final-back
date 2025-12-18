package com.upsaude.controller.profissional.equipe;

import com.upsaude.api.request.profissional.equipe.VinculoProfissionalEquipeRequest;
import com.upsaude.api.response.profissional.equipe.VinculoProfissionalEquipeResponse;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoVinculoProfissionalEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.profissional.equipe.VinculoProfissionalEquipeService;
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
@RequestMapping("/v1/vinculos-profissional-equipe")
@Tag(name = "Vínculos Profissional-Equipe", description = "API para gerenciamento de vínculos entre profissionais de saúde e equipes de saúde")
@RequiredArgsConstructor
@Slf4j
public class VinculoProfissionalEquipeController {

    private final VinculoProfissionalEquipeService vinculoService;

    @PostMapping
    @Operation(summary = "Criar novo vínculo profissional-equipe", description = "Cria um novo vínculo entre um profissional de saúde e uma equipe de saúde")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vínculo criado com sucesso",
                    content = @Content(schema = @Schema(implementation = VinculoProfissionalEquipeResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<VinculoProfissionalEquipeResponse> criar(@Valid @RequestBody VinculoProfissionalEquipeRequest request) {
        log.debug("REQUEST POST /v1/vinculos-profissional-equipe - payload: {}", request);
        try {
            VinculoProfissionalEquipeResponse response = vinculoService.criar(request);
            log.info("Vínculo profissional-equipe criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar vínculo profissional-equipe — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar vínculo profissional-equipe — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar todos os vínculos profissional-equipe", description = "Retorna uma lista paginada de todos os vínculos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de vínculos retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<VinculoProfissionalEquipeResponse>> listar(Pageable pageable) {
        log.debug("REQUEST GET /v1/vinculos-profissional-equipe - pageable: {}", pageable);
        try {
            Page<VinculoProfissionalEquipeResponse> response = vinculoService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar vínculos profissional-equipe — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter vínculo por ID", description = "Retorna um vínculo específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vínculo encontrado",
                    content = @Content(schema = @Schema(implementation = VinculoProfissionalEquipeResponse.class))),
            @ApiResponse(responseCode = "404", description = "Vínculo não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<VinculoProfissionalEquipeResponse> obterPorId(@PathVariable UUID id) {
        log.debug("REQUEST GET /v1/vinculos-profissional-equipe/{}", id);
        try {
            VinculoProfissionalEquipeResponse response = vinculoService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Vínculo profissional-equipe não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter vínculo profissional-equipe por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @GetMapping("/profissional/{profissionalId}")
    @Operation(summary = "Listar vínculos por profissional", description = "Retorna uma lista paginada de vínculos de um profissional específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de vínculos retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID do profissional inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<VinculoProfissionalEquipeResponse>> listarPorProfissional(
            @Parameter(description = "ID do profissional", required = true) @PathVariable UUID profissionalId,
            Pageable pageable) {
        log.debug("REQUEST GET /v1/vinculos-profissional-equipe/profissional/{} - pageable: {}", profissionalId, pageable);
        try {
            Page<VinculoProfissionalEquipeResponse> response = vinculoService.listarPorProfissional(profissionalId, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar vínculos por profissional — profissionalId: {}, mensagem: {}", profissionalId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar vínculos por profissional — profissionalId: {}, pageable: {}", profissionalId, pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/equipe/{equipeId}")
    @Operation(summary = "Listar vínculos por equipe", description = "Retorna uma lista paginada de vínculos de uma equipe específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de vínculos retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID da equipe inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<VinculoProfissionalEquipeResponse>> listarPorEquipe(
            @Parameter(description = "ID da equipe", required = true) @PathVariable UUID equipeId,
            Pageable pageable) {
        log.debug("REQUEST GET /v1/vinculos-profissional-equipe/equipe/{} - pageable: {}", equipeId, pageable);
        try {
            Page<VinculoProfissionalEquipeResponse> response = vinculoService.listarPorEquipe(equipeId, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar vínculos por equipe — equipeId: {}, mensagem: {}", equipeId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar vínculos por equipe — equipeId: {}, pageable: {}", equipeId, pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/tipo-vinculo/{tipoVinculo}/equipe/{equipeId}")
    @Operation(summary = "Listar vínculos por tipo de vínculo e equipe", description = "Retorna uma lista paginada de vínculos filtrados por tipo e equipe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de vínculos retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Tipo de vínculo ou ID da equipe inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<VinculoProfissionalEquipeResponse>> listarPorTipoVinculo(
            @Parameter(description = "Tipo de vínculo (EFETIVO, CONTRATO, TEMPORARIO, etc.)", required = true) @PathVariable TipoVinculoProfissionalEnum tipoVinculo,
            @Parameter(description = "ID da equipe", required = true) @PathVariable UUID equipeId,
            Pageable pageable) {
        log.debug("REQUEST GET /v1/vinculos-profissional-equipe/tipo-vinculo/{}/equipe/{} - pageable: {}", tipoVinculo, equipeId, pageable);
        try {
            Page<VinculoProfissionalEquipeResponse> response = vinculoService.listarPorTipoVinculo(tipoVinculo, equipeId, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar vínculos por tipo de vínculo e equipe — tipoVinculo: {}, equipeId: {}, mensagem: {}", tipoVinculo, equipeId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar vínculos por tipo de vínculo e equipe — tipoVinculo: {}, equipeId: {}, pageable: {}", tipoVinculo, equipeId, pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/status/{status}/equipe/{equipeId}")
    @Operation(summary = "Listar vínculos por status e equipe", description = "Retorna uma lista paginada de vínculos filtrados por status e equipe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de vínculos retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Status ou ID da equipe inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<VinculoProfissionalEquipeResponse>> listarPorStatus(
            @Parameter(description = "Status do vínculo (ATIVO, SUSPENSO, INATIVO)", required = true) @PathVariable StatusAtivoEnum status,
            @Parameter(description = "ID da equipe", required = true) @PathVariable UUID equipeId,
            Pageable pageable) {
        log.debug("REQUEST GET /v1/vinculos-profissional-equipe/status/{}/equipe/{} - pageable: {}", status, equipeId, pageable);
        try {
            Page<VinculoProfissionalEquipeResponse> response = vinculoService.listarPorStatus(status, equipeId, pageable);
            return ResponseEntity.ok(response);
        } catch (BadRequestException ex) {
            log.warn("Falha ao listar vínculos por status e equipe — status: {}, equipeId: {}, mensagem: {}", status, equipeId, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar vínculos por status e equipe — status: {}, equipeId: {}, pageable: {}", status, equipeId, pageable, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar vínculo profissional-equipe", description = "Atualiza um vínculo existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vínculo atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = VinculoProfissionalEquipeResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Vínculo não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<VinculoProfissionalEquipeResponse> atualizar(
            @PathVariable UUID id,
            @Valid @RequestBody VinculoProfissionalEquipeRequest request) {
        log.debug("REQUEST PUT /v1/vinculos-profissional-equipe/{} - payload: {}", id, request);
        try {
            VinculoProfissionalEquipeResponse response = vinculoService.atualizar(id, request);
            log.info("Vínculo profissional-equipe atualizado com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar vínculo profissional-equipe — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar vínculo profissional-equipe — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir vínculo profissional-equipe", description = "Exclui (desativa) um vínculo existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vínculo excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Vínculo não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/vinculos-profissional-equipe/{}", id);
        try {
            vinculoService.excluir(id);
            log.info("Vínculo profissional-equipe excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Vínculo profissional-equipe não encontrado para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir vínculo profissional-equipe — ID: {}", id, ex);
            throw ex;
        }
    }
}
