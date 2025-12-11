package com.upsaude.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "Email ou usuário é obrigatório")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    private String password;
}
