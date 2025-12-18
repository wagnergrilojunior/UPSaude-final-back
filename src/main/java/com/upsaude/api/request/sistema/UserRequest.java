package com.upsaude.api.request.sistema;

import com.upsaude.validation.annotation.EmailValido;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de user")
public class UserRequest {
    @EmailValido
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    private String email;

    @Size(max = 255, message = "Role deve ter no máximo 255 caracteres")
    private String role;

    @Size(max = 255, message = "Senha deve ter no máximo 255 caracteres")
    private String senha;
}
