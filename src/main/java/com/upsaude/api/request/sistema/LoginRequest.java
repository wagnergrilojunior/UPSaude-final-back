package com.upsaude.api.request.sistema;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de login")
public class LoginRequest {

    @NotBlank(message = "Email ou usuário é obrigatório")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    private String password;
}
