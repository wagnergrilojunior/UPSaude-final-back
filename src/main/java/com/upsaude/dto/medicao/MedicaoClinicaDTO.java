package com.upsaude.dto.medicao;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicaoClinicaDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteDTO paciente;
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
