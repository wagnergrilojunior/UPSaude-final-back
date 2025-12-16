package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

@Embeddable
@Data
@Builder
@AllArgsConstructor
public class CalendarioVacinal {

    public CalendarioVacinal() {
        this.calendarioBasico = false;
        this.calendarioCampanha = false;
        this.faixaEtariaCalendario = "";
        this.situacaoEpidemiologica = "";
        this.obrigatoria = false;
    }

    @Column(name = "calendario_basico", nullable = false)
    @Builder.Default
    private Boolean calendarioBasico = false;

    @Column(name = "calendario_campanha", nullable = false)
    @Builder.Default
    private Boolean calendarioCampanha = false;

    @Column(name = "faixa_etaria_calendario", length = 100)
    private String faixaEtariaCalendario;

    @Column(name = "situacao_epidemiologica", length = 50)
    private String situacaoEpidemiologica;

    @Column(name = "obrigatoria", nullable = false)
    @Builder.Default
    private Boolean obrigatoria = false;
}
