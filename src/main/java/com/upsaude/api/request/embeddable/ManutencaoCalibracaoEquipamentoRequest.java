package com.upsaude.api.request.embeddable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
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
@Schema(description = "Dados de manutenção e calibração do equipamento")
public class ManutencaoCalibracaoEquipamentoRequest {

    @Builder.Default
    private Boolean requerCalibracao = false;

    private Integer periodoCalibracaoMeses;

    @Builder.Default
    private Boolean requerManutencaoPreventiva = false;

    private Integer periodoManutencaoMeses;

    @Size(max = 100, message = "Tipo de manutenção deve ter no máximo 100 caracteres")
    private String tipoManutencao;
}
