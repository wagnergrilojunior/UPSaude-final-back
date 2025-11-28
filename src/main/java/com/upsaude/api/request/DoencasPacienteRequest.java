package com.upsaude.api.request;

import com.upsaude.entity.embeddable.AcompanhamentoDoencaPaciente;
import com.upsaude.entity.embeddable.DiagnosticoDoencaPaciente;
import com.upsaude.entity.embeddable.TratamentoAtualDoencaPaciente;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoencasPacienteRequest {
    @NotNull(message = "Paciente é obrigatório")
    private UUID pacienteId;

    @NotNull(message = "Doença é obrigatória")
    private UUID doencaId;

    private UUID cidPrincipalId;

    private DiagnosticoDoencaPaciente diagnostico;

    private AcompanhamentoDoencaPaciente acompanhamento;

    private TratamentoAtualDoencaPaciente tratamentoAtual;

    private String observacoes;
}

