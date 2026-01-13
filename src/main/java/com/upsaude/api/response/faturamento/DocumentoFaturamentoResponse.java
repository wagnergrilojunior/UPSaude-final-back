package com.upsaude.api.response.faturamento;

import com.upsaude.api.response.agendamento.AgendamentoSimplificadoResponse;
import com.upsaude.api.response.clinica.atendimento.AtendimentoSimplificadoResponse;
import com.upsaude.api.response.convenio.ConvenioSimplificadoResponse;
import com.upsaude.api.response.financeiro.CompetenciaFinanceiraResponse;
import com.upsaude.api.response.financeiro.GuiaAtendimentoAmbulatorialSimplificadoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoFaturamentoResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;

    private CompetenciaFinanceiraResponse competencia;
    private ConvenioSimplificadoResponse convenio;
    private AgendamentoSimplificadoResponse agendamento;
    private AtendimentoSimplificadoResponse atendimento;
    private GuiaAtendimentoAmbulatorialSimplificadoResponse guiaAmbulatorial;

    private String tipo;
    private String numero;
    private String serie;
    private String status;
    private String pagadorTipo;

    private OffsetDateTime emitidoEm;
    private OffsetDateTime canceladoEm;
    private UUID canceladoPor;

    private String payloadLayout;

    @Builder.Default
    private List<DocumentoFaturamentoItemResponse> itens = new ArrayList<>();
}

