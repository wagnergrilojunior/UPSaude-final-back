package com.upsaude.api.response.clinica.cirurgia;

import com.upsaude.api.response.clinica.atendimento.PacienteAtendimentoResponse;
import com.upsaude.api.response.clinica.atendimento.ProfissionalAtendimentoResponse;
import com.upsaude.api.response.clinica.atendimento.MedicoConsultaResponse;
import com.upsaude.api.response.agendamento.ConvenioAgendamentoResponse;
import com.upsaude.api.response.referencia.cid.DiagnosticoPrincipalResponse;

import com.upsaude.enums.StatusCirurgiaEnum;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CirurgiaResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private UUID tenantId;
    private UUID estabelecimentoId;
    private PacienteAtendimentoResponse paciente;
    private ProfissionalAtendimentoResponse cirurgiaoPrincipal;
    private MedicoConsultaResponse medicoCirurgiao;
    private ConvenioAgendamentoResponse convenio;
    private DiagnosticoPrincipalResponse diagnosticoPrincipal;
    private String descricao;
    private OffsetDateTime dataHoraPrevista;
    private OffsetDateTime dataHoraInicio;
    private OffsetDateTime dataHoraFim;
    private Integer duracaoPrevistaMinutos;
    private Integer duracaoRealMinutos;
    private String salaCirurgica;
    private String leitoCentroCirurgico;
    private StatusCirurgiaEnum status;
    private BigDecimal valorCirurgia;
    private BigDecimal valorMaterial;
    private BigDecimal valorTotal;
    private String observacoesPreOperatorio;
    private String observacoesPosOperatorio;
    private String observacoes;
    private String observacoesInternas;

    @Builder.Default
    private List<EquipeCirurgicaResponse> equipe = new ArrayList<>();

    @Builder.Default
    private List<CirurgiaProcedimentoResponse> procedimentos = new ArrayList<>();
}
