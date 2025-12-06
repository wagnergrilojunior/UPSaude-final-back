package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Classe embeddable para sintomas e manifestações da doença.
 *
 * @author UPSaúde
 */
@Embeddable
@Data
@Builder
@AllArgsConstructor
public class SintomasDoenca {

    public SintomasDoenca() {
        this.sintomasComuns = "";
        this.sintomasGraves = "";
        this.sinaisClinicos = "";
        this.manifestacoesExtras = "";
    }

    @Column(name = "sintomas_comuns", columnDefinition = "TEXT")
    private String sintomasComuns; // Sintomas comuns da doença

    @Column(name = "sintomas_graves", columnDefinition = "TEXT")
    private String sintomasGraves; // Sintomas graves que requerem atenção imediata

    @Column(name = "sinais_clinicos", columnDefinition = "TEXT")
    private String sinaisClinicos; // Sinais clínicos característicos

    @Column(name = "manifestacoes_extras", columnDefinition = "TEXT")
    private String manifestacoesExtras; // Outras manifestações da doença
}

