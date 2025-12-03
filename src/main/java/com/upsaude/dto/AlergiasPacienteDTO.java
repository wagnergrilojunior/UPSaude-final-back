package com.upsaude.dto;

import com.upsaude.entity.embeddable.DiagnosticoAlergiaPaciente;
import com.upsaude.entity.embeddable.HistoricoReacoesAlergiaPaciente;
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
    private DiagnosticoAlergiaPaciente diagnostico;
    private HistoricoReacoesAlergiaPaciente historicoReacoes;
    private String observacoes;
}
