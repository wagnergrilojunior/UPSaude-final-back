package com.upsaude.controller;

import com.upsaude.api.request.PerfisUsuariosRequest;
import com.upsaude.api.response.PerfisUsuariosResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.PerfisUsuariosService;
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

@Slf4j
@RestController
@RequestMapping("/v1/perfis-usuarios")
@Tag(name = "Perfis de Usuários", description = "API para gerenciamento de Perfis de Usuários")
@RequiredArgsConstructor
public class PerfisUsuariosController {

    private final PerfisUsuariosService perfisUsuariosService;

    @PostMapping
    @Operation(summary = "Criar novo perfil de usuário", description = "Cria um novo perfil de usuário no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Perfil de usuário criado com sucesso",
                    content = @Content(schema = @Schema(implementation = PerfisUsuariosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<PerfisUsuariosResponse> criar(@Valid @RequestBody PerfisUsuariosRequest request) {
        log.debug("REQUEST POST /v1/perfis-usuarios - payload: {}", request);
        try {
            PerfisUsuariosResponse response = perfisUsuariosService.criar(request);
            log.info("Perfil de usuário criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar perfil de usuário — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar perfil de usuário — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar perfis de usuários", description = "Retorna uma lista paginada de perfis de usuários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de perfis de usuários retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<PerfisUsuariosResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/perfis-usuarios - pageable: {}", pageable);
        try {
            Page<PerfisUsuariosResponse> response = perfisUsuariosService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar perfis de usuários — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter perfil de usuário por ID", description = "Retorna um perfil de usuário específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil de usuário encontrado",
                    content = @Content(schema = @Schema(implementation = PerfisUsuariosResponse.class))),
            @ApiResponse(responseCode = "404", description = "Perfil de usuário não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<PerfisUsuariosResponse> obterPorId(
            @Parameter(description = "ID do perfil de usuário", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/perfis-usuarios/{}", id);
        try {
            PerfisUsuariosResponse response = perfisUsuariosService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Perfil de usuário não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter perfil de usuário por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar perfil de usuário", description = "Atualiza um perfil de usuário existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil de usuário atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = PerfisUsuariosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Perfil de usuário não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<PerfisUsuariosResponse> atualizar(
            @Parameter(description = "ID do perfil de usuário", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody PerfisUsuariosRequest request) {
        log.debug("REQUEST PUT /v1/perfis-usuarios/{} - payload: {}", id, request);
        try {
            PerfisUsuariosResponse response = perfisUsuariosService.atualizar(id, request);
            log.info("Perfil de usuário atualizado com sucesso. ID: {}", id);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao atualizar perfil de usuário — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar perfil de usuário — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir perfil de usuário", description = "Exclui (desativa) um perfil de usuário do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Perfil de usuário excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Perfil de usuário não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do perfil de usuário", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/perfis-usuarios/{}", id);
        try {
            perfisUsuariosService.excluir(id);
            log.info("Perfil de usuário excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Falha ao excluir perfil de usuário — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir perfil de usuário — ID: {}", id, ex);
            throw ex;
        }
    }
}
