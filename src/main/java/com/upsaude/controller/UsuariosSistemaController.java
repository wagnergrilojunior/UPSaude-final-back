package com.upsaude.controller;

import com.upsaude.api.request.UsuariosSistemaRequest;
import com.upsaude.api.response.UsuariosSistemaResponse;
import com.upsaude.service.UsuariosSistemaService;
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
 * Controlador REST para operações relacionadas a Usuários do Sistema.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/api/v1/usuarios-sistema")
@Tag(name = "Usuários do Sistema", description = "API para gerenciamento de Usuários do Sistema")
@RequiredArgsConstructor
public class UsuariosSistemaController {

    private final UsuariosSistemaService usuariosSistemaService;

    @PostMapping
    @Operation(summary = "Criar novo usuário do sistema", description = "Cria um novo usuário do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário do sistema criado com sucesso",
                    content = @Content(schema = @Schema(implementation = UsuariosSistemaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<UsuariosSistemaResponse> criar(@Valid @RequestBody UsuariosSistemaRequest request) {
        UsuariosSistemaResponse response = usuariosSistemaService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar usuários do sistema", description = "Retorna uma lista paginada de usuários do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários do sistema retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<UsuariosSistemaResponse>> listar(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            Pageable pageable) {
        Page<UsuariosSistemaResponse> response = usuariosSistemaService.listar(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter usuário do sistema por ID", description = "Retorna um usuário do sistema específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário do sistema encontrado",
                    content = @Content(schema = @Schema(implementation = UsuariosSistemaResponse.class))),
            @ApiResponse(responseCode = "404", description = "Usuário do sistema não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<UsuariosSistemaResponse> obterPorId(
            @Parameter(description = "ID do usuário do sistema", required = true)
            @PathVariable UUID id) {
        UsuariosSistemaResponse response = usuariosSistemaService.obterPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário do sistema", description = "Atualiza um usuário do sistema existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário do sistema atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = UsuariosSistemaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Usuário do sistema não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<UsuariosSistemaResponse> atualizar(
            @Parameter(description = "ID do usuário do sistema", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody UsuariosSistemaRequest request) {
        UsuariosSistemaResponse response = usuariosSistemaService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir usuário do sistema", description = "Exclui (desativa) um usuário do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário do sistema excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário do sistema não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do usuário do sistema", required = true)
            @PathVariable UUID id) {
        usuariosSistemaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

