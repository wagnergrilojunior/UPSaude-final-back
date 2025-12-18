package com.upsaude.controller.sistema;
import com.upsaude.entity.sistema.User;

import com.upsaude.api.request.LoginRequest;
import com.upsaude.api.response.LoginResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.UnauthorizedException;
import com.upsaude.integration.supabase.SupabaseAuthResponse;
import com.upsaude.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/v1/auth")
@Tag(name = "Autenticação", description = "API para autenticação de usuários")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Realizar login", description = "Autentica um usuário usando email e senha através do Supabase Auth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso",
                    content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        log.debug("REQUEST POST /v1/auth/login - email: {}", request.getEmail() != null ? request.getEmail() : "null");
        try {
            LoginResponse response = authService.login(request);
            log.info("Login realizado com sucesso. Email: {}", request.getEmail());
            return ResponseEntity.ok(response);
        } catch (BadRequestException | UnauthorizedException ex) {
            log.warn("Falha ao realizar login — mensagem: {}, email: {}", ex.getMessage(), request.getEmail());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao realizar login — email: {}", request.getEmail(), ex);
            throw ex;
        }
    }

    @GetMapping("/verificar-acesso")
    @Operation(
            summary = "Verificar se usuário tem acesso ao sistema",
            description = "Verifica se um usuário autenticado tem acesso ao sistema (se existe UsuariosSistema criado). " +
                         "Este endpoint requer autenticação e é usado pelo frontend para verificar se precisa criar o registro.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verificação realizada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Token inválido ou não fornecido")
    })
    public ResponseEntity<Map<String, Object>> verificarAcesso() {
        log.debug("REQUEST GET /v1/auth/verificar-acesso");
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()) {
                log.warn("Tentativa de verificar acesso sem autenticação");
                return ResponseEntity.status(401).build();
            }

            Map<String, Object> response = new HashMap<>();

            UUID userId = null;
            Object details = authentication.getDetails();
            if (details instanceof SupabaseAuthResponse.User) {
                SupabaseAuthResponse.User user = (SupabaseAuthResponse.User) details;
                userId = user.getId();
            } else if (authentication.getPrincipal() instanceof String) {
                try {
                    userId = UUID.fromString(authentication.getPrincipal().toString());
                } catch (IllegalArgumentException e) {

                    log.debug("Principal não é um UUID válido: {}", authentication.getPrincipal());
                }
            }

            if (userId != null) {
                boolean temAcesso = authService.verificarAcessoAoSistema(userId);
                response.put("temAcesso", temAcesso);
                response.put("userId", userId);
                log.debug("Verificação de acesso concluída — userId: {}, temAcesso: {}", userId, temAcesso);
            } else {
                response.put("temAcesso", false);
                response.put("erro", "Não foi possível identificar o usuário");
                log.warn("Não foi possível identificar o usuário na verificação de acesso");
            }

            return ResponseEntity.ok(response);
        } catch (UnauthorizedException ex) {
            log.warn("Falha ao verificar acesso — mensagem: {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao verificar acesso", ex);
            throw ex;
        }
    }

    @GetMapping("/me")
    @Operation(
            summary = "Obter informações do usuário autenticado",
            description = "Retorna as informações do usuário autenticado baseado no token JWT",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Informações do usuário retornadas com sucesso"),
            @ApiResponse(responseCode = "401", description = "Token inválido ou não fornecido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Map<String, Object>> getCurrentUser() {
        log.debug("REQUEST GET /v1/auth/me");
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()) {
                log.warn("Tentativa de obter informações do usuário sem autenticação");
                return ResponseEntity.status(401).build();
            }

            Map<String, Object> userInfo = new HashMap<>();

            Object details = authentication.getDetails();
            if (details instanceof SupabaseAuthResponse.User) {
                SupabaseAuthResponse.User user = (SupabaseAuthResponse.User) details;
                userInfo.put("id", user.getId());
                userInfo.put("email", user.getEmail());
                userInfo.put("role", user.getRole());
                userInfo.put("userMetadata", user.getUserMetadata());
                userInfo.put("appMetadata", user.getAppMetadata());
                log.debug("Informações do usuário obtidas — userId: {}, email: {}", user.getId(), user.getEmail());
            } else {

                userInfo.put("principal", authentication.getPrincipal());
                userInfo.put("authorities", authentication.getAuthorities());
                log.debug("Informações do usuário obtidas via fallback — principal: {}", authentication.getPrincipal());
            }

            return ResponseEntity.ok(userInfo);
        } catch (UnauthorizedException ex) {
            log.warn("Falha ao obter informações do usuário — mensagem: {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao obter informações do usuário", ex);
            throw ex;
        }
    }
}
