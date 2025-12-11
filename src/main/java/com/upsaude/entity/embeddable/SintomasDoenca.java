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
public class SintomasDoenca {

    public SintomasDoenca() {
        this.sintomasComuns = "";
        this.sintomasGraves = "";
        this.sinaisClinicos = "";
        this.manifestacoesExtras = "";
    }

    @Column(name = "sintomas_comuns", columnDefinition = "TEXT")
    private String sintomasComuns;

    @Column(name = "sintomas_graves", columnDefinition = "TEXT")
    private String sintomasGraves;

    @Column(name = "sinais_clinicos", columnDefinition = "TEXT")
    private String sinaisClinicos;

    @Column(name = "manifestacoes_extras", columnDefinition = "TEXT")
    private String manifestacoesExtras;
}
