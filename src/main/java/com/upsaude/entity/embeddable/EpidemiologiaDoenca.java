package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe embeddable para informações epidemiológicas da doença.
 *
 * @author UPSaúde
 */
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EpidemiologiaDoenca {

    @Column(name = "incidencia_anual")
    private Integer incidenciaAnual; // Incidência anual estimada (por 100.000 habitantes)

    @Column(name = "prevalencia")
    private Integer prevalencia; // Prevalência estimada (por 100.000 habitantes)

    @Column(name = "faixa_etaria_mais_afetada", length = 100)
    private String faixaEtariaMaisAfetada; // Ex: 0-5 anos, 20-40 anos, 60+ anos

    @Column(name = "sexo_mais_afetado", length = 1)
    private String sexoMaisAfetado; // M, F ou ambos

    @Column(name = "fatores_risco", columnDefinition = "TEXT")
    private String fatoresRisco; // Fatores de risco conhecidos

    @Column(name = "sazonalidade", length = 100)
    private String sazonalidade; // Ex: Verão, Inverno, Todo o ano

    @Column(name = "distribuicao_geografica", columnDefinition = "TEXT")
    private String distribuicaoGeografica; // Distribuição geográfica
}

