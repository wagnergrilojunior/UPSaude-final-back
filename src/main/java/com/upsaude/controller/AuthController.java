package com.upsaude.controller;

import com.upsaude.api.request.LoginRequest;
import com.upsaude.api.response.LoginResponse;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Controlador REST para operações de autenticação.
 *
 * @author UPSaúde
 */
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
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        Map<String, Object> response = new HashMap<>();
        
        // Obtém o userId do token JWT
        UUID userId = null;
        Object details = authentication.getDetails();
        if (details instanceof SupabaseAuthResponse.User) {
            SupabaseAuthResponse.User user = (SupabaseAuthResponse.User) details;
            userId = user.getId();
        } else if (authentication.getPrincipal() instanceof String) {
            try {
                userId = UUID.fromString(authentication.getPrincipal().toString());
            } catch (IllegalArgumentException e) {
                // Ignora se não for um UUID válido
            }
        }
        
        if (userId != null) {
            boolean temAcesso = authService.verificarAcessoAoSistema(userId);
            response.put("temAcesso", temAcesso);
            response.put("userId", userId);
        } else {
            response.put("temAcesso", false);
            response.put("erro", "Não foi possível identificar o usuário");
        }
        
        return ResponseEntity.ok(response);
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        Map<String, Object> userInfo = new HashMap<>();
        
        // Obtém os detalhes do usuário que foram setados no filtro JWT
        Object details = authentication.getDetails();
        if (details instanceof SupabaseAuthResponse.User) {
            SupabaseAuthResponse.User user = (SupabaseAuthResponse.User) details;
            userInfo.put("id", user.getId());
            userInfo.put("email", user.getEmail());
            userInfo.put("role", user.getRole());
            userInfo.put("userMetadata", user.getUserMetadata());
            userInfo.put("appMetadata", user.getAppMetadata());
        } else {
            // Fallback para informações básicas do Authentication
            userInfo.put("principal", authentication.getPrincipal());
            userInfo.put("authorities", authentication.getAuthorities());
        }
        
        return ResponseEntity.ok(userInfo);
    }
}

