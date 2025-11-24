package com.upsaude.controller;

import com.upsaude.api.request.UserRequest;
import com.upsaude.api.response.UserResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controlador REST para operações relacionadas a Users.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "API para gerenciamento de Users")
@RequiredArgsConstructor
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
        UserResponse response = userService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
        Page<UserResponse> response = userService.listar(pageable);
        return ResponseEntity.ok(response);
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
        UserResponse response = userService.obterPorId(id);
        return ResponseEntity.ok(response);
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
        UserResponse response = userService.atualizar(id, request);
        return ResponseEntity.ok(response);
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
        userService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

