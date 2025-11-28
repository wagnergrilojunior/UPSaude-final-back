package com.upsaude.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * DTO (Data Transfer Object) para Medição Clínica.
 *
 * @author UPSaúde
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicaoClinicaDTO {
    private UUID id;
    private UUID pacienteId;
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
    private Boolean active;
}

