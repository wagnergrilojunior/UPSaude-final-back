package com.upsaude.dto;

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
public class DoencasPacienteDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteDTO paciente;
    private DoencasDTO doenca;
    private CidDoencasDTO cidPrincipal;
    private DiagnosticoDoencaPaciente diagnostico;
    private AcompanhamentoDoencaPaciente acompanhamento;
    private TratamentoAtualDoencaPaciente tratamentoAtual;
    private String observacoes;
}
