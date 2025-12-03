package com.upsaude.api.request;

import com.upsaude.entity.embeddable.AcompanhamentoDoencaPaciente;
import com.upsaude.entity.embeddable.DiagnosticoDoencaPaciente;
import com.upsaude.entity.embeddable.TratamentoAtualDoencaPaciente;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoencasPacienteRequest {
    private UUID paciente;
    private UUID doenca;
    private UUID cidPrincipal;
    private DiagnosticoDoencaPaciente diagnostico;
    private AcompanhamentoDoencaPaciente acompanhamento;
    private TratamentoAtualDoencaPaciente tratamentoAtual;
    private String observacoes;
}
