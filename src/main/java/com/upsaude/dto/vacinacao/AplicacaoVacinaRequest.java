package com.upsaude.dto.vacinacao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AplicacaoVacinaRequest {

    @NotNull(message = "Paciente é obrigatório")
    private UUID pacienteId;

    @NotNull(message = "Imunobiológico é obrigatório")
    private UUID imunobiologicoId;

    private UUID loteId;
    private String numeroLote;
    private UUID fabricanteId;
    private LocalDate dataValidade;

    private UUID tipoDoseId;
    private Integer numeroDose;
    private BigDecimal doseQuantidade;
    private String doseUnidade;

    private UUID localAplicacaoId;
    private UUID viaAdministracaoId;
    private UUID estrategiaId;

    @NotNull(message = "Data de aplicação é obrigatória")
    private OffsetDateTime dataAplicacao;

    private UUID profissionalId;
    private String profissionalFuncao;
    private UUID estabelecimentoId;

    private Boolean fontePrimaria;
    private String origemRegistro;
    private Boolean doseSubpotente;
    private String motivoSubpotencia;
    private String elegibilidadePrograma;
    private String fonteFinanciamento;
    private String observacoes;
}
