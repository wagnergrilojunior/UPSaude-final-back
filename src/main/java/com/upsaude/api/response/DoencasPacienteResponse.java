package com.upsaude.api.response;

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
public class DoencasPacienteResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteResponse paciente;
    private DoencasResponse doenca;
    private CidDoencasResponse cidPrincipal;
    private DiagnosticoDoencaPaciente diagnostico;
    private AcompanhamentoDoencaPaciente acompanhamento;
    private TratamentoAtualDoencaPaciente tratamentoAtual;
    private String observacoes;
}
