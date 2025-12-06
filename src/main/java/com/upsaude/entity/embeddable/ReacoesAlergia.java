package com.upsaude.entity.embeddable;

import com.upsaude.enums.TipoReacaoAlergicaEnum;
import com.upsaude.util.converter.TipoReacaoAlergicaEnumConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Classe embeddable para informações de reações alérgicas.
 *
 * @author UPSaúde
 */
@Embeddable
@Data
@Builder
@AllArgsConstructor
public class ReacoesAlergia {

    @Convert(converter = TipoReacaoAlergicaEnumConverter.class)
    @Column(name = "tipo_reacao_principal")
    private TipoReacaoAlergicaEnum tipoReacaoPrincipal;

    @Column(name = "reacoes_comuns", columnDefinition = "TEXT")
    private String reacoesComuns;

    @Column(name = "reacoes_graves", columnDefinition = "TEXT")
    private String reacoesGraves;

    @Column(name = "sintomas", columnDefinition = "TEXT")
    private String sintomas;

    @Column(name = "tempo_apos_exposicao", length = 100)
    private String tempoAposExposicao;

    // Garantia contra null em string
    public ReacoesAlergia() {
        this.reacoesComuns = "";
        this.reacoesGraves = "";
        this.sintomas = "";
        this.tempoAposExposicao = "";
    }
}


