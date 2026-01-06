package com.upsaude.api.response.embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManutencaoCalibracaoEquipamentoResponse {

    private Boolean requerCalibracao;

    private Integer periodoCalibracaoMeses;

    private Boolean requerManutencaoPreventiva;

    private Integer periodoManutencaoMeses;

    private String tipoManutencao;
}
