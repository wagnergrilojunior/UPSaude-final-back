package com.upsaude.api.request.clinica.atendimento;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Solicitação para registro de sinais vitais")
public class SinalVitalRequest {
    private BigDecimal pesoKg;
    private Integer alturaCm;
    private Integer pressaoArterialSistolica;
    private Integer pressaoArterialDiastolica;
    private Integer frequenciaCardiacaBpm;
    private Integer frequenciaRespiratoriaRpm;
    private BigDecimal temperaturaCelsius;
    private Integer saturacaoO2Percentual;
    private Integer glicemiaCapilarMgDl;
    private String statusGlicemia;
    private String observacoes;
}
