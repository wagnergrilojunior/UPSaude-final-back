package com.upsaude.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    
    /**
     * Email ou username (campo 'user') do usuário.
     * O sistema tentará primeiro fazer login com email, depois com username.
     */
    @NotBlank(message = "Email ou usuário é obrigatório")
    private String email; // Mantém nome 'email' para compatibilidade, mas aceita email OU user
    
    @NotBlank(message = "Senha é obrigatória")
    private String password;
}

