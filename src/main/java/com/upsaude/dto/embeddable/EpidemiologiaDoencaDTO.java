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
public class EpidemiologiaDoencaDTO {
    private Integer incidenciaAnual;
    private Integer prevalencia;
    private String faixaEtariaMaisAfetada;
    private String sexoMaisAfetado;
    private String fatoresRisco;
    private String sazonalidade;
    private String distribuicaoGeografica;
}
