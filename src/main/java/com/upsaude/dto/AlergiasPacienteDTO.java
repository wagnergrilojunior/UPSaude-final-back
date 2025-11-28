package com.upsaude.dto;

import com.upsaude.entity.embeddable.DiagnosticoAlergiaPaciente;
import com.upsaude.entity.embeddable.HistoricoReacoesAlergiaPaciente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlergiasPacienteDTO {
    private UUID id;
    private UUID pacienteId;
    private UUID alergiaId;
    private DiagnosticoAlergiaPaciente diagnostico;
    private HistoricoReacoesAlergiaPaciente historicoReacoes;
    private String observacoes;
    private Boolean alertaMedico;
    private Boolean active;
}
