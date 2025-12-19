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
public class IdentificacaoMedicamentoResponse {
    private String principioAtivo;
    private String nomeComercial;
    private String nomeGenerico;
    private String codigoInterno;
    private String catmatCodigo;
    private String codigoAnvisa;
    private String codigoTuss;
}
