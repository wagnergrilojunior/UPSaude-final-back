package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import com.upsaude.validation.annotation.EmailValido;
import com.upsaude.validation.annotation.SiteValido;
import com.upsaude.validation.annotation.TelefoneValido;

@Embeddable
@Data
@Builder
@AllArgsConstructor
public class ContatoMedico {

    public ContatoMedico() {
        this.telefone = "";
        this.telefoneCelular = "";
        this.whatsapp = "";
        this.email = "";
        this.emailInstitucional = "";
        this.site = "";
    }

    @TelefoneValido
    @Column(name = "telefone", length = 20)
    private String telefone;

    @TelefoneValido
    @Column(name = "telefone_celular", length = 20)
    private String telefoneCelular;

    @TelefoneValido
    @Column(name = "whatsapp", length = 20)
    private String whatsapp;

    @EmailValido
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    @Column(name = "email", length = 255)
    private String email;

    @EmailValido
    @Size(max = 255, message = "Email institucional deve ter no máximo 255 caracteres")
    @Column(name = "email_institucional", length = 255)
    private String emailInstitucional;

    @Size(max = 255, message = "Site deve ter no máximo 255 caracteres")
    @SiteValido
    @Column(name = "site", length = 255)
    private String site;
}
