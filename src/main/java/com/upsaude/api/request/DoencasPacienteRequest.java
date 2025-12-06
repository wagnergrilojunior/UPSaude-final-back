package com.upsaude.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.upsaude.api.request.embeddable.AcompanhamentoDoencaPacienteRequest;
import com.upsaude.api.request.embeddable.DiagnosticoDoencaPacienteRequest;
import com.upsaude.api.request.embeddable.TratamentoAtualDoencaPacienteRequest;
import jakarta.validation.constraints.NotNull;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class DoencasPacienteRequest {
    @NotNull(message = "Paciente é obrigatório")
    private UUID paciente;
    
    @NotNull(message = "Doença é obrigatória")
    private UUID doenca;
    
    private UUID cidPrincipal;
    
    private DiagnosticoDoencaPacienteRequest diagnostico;
    private AcompanhamentoDoencaPacienteRequest acompanhamento;
    private TratamentoAtualDoencaPacienteRequest tratamentoAtual;
    
    private String observacoes;
}
