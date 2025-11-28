package com.upsaude.entity.embeddable;

import com.upsaude.enums.TipoReacaoAlergicaEnum;
import com.upsaude.util.converter.TipoReacaoAlergicaEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe embeddable para informações de reações alérgicas.
 *
 * @author UPSaúde
 */
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReacoesAlergia {

    @Convert(converter = TipoReacaoAlergicaEnumConverter.class)
    @Column(name = "tipo_reacao_principal")
    private TipoReacaoAlergicaEnum tipoReacaoPrincipal; // Tipo de reação principal

    @Column(name = "reacoes_comuns", columnDefinition = "TEXT")
    private String reacoesComuns; // Reações comuns observadas

    @Column(name = "reacoes_graves", columnDefinition = "TEXT")
    private String reacoesGraves; // Reações graves que podem ocorrer

    @Column(name = "sintomas", columnDefinition = "TEXT")
    private String sintomas; // Sintomas característicos da alergia

    @Column(name = "tempo_apos_exposicao", length = 100)
    private String tempoAposExposicao; // Tempo após exposição para aparecimento dos sintomas (ex: Imediato, 2-4 horas)
}

