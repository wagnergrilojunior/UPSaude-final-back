package com.upsaude.api.response.embeddable;

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
public class ContatoProfissionalResponse {
    private String telefone;
    private String celular;
    private String email;
    private String telefoneInstitucional;
    private String emailInstitucional;
}

