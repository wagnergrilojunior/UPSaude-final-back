package com.upsaude.dto;

import com.upsaude.dto.embeddable.AcompanhamentoDoencaPacienteDTO;
import com.upsaude.dto.embeddable.DiagnosticoDoencaPacienteDTO;
import com.upsaude.dto.embeddable.TratamentoAtualDoencaPacienteDTO;
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
    private DiagnosticoDoencaPacienteDTO diagnostico;
    private AcompanhamentoDoencaPacienteDTO acompanhamento;
    private TratamentoAtualDoencaPacienteDTO tratamentoAtual;
    private String observacoes;
}
