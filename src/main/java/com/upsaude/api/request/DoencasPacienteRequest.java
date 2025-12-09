package com.upsaude.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.upsaude.api.request.embeddable.AcompanhamentoDoencaPacienteRequest;
import com.upsaude.api.request.embeddable.DiagnosticoDoencaPacienteRequest;
import com.upsaude.api.request.embeddable.TratamentoAtualDoencaPacienteRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    
    @Valid
    private DiagnosticoDoencaPacienteRequest diagnostico;
    
    @Valid
    private AcompanhamentoDoencaPacienteRequest acompanhamento;
    
    @Valid
    private TratamentoAtualDoencaPacienteRequest tratamentoAtual;
    
    @Size(max = 1000, message = "Observações deve ter no máximo 1000 caracteres")
    private String observacoes;
}
