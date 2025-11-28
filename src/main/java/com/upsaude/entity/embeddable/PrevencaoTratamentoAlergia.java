package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe embeddable para informações de prevenção e tratamento da alergia.
 *
 * @author UPSaúde
 */
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrevencaoTratamentoAlergia {

    @Column(name = "medidas_prevencao", columnDefinition = "TEXT")
    private String medidasPrevencao; // Medidas de prevenção recomendadas

    @Column(name = "tratamento_imediato", columnDefinition = "TEXT")
    private String tratamentoImediato; // Tratamento imediato em caso de reação

    @Column(name = "medicamentos_evitar", columnDefinition = "TEXT")
    private String medicamentosEvitar; // Medicamentos que devem ser evitados

    @Column(name = "alimentos_evitar", columnDefinition = "TEXT")
    private String alimentosEvitar; // Alimentos que devem ser evitados

    @Column(name = "substancias_evitar", columnDefinition = "TEXT")
    private String substanciasEvitar; // Substâncias que devem ser evitadas

    @Column(name = "epinefrina_necessaria", nullable = false)
    @Builder.Default
    private Boolean epinefrinaNecessaria = false; // Se requer epinefrina em caso de reação grave

    @Column(name = "antihistaminico_recomendado", length = 255)
    private String antihistaminicoRecomendado; // Anti-histamínico recomendado
}

