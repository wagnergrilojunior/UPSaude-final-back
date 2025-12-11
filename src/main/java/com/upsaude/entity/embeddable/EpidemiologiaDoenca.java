package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Embeddable
@Data
@Builder
@AllArgsConstructor
public class EpidemiologiaDoenca {

    public EpidemiologiaDoenca() {
        this.faixaEtariaMaisAfetada = "";
        this.sexoMaisAfetado = "";
        this.fatoresRisco = "";
        this.sazonalidade = "";
        this.distribuicaoGeografica = "";
    }

    @Column(name = "incidencia_anual")
    private Integer incidenciaAnual;

    @Column(name = "prevalencia")
    private Integer prevalencia;

    @Column(name = "faixa_etaria_mais_afetada", length = 100)
    private String faixaEtariaMaisAfetada;

    @Column(name = "sexo_mais_afetado", length = 1)
    private String sexoMaisAfetado;

    @Column(name = "fatores_risco", columnDefinition = "TEXT")
    private String fatoresRisco;

    @Column(name = "sazonalidade", length = 100)
    private String sazonalidade;

    @Column(name = "distribuicao_geografica", columnDefinition = "TEXT")
    private String distribuicaoGeografica;
}
