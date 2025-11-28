package com.upsaude.api.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Classe de requisição para criação e atualização de Medição Clínica.
 *
 * @author UPSaúde
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicaoClinicaRequest {

    @NotNull(message = "ID do paciente é obrigatório")
    private UUID pacienteId;

    @NotNull(message = "Data e hora da medição são obrigatórias")
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

