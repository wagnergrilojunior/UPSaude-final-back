package com.upsaude.api.request;

import jakarta.validation.constraints.Email;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    
    @Email(message = "Email inválido")
    private String email;

    private String role;
    
    // Campo opcional para atualização de senha
    // Se não fornecido (null ou vazio), a senha não será alterada
    private String senha;
    
    // Outros campos são gerenciados pelo Supabase Auth
}

