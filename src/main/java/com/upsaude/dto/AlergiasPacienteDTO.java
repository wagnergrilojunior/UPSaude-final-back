package com.upsaude.dto;

import com.upsaude.dto.embeddable.DiagnosticoAlergiaPacienteDTO;
import com.upsaude.dto.embeddable.HistoricoReacoesAlergiaPacienteDTO;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlergiasPacienteDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteDTO paciente;
    private AlergiasDTO alergia;
    private DiagnosticoAlergiaPacienteDTO diagnostico;
    private HistoricoReacoesAlergiaPacienteDTO historicoReacoes;
    private String observacoes;
}
