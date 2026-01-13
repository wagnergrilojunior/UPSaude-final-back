package com.upsaude.api.response.financeiro;

import com.upsaude.api.response.agendamento.AgendamentoSimplificadoResponse;
import com.upsaude.api.response.clinica.atendimento.AtendimentoProcedimentoSimplificadoResponse;
import com.upsaude.api.response.clinica.atendimento.AtendimentoSimplificadoResponse;
import com.upsaude.api.response.sistema.usuario.PacienteSimplificadoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstornoFinanceiroResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;

    private CompetenciaFinanceiraResponse competencia;
    private AgendamentoSimplificadoResponse agendamento;
    private AtendimentoSimplificadoResponse atendimento;
    private GuiaAtendimentoAmbulatorialSimplificadoResponse guiaAmbulatorial;
    private PacienteSimplificadoResponse paciente;
    private AtendimentoProcedimentoSimplificadoResponse atendimentoProcedimento;

    private LancamentoFinanceiroSimplificadoResponse lancamentoFinanceiroOrigem;
    private LancamentoFinanceiroSimplificadoResponse lancamentoFinanceiroEstorno;

    private String motivo;
    private BigDecimal valorEstornado;
    private String procedimentoCodigo;
    private String procedimentoNome;
    private OffsetDateTime dataEstorno;
    private String observacoes;
}

