package com.upsaude.api.response;

import com.upsaude.enums.PrioridadeAtendimentoEnum;
import com.upsaude.enums.StatusAgendamentoEnum;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
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
}
