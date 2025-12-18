package com.upsaude.api.response.clinica.doencas;

import com.upsaude.api.response.clinica.doencas.DoencasResponse;
import com.upsaude.api.response.embeddable.AcompanhamentoDoencaPacienteResponse;
import com.upsaude.api.response.embeddable.DiagnosticoDoencaPacienteResponse;
import com.upsaude.api.response.paciente.PacienteResponse;
import com.upsaude.api.response.embeddable.TratamentoAtualDoencaPacienteResponse;


import com.upsaude.api.response.embeddable.AcompanhamentoDoencaPacienteResponse;
import com.upsaude.api.response.embeddable.DiagnosticoDoencaPacienteResponse;
import com.upsaude.api.response.embeddable.TratamentoAtualDoencaPacienteResponse;
import java.time.OffsetDateTime;
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
public class DoencasPacienteResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteResponse paciente;
    private DoencasResponse doenca;
    private DiagnosticoDoencaPacienteResponse diagnostico;
    private AcompanhamentoDoencaPacienteResponse acompanhamento;
    private TratamentoAtualDoencaPacienteResponse tratamentoAtual;
    private String observacoes;
}
