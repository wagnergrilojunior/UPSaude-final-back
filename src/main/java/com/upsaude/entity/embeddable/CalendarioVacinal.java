package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe embeddable para informações de calendário vacinal.
 *
 * @author UPSaúde
 */
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalendarioVacinal {

    @Column(name = "calendario_basico", nullable = false)
    @Builder.Default
    private Boolean calendarioBasico = false; // Se faz parte do calendário básico

    @Column(name = "calendario_campanha", nullable = false)
    @Builder.Default
    private Boolean calendarioCampanha = false; // Se faz parte de campanhas

    @Column(name = "faixa_etaria_calendario", length = 100)
    private String faixaEtariaCalendario; // Faixa etária no calendário

    @Column(name = "situacao_epidemiologica", length = 50)
    private String situacaoEpidemiologica; // Ex: Endêmica, Epidêmica, Eliminada

    @Column(name = "obrigatoria", nullable = false)
    @Builder.Default
    private Boolean obrigatoria = false; // Se é obrigatória no calendário
}

