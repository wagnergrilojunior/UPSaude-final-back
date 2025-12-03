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
    
    // Outros campos são gerenciados pelo Supabase Auth
}

