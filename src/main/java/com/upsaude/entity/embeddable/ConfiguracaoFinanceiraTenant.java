package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfiguracaoFinanceiraTenant {

    @Default
    @Column(name = "financeiro_habilitado")
    private Boolean financeiroHabilitado = false;

    @Column(name = "plano_contas_padrao_id")
    private UUID planoContasPadraoId;

    @Column(name = "regra_competencia", length = 50)
    private String regraCompetencia; 

    @Column(name = "politica_reserva_consumo", length = 50)
    private String politicaReservaConsumo; 
}
