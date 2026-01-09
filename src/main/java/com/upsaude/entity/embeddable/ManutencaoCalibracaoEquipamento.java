package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManutencaoCalibracaoEquipamento {

    @Column(name = "requer_calibracao", nullable = false)
    private Boolean requerCalibracao = false;

    @Column(name = "periodo_calibracao_meses")
    private Integer periodoCalibracaoMeses;

    @Column(name = "requer_manutencao_preventiva", nullable = false)
    private Boolean requerManutencaoPreventiva = false;

    @Column(name = "periodo_manutencao_meses")
    private Integer periodoManutencaoMeses;

    @Column(name = "tipo_manutencao", length = 100)
    private String tipoManutencao;
}
