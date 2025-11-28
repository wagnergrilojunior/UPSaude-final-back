package com.upsaude.dto;

import com.upsaude.entity.embeddable.AcompanhamentoDoencaPaciente;
import com.upsaude.entity.embeddable.DiagnosticoDoencaPaciente;
import com.upsaude.entity.embeddable.TratamentoAtualDoencaPaciente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoencasPacienteDTO {
    private UUID id;
    private UUID pacienteId;
    private UUID doencaId;
    private UUID cidPrincipalId;
    private DiagnosticoDoencaPaciente diagnostico;
    private AcompanhamentoDoencaPaciente acompanhamento;
    private TratamentoAtualDoencaPaciente tratamentoAtual;
    private String observacoes;
    private Boolean active;
}

