package com.upsaude.controller;

import com.upsaude.api.request.UsuariosPerfisRequest;
import com.upsaude.api.response.UsuariosPerfisResponse;
import com.upsaude.service.UsuariosPerfisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controlador REST para operações relacionadas a Usuários e Perfis.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/api/v1/usuarios-perfis")
@Tag(name = "Usuários e Perfis", description = "API para gerenciamento de Usuários e Perfis")
@RequiredArgsConstructor
public class UsuariosPerfisController {

    private final UsuariosPerfisService usuariosPerfisService;

    @PostMapping
    @Operation(summary = "Criar novo usuário e perfil", description = "Cria um novo usuário e perfil no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário e perfil criado com sucesso",
                    content = @Content(schema = @Schema(implementation = UsuariosPerfisResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<UsuariosPerfisResponse> criar(@Valid @RequestBody UsuariosPerfisRequest request) {
        UsuariosPerfisResponse response = usuariosPerfisService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar usuários e perfis", description = "Retorna uma lista paginada de usuários e perfis")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários e perfis retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<UsuariosPerfisResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<UsuariosPerfisResponse> response = usuariosPerfisService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter usuário e perfil por ID", description = "Retorna um usuário e perfil específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário e perfil encontrado",
                    content = @Content(schema = @Schema(implementation = UsuariosPerfisResponse.class))),
            @ApiResponse(responseCode = "404", description = "Usuário e perfil não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<UsuariosPerfisResponse> obterPorId(
            @Parameter(description = "ID do usuário e perfil", required = true)
            @PathVariable UUID id) {
        UsuariosPerfisResponse response = usuariosPerfisService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário e perfil", description = "Atualiza um usuário e perfil existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário e perfil atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = UsuariosPerfisResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Usuário e perfil não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<UsuariosPerfisResponse> atualizar(
            @Parameter(description = "ID do usuário e perfil", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody UsuariosPerfisRequest request) {
        UsuariosPerfisResponse response = usuariosPerfisService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir usuário e perfil", description = "Exclui (desativa) um usuário e perfil do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário e perfil excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário e perfil não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do usuário e perfil", required = true)
            @PathVariable UUID id) {
        usuariosPerfisService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

