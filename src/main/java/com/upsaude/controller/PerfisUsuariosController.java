package com.upsaude.controller;

import com.upsaude.api.request.PerfisUsuariosRequest;
import com.upsaude.api.response.PerfisUsuariosResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controlador REST para operações relacionadas a Perfis de Usuários.
 *
 * @author UPSaúde
 */
@RestController
@RequestMapping("/api/v1/perfis-usuarios")
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
        PerfisUsuariosResponse response = perfisUsuariosService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
        Page<PerfisUsuariosResponse> response = perfisUsuariosService.listar(pageable);
        return ResponseEntity.ok(response);
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
        PerfisUsuariosResponse response = perfisUsuariosService.obterPorId(id);
        return ResponseEntity.ok(response);
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
        PerfisUsuariosResponse response = perfisUsuariosService.atualizar(id, request);
        return ResponseEntity.ok(response);
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
        perfisUsuariosService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

