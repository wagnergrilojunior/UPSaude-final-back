package com.upsaude.api.response.alergia;

import com.upsaude.api.response.embeddable.DiagnosticoAlergiaPacienteResponse;
import com.upsaude.api.response.embeddable.HistoricoReacoesAlergiaPacienteResponse;
import java.time.OffsetDateTime;
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
public class AlergiasPacienteResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteResponse paciente;
    private AlergiasResponse alergia;
    private DiagnosticoAlergiaPacienteResponse diagnostico;
    private HistoricoReacoesAlergiaPacienteResponse historicoReacoes;
    private String observacoes;
    private Boolean alertaMedico;
}
