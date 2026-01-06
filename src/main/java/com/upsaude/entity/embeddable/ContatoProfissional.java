package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContatoProfissional {

    @Column(name = "telefone", length = 20)
    private String telefone;

    @Column(name = "celular", length = 20)
    private String celular;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "telefone_institucional", length = 20)
    private String telefoneInstitucional;

    @Column(name = "email_institucional", length = 255)
    private String emailInstitucional;
}
