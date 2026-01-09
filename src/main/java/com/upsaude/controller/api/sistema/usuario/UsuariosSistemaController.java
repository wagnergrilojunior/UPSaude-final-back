package com.upsaude.controller.api.sistema.usuario;

import com.upsaude.api.request.sistema.usuario.TrocaSenhaRequest;
import com.upsaude.api.request.sistema.usuario.UsuariosSistemaRequest;
import com.upsaude.api.response.sistema.usuario.UsuariosSistemaResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.sistema.usuario.UsuariosSistemaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/usuarios-sistema")
@Tag(name = "Usuários do Sistema", description = "API para gerenciamento de Usuários do Sistema")
@RequiredArgsConstructor
@Slf4j
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
        log.debug("REQUEST POST /v1/usuarios-sistema - payload: {}", request);
        try {
            UsuariosSistemaResponse response = usuariosSistemaService.criar(request);
            log.info("Usuário do sistema criado com sucesso. ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestException | ConflictException ex) {
            log.warn("Falha ao criar usuário do sistema — mensagem: {}, payload: {}", ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar usuário do sistema — Path: /v1/usuarios-sistema, Method: POST, payload: {}, Exception: {}",
                request, ex.getClass().getName(), ex);
            throw ex;
        }
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
        log.debug("REQUEST GET /v1/usuarios-sistema - pageable: {}", pageable);
        try {
            Page<UsuariosSistemaResponse> response = usuariosSistemaService.listar(pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar usuários do sistema — Path: /v1/usuarios-sistema, Method: GET, pageable: {}, Exception: {}",
                pageable, ex.getClass().getName(), ex);
            throw ex;
        }
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
        log.debug("REQUEST GET /v1/usuarios-sistema/{}", id);
        try {
            UsuariosSistemaResponse response = usuariosSistemaService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Usuário do sistema não encontrado — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter usuário do sistema por ID — Path: /v1/usuarios-sistema/{}, Method: GET, ID: {}, Exception: {}",
                id, id, ex.getClass().getName(), ex);
            throw ex;
        }
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
        log.debug("REQUEST PUT /v1/usuarios-sistema/{} - payload: {}", id, request);
        try {
            UsuariosSistemaResponse response = usuariosSistemaService.atualizar(id, request);
            log.info("Usuário do sistema atualizado com sucesso. ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException | ConflictException ex) {
            log.warn("Falha ao atualizar usuário do sistema — ID: {}, mensagem: {}, payload: {}", id, ex.getMessage(), request);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao atualizar usuário do sistema — Path: /v1/usuarios-sistema/{}, Method: PUT, ID: {}, payload: {}, Exception: {}",
                id, id, request, ex.getClass().getName(), ex);
            throw ex;
        }
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
        log.debug("REQUEST DELETE /v1/usuarios-sistema/{}", id);
        try {
            usuariosSistemaService.excluir(id);
            log.info("Usuário do sistema excluído com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Usuário do sistema não encontrado para exclusão — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao excluir usuário do sistema — Path: /v1/usuarios-sistema/{}, Method: DELETE, ID: {}, Exception: {}",
                id, id, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @PostMapping("/{id}/foto")
    @Operation(
            summary = "Upload de foto do usuário",
            description = "Faz upload da foto do usuário para o Supabase Storage",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Foto enviada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Arquivo inválido"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Map<String, String>> uploadFoto(
            @Parameter(description = "ID do usuário do sistema", required = true)
            @PathVariable UUID id,
            @Parameter(description = "Arquivo de imagem (JPEG, PNG, WEBP ou GIF, máximo 5MB)", required = true)
            @RequestParam("file") MultipartFile file) {
        log.debug("REQUEST POST /v1/usuarios-sistema/{}/foto - filename: {}, size: {} bytes", id, file.getOriginalFilename(), file.getSize());
        try {
            String fotoUrl = usuariosSistemaService.uploadFoto(id, file);
            Map<String, String> response = new HashMap<>();
            response.put("fotoUrl", fotoUrl);
            response.put("message", "Foto enviada com sucesso");
            log.info("Foto do usuário enviada com sucesso. ID: {}, fotoUrl: {}", id, fotoUrl);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao fazer upload de foto do usuário — ID: {}, filename: {}, mensagem: {}", id, file.getOriginalFilename(), ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao fazer upload de foto do usuário — Path: /v1/usuarios-sistema/{}/foto, Method: POST, ID: {}, filename: {}, Exception: {}",
                id, id, file.getOriginalFilename(), ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @GetMapping("/{id}/foto")
    @Operation(
            summary = "Obter URL da foto do usuário",
            description = "Retorna a URL pública da foto do usuário no Supabase Storage",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "URL da foto retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado ou foto não disponível"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Map<String, String>> obterFoto(
            @Parameter(description = "ID do usuário do sistema", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/usuarios-sistema/{}/foto", id);
        try {
            String fotoUrl = usuariosSistemaService.obterFotoUrl(id);
            Map<String, String> response = new HashMap<>();
            response.put("fotoUrl", fotoUrl);
            return ResponseEntity.ok(response);
        } catch (NotFoundException ex) {
            log.warn("Foto do usuário não encontrada — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter foto do usuário — Path: /v1/usuarios-sistema/{}/foto, Method: GET, ID: {}, Exception: {}",
                id, id, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @DeleteMapping("/{id}/foto")
    @Operation(
            summary = "Deletar foto do usuário",
            description = "Remove a foto do usuário do Supabase Storage",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Foto deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> deletarFoto(
            @Parameter(description = "ID do usuário do sistema", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST DELETE /v1/usuarios-sistema/{}/foto", id);
        try {
            usuariosSistemaService.deletarFoto(id);
            log.info("Foto do usuário deletada com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("Usuário não encontrado para deletar foto — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao deletar foto do usuário — Path: /v1/usuarios-sistema/{}/foto, Method: DELETE, ID: {}, Exception: {}",
                id, id, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @PutMapping("/{id}/trocar-senha")
    @Operation(
            summary = "Trocar senha do usuário",
            description = "Atualiza a senha do usuário no Supabase Auth",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Senha alterada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Void> trocarSenha(
            @Parameter(description = "ID do usuário do sistema", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody TrocaSenhaRequest request) {
        log.debug("REQUEST PUT /v1/usuarios-sistema/{}/trocar-senha", id);
        try {
            usuariosSistemaService.trocarSenha(id, request.getNovaSenha());
            log.info("Senha do usuário alterada com sucesso. ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (BadRequestException | NotFoundException ex) {
            log.warn("Falha ao trocar senha do usuário — ID: {}, mensagem: {}", id, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao trocar senha do usuário — Path: /v1/usuarios-sistema/{}/trocar-senha, Method: PUT, ID: {}, Exception: {}",
                id, id, ex.getClass().getName(), ex);
            throw ex;
        }
    }

    @PostMapping("/sincronizar-users")
    @Operation(
            summary = "Sincronizar users",
            description = "Remove users órfãos da tabela users que não possuem correspondência em usuarios_sistema. A fonte de verdade é a tabela usuarios_sistema.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sincronização concluída com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Map<String, Object>> sincronizarUsers() {
        log.debug("REQUEST POST /v1/usuarios-sistema/sincronizar-users");
        try {
            int totalDeletados = usuariosSistemaService.sincronizarUsers();
            Map<String, Object> response = new HashMap<>();
            response.put("mensagem", "Sincronização concluída com sucesso");
            response.put("totalDeletados", totalDeletados);
            log.info("Sincronização de users concluída. Total deletado: {}", totalDeletados);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro inesperado ao sincronizar users — Path: /v1/usuarios-sistema/sincronizar-users, Method: POST, Exception: {}",
                ex.getClass().getName(), ex);
            throw ex;
        }
    }
}
