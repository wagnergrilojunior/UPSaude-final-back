package com.upsaude.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.upsaude.api.request.embeddable.DiagnosticoAlergiaPacienteRequest;
import com.upsaude.api.request.embeddable.HistoricoReacoesAlergiaPacienteRequest;
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
public class AlergiasPacienteRequest {
    @NotNull(message = "Paciente é obrigatório")
    private UUID paciente;
    
    @NotNull(message = "Alergia é obrigatória")
    private UUID alergia;
    
    private DiagnosticoAlergiaPacienteRequest diagnostico;
    private HistoricoReacoesAlergiaPacienteRequest historicoReacoes;
    
    private String observacoes;
    private Boolean alertaMedico;
}
