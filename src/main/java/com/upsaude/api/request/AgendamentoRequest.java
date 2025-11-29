package com.upsaude.api.request;

import com.upsaude.enums.PrioridadeAtendimentoEnum;
import com.upsaude.enums.StatusAgendamentoEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Classe de requisição para criação e atualização de Agendamento.
 *
 * @author UPSaúde
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgendamentoRequest {

    @NotNull(message = "ID do paciente é obrigatório")
    private UUID pacienteId;

    @NotNull(message = "ID do profissional é obrigatório")
    private UUID profissionalId;

    private UUID medicoId;
    private UUID especialidadeId;
    private UUID convenioId;
    private UUID agendamentoOriginalId;

    @NotNull(message = "Data e hora do agendamento são obrigatórias")
    private OffsetDateTime dataHora;

    private OffsetDateTime dataHoraFim;
    private Integer duracaoPrevistaMinutos;

    @NotNull(message = "Status do agendamento é obrigatório")
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
    private String motivoCancelamento;
    private String motivoReagendamento;
    private Boolean notificacaoEnviada24h;
    private Boolean notificacaoEnviada1h;
    private Boolean confirmacaoEnviada;
}

