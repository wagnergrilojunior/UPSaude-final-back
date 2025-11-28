package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe embeddable para dados de contato do médico.
 *
 * @author UPSaúde
 */
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContatoMedico {

    @Pattern(regexp = "^\\d{10,11}$", message = "Telefone deve ter 10 ou 11 dígitos")
    @Column(name = "telefone", length = 20)
    private String telefone;

    @Pattern(regexp = "^\\d{10,11}$", message = "Telefone celular deve ter 10 ou 11 dígitos")
    @Column(name = "telefone_celular", length = 20)
    private String telefoneCelular;

    @Pattern(regexp = "^\\d{10,11}$", message = "WhatsApp deve ter 10 ou 11 dígitos")
    @Column(name = "whatsapp", length = 20)
    private String whatsapp;

    @Email(message = "Email inválido")
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    @Column(name = "email", length = 255)
    private String email;

    @Email(message = "Email institucional inválido")
    @Size(max = 255, message = "Email institucional deve ter no máximo 255 caracteres")
    @Column(name = "email_institucional", length = 255)
    private String emailInstitucional;

    @Size(max = 255, message = "Site deve ter no máximo 255 caracteres")
    @Column(name = "site", length = 255)
    private String site; // Site pessoal ou clínica
}

