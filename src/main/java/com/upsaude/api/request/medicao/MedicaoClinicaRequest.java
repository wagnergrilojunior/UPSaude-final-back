package com.upsaude.api.request.medicao;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de medicao clinica")
public class MedicaoClinicaRequest {
    private UUID paciente;
    private OffsetDateTime dataHora;
    private Integer pressaoSistolica;
    private Integer pressaoDiastolica;
    private Integer frequenciaCardiaca;
    private Integer frequenciaRespiratoria;
    private BigDecimal temperatura;
    private Integer saturacaoOxigenio;
    private BigDecimal glicemiaCapilar;
    private BigDecimal peso;
    private BigDecimal altura;
    private BigDecimal circunferenciaAbdominal;
    private BigDecimal imc;
    private String observacoes;
}
