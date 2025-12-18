package com.upsaude.api.request.alergia;

import com.upsaude.entity.paciente.Paciente;
import com.upsaude.api.request.embeddable.DiagnosticoAlergiaPacienteRequest;
import com.upsaude.api.request.embeddable.HistoricoReacoesAlergiaPacienteRequest;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.upsaude.api.request.embeddable.DiagnosticoAlergiaPacienteRequest;
import com.upsaude.api.request.embeddable.HistoricoReacoesAlergiaPacienteRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Dados de alergias paciente")
public class AlergiasPacienteRequest {
    @NotNull(message = "Paciente é obrigatório")
    private UUID paciente;

    @NotNull(message = "Alergia é obrigatória")
    private UUID alergia;

    @Valid
    private DiagnosticoAlergiaPacienteRequest diagnostico;

    @Valid
    private HistoricoReacoesAlergiaPacienteRequest historicoReacoes;

    @Size(max = 1000, message = "Observações deve ter no máximo 1000 caracteres")
    private String observacoes;
    private Boolean alertaMedico;
}
