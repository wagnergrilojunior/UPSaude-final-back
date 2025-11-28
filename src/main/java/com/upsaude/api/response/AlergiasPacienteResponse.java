package com.upsaude.api.response;

import com.upsaude.entity.embeddable.DiagnosticoAlergiaPaciente;
import com.upsaude.entity.embeddable.HistoricoReacoesAlergiaPaciente;
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
public class AlergiasPacienteResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private UUID pacienteId;
    private String pacienteNome;
    private UUID alergiaId;
    private String alergiaNome;
    private DiagnosticoAlergiaPaciente diagnostico;
    private HistoricoReacoesAlergiaPaciente historicoReacoes;
    private String observacoes;
    private Boolean alertaMedico;
}
