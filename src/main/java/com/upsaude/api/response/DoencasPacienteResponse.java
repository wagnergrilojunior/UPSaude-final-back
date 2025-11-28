package com.upsaude.api.response;

import com.upsaude.entity.embeddable.AcompanhamentoDoencaPaciente;
import com.upsaude.entity.embeddable.DiagnosticoDoencaPaciente;
import com.upsaude.entity.embeddable.TratamentoAtualDoencaPaciente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoencasPacienteResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private UUID pacienteId;
    private String pacienteNome;
    private UUID doencaId;
    private String doencaNome;
    private UUID cidPrincipalId;
    private String cidPrincipalCodigo;
    private String cidPrincipalDescricao;
    private DiagnosticoDoencaPaciente diagnostico;
    private AcompanhamentoDoencaPaciente acompanhamento;
    private TratamentoAtualDoencaPaciente tratamentoAtual;
    private String observacoes;
}

