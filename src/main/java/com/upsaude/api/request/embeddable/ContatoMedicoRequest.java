package com.upsaude.api.request.embeddable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
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
public class ContatoMedicoRequest {
    @Pattern(regexp = "^\\d{10,11}$", message = "Telefone deve ter 10 ou 11 dígitos")
    private String telefone;
    
    @Pattern(regexp = "^\\d{10,11}$", message = "Telefone celular deve ter 10 ou 11 dígitos")
    private String telefoneCelular;
    
    @Pattern(regexp = "^\\d{10,11}$", message = "WhatsApp deve ter 10 ou 11 dígitos")
    private String whatsapp;
    
    @Email(message = "Email inválido")
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    private String email;
    
    @Email(message = "Email institucional inválido")
    @Size(max = 255, message = "Email institucional deve ter no máximo 255 caracteres")
    private String emailInstitucional;
    
    @Size(max = 255, message = "Site deve ter no máximo 255 caracteres")
    private String site;
}
