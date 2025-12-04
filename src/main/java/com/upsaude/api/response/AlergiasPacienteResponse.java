package com.upsaude.api.response;

import com.upsaude.entity.embeddable.DiagnosticoAlergiaPaciente;
import com.upsaude.entity.embeddable.HistoricoReacoesAlergiaPaciente;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlergiasPacienteResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteResponse paciente;
    private AlergiasResponse alergia;
    private DiagnosticoAlergiaPaciente diagnostico;
    private HistoricoReacoesAlergiaPaciente historicoReacoes;
    private String observacoes;
    private Boolean alertaMedico;
}
