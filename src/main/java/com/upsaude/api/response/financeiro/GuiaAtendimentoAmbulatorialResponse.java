package com.upsaude.api.response.financeiro;

import com.upsaude.api.response.agendamento.AgendamentoSimplificadoResponse;
import com.upsaude.api.response.agendamento.EstabelecimentoSimplificadoResponse;
import com.upsaude.api.response.clinica.atendimento.AtendimentoSimplificadoResponse;
import com.upsaude.api.response.faturamento.DocumentoFaturamentoSimplificadoResponse;
import com.upsaude.api.response.sistema.usuario.PacienteSimplificadoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuiaAtendimentoAmbulatorialResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;

    private CompetenciaFinanceiraResponse competencia;
    private AgendamentoSimplificadoResponse agendamento;
    private AtendimentoSimplificadoResponse atendimento;
    private PacienteSimplificadoResponse paciente;
    private EstabelecimentoSimplificadoResponse estabelecimento;
    private DocumentoFaturamentoSimplificadoResponse documentoFaturamento;

    private String numero;
    private String status;
    private OffsetDateTime emitidaEm;
    private OffsetDateTime canceladaEm;
    private UUID canceladaPor;
    private String observacoes;
}

