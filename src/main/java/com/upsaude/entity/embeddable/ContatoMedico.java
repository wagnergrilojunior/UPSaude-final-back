package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.upsaude.validation.annotation.EmailValido;
import com.upsaude.validation.annotation.SiteValido;
import com.upsaude.validation.annotation.TelefoneValido;

@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContatoMedico {

    @TelefoneValido
    @Column(name = "telefone", length = 20)
    private String telefone;

    @TelefoneValido
    @Column(name = "celular", length = 20)
    private String celular;

    @EmailValido
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    @Column(name = "email", length = 255)
    private String email;

    @Size(max = 255, message = "Site deve ter no máximo 255 caracteres")
    @SiteValido
    @Column(name = "site", length = 255)
    private String site;
}
