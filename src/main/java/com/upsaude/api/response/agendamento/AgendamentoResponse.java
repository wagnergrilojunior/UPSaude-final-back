package com.upsaude.api.response.agendamento;

import com.upsaude.api.response.sistema.notificacao.NotificacaoResponse;
import com.upsaude.api.response.profissional.EspecialidadesMedicasResponse;
import com.upsaude.api.response.profissional.MedicosResponse;
import com.upsaude.api.response.profissional.ProfissionaisSaudeResponse;
import com.upsaude.api.response.clinica.atendimento.CheckInAtendimentoResponse;
import com.upsaude.api.response.clinica.atendimento.AtendimentoResponse;
import com.upsaude.api.response.paciente.PacienteResponse;
import com.upsaude.api.response.convenio.ConvenioResponse;


import com.upsaude.enums.PrioridadeAtendimentoEnum;
import com.upsaude.enums.StatusAgendamentoEnum;
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
public class AgendamentoResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteResponse paciente;
    private ProfissionaisSaudeResponse profissional;
    private MedicosResponse medico;
    private EspecialidadesMedicasResponse especialidade;
    private ConvenioResponse convenio;
    private AtendimentoResponse atendimento;
    private AgendamentoResponse agendamentoOriginal;
    private OffsetDateTime dataHora;
    private OffsetDateTime dataHoraFim;
    private Integer duracaoPrevistaMinutos;
    private StatusAgendamentoEnum status;
    private PrioridadeAtendimentoEnum prioridade;
    private Boolean ehEncaixe;
    private Boolean ehRetorno;
    private String motivoConsulta;
    private String observacoesAgendamento;
    private String observacoesInternas;
    private Boolean temConflitoHorario;
    private Boolean sobreposicaoPermitida;
    private String justificativaConflito;
    private OffsetDateTime dataCancelamento;
    private UUID canceladoPor;
    private String motivoCancelamento;
    private OffsetDateTime dataReagendamento;
    private UUID reagendadoPor;
    private String motivoReagendamento;
    private UUID agendadoPor;
    private UUID confirmadoPor;
    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataUltimaAlteracao;
    private UUID alteradoPor;
    private Boolean notificacaoEnviada24h;
    private Boolean notificacaoEnviada1h;
    private Boolean confirmacaoEnviada;

    @Builder.Default
    private List<AgendamentoResponse> reagendamentos = new ArrayList<>();

    @Builder.Default
    private List<NotificacaoResponse> notificacoes = new ArrayList<>();

    @Builder.Default
    private List<CheckInAtendimentoResponse> checkIns = new ArrayList<>();
}
