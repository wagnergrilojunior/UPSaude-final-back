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
public class ContatoConvenioResponse {
    private String telefonePrincipal;
    private String telefoneSecundario;
    private String fax;
    private String whatsapp;
    private String emailPrincipal;
    private String emailSecundario;
    private String site;
    private String nomeContato;
    private String cargoContato;
    private String telefoneContato;
    private String emailContato;
}
