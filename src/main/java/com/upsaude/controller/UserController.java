package com.upsaude.controller;

import com.upsaude.api.request.UserRequest;
import com.upsaude.api.response.UserResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.UserService;
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
@RequestMapping("/v1/users")
@Tag(name = "Users", description = "API para gerenciamento de Users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Criar novo user", description = "Cria um novo user no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User criado com sucesso",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<UserResponse> criar(@Valid @RequestBody UserRequest request) {
        log.debug("REQUEST POST /v1/users - payload: {}", request);
        try {
            UserResponse response = userService.criar(request);
            log.info("User criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar user — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar user — payload: {}", request, ex);
            throw ex;
        }
    }

    @GetMapping
    @Operation(summary = "Listar users", description = "Retorna uma lista paginada de users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de users retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<UserResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        log.debug("REQUEST GET /v1/users - pageable: {}", pageable);
        try {
            Page<UserResponse> response = userService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar users — pageable: {}", pageable, ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter user por ID", description = "Retorna um user específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User encontrado",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "404", description = "User não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<UserResponse> obterPorId(
            @Parameter(description = "ID do user", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/users/{}", id);
        try {
            UserResponse response = userService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("User não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter user por ID — ID: {}", id, ex);
            throw ex;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar user", description = "Atualiza um user existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "User não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<UserResponse> atualizar(
            @Parameter(description = "ID do user", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody UserRequest request) {
        log.debug("REQUEST PUT /v1/users/{} - payload: {}", id, request);
        try {
            UserResponse response = userService.atualizar(id, request);
            log.info("User atualizado com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar user — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar user — ID: {}, payload: {}", id, request, ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir user", description = "Exclui (desativa) um user do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "User não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do user", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/users/{}", id);
        try {
            userService.excluir(id);
            log.info("User excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("User não encontrado para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir user — ID: {}", id, ex);
            throw ex;
        }
    }
}
