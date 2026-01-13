package com.upsaude.api.response.agendamento;

import com.upsaude.enums.StatusAgendamentoEnum;
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
public class AgendamentoSimplificadoResponse {
    private UUID id;
    private OffsetDateTime dataHora;
    private OffsetDateTime dataHoraFim;
    private StatusAgendamentoEnum status;
}

