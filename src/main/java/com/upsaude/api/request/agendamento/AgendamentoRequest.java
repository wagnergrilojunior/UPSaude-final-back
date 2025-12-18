package com.upsaude.api.request.agendamento;

import com.upsaude.entity.paciente.Paciente;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.PrioridadeAtendimentoEnum;
import com.upsaude.enums.StatusAgendamentoEnum;
import com.upsaude.util.converter.PrioridadeAtendimentoEnumDeserializer;
import com.upsaude.util.converter.StatusAgendamentoEnumDeserializer;
import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de agendamento")
public class AgendamentoRequest {
    @NotNull(message = "Paciente é obrigatório")
    private UUID paciente;

    private UUID profissional;
    private UUID medico;
    private UUID especialidade;
    private UUID convenio;
    private UUID atendimento;
    private UUID agendamentoOriginal;

    @NotNull(message = "Data e hora são obrigatórias")
    private OffsetDateTime dataHora;
    private OffsetDateTime dataHoraFim;
    private Integer duracaoPrevistaMinutos;

    @NotNull(message = "Status do agendamento é obrigatório")
    @JsonDeserialize(using = StatusAgendamentoEnumDeserializer.class)
    private StatusAgendamentoEnum status;

    @JsonDeserialize(using = PrioridadeAtendimentoEnumDeserializer.class)
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
