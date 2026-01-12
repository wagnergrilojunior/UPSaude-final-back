package com.upsaude.api.response.clinica.atendimento;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de resposta para sinais vitais")
public class SinalVitalResponse {
    private UUID id;
    private OffsetDateTime dataMedicao;
    private BigDecimal pesoKg;
    private Integer alturaCm;
    private BigDecimal imc;
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
