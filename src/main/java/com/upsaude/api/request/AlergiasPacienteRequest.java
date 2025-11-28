package com.upsaude.api.request;

import com.upsaude.entity.embeddable.DiagnosticoAlergiaPaciente;
import com.upsaude.entity.embeddable.HistoricoReacoesAlergiaPaciente;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlergiasPacienteRequest {

    @NotNull(message = "Paciente é obrigatório")
    private UUID pacienteId;

    @NotNull(message = "Alergia é obrigatória")
    private UUID alergiaId;

    private DiagnosticoAlergiaPaciente diagnostico;

    private HistoricoReacoesAlergiaPaciente historicoReacoes;

    private String observacoes;

    private Boolean alertaMedico;
}
