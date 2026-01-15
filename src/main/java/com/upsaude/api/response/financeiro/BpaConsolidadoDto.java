package com.upsaude.api.response.financeiro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BpaConsolidadoDto {

    private String cnes;

private String competencia;

private String procedimentoCodigo;
    private String procedimentoNome;

    private String cidPrincipal;

    private Integer quantidade;
    private BigDecimal valorUnitario;
    private BigDecimal valorTotal;

    private OffsetDateTime dataAtendimento;
    private LocalDate dataAtendimentoLocalDate;

    private String cboProfissional;

    private String caraterAtendimento;

    private Integer idade;
private String sexo;
    private String municipioPacienteIbge;

    private String tipoDocumentoOrigem;
    private String numeroDocumentoOrigem;

private String origemTipo;
    private UUID origemId;

    private UUID agendamentoId;
    private UUID atendimentoId;
    private UUID pacienteId;
}
