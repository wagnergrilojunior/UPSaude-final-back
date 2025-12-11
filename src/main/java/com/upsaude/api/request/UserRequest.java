package com.upsaude.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @Email(message = "Email inválido")
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    private String email;

    @Size(max = 255, message = "Role deve ter no máximo 255 caracteres")
    private String role;

    @Size(max = 255, message = "Senha deve ter no máximo 255 caracteres")
    private String senha;
}
