package com.upsaude.dto.agendamento;

import com.upsaude.enums.PrioridadeAtendimentoEnum;
import com.upsaude.enums.StatusAgendamentoEnum;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import com.upsaude.dto.clinica.atendimento.AtendimentoDTO;
import com.upsaude.dto.convenio.ConvenioDTO;
import com.upsaude.dto.profissional.EspecialidadesMedicasDTO;
import com.upsaude.dto.profissional.MedicosDTO;
import com.upsaude.dto.paciente.PacienteDTO;
import com.upsaude.dto.profissional.ProfissionaisSaudeDTO;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgendamentoDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteDTO paciente;
    private ProfissionaisSaudeDTO profissional;
    private MedicosDTO medico;
    private EspecialidadesMedicasDTO especialidade;
    private ConvenioDTO convenio;
    private AtendimentoDTO atendimento;
    private AgendamentoDTO agendamentoOriginal;
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
