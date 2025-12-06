package com.upsaude.dto.embeddable;

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
public class ContatoMedicoDTO {
    private String telefone;
    private String telefoneCelular;
    private String whatsapp;
    private String email;
    private String emailInstitucional;
    private String site;
}
