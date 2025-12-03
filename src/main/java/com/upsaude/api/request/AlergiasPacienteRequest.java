package com.upsaude.api.request;

import com.upsaude.entity.embeddable.DiagnosticoAlergiaPaciente;
import com.upsaude.entity.embeddable.HistoricoReacoesAlergiaPaciente;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlergiasPacienteRequest {
    private UUID paciente;
    private UUID alergia;
    private DiagnosticoAlergiaPaciente diagnostico;
    private HistoricoReacoesAlergiaPaciente historicoReacoes;
    private String observacoes;
}
