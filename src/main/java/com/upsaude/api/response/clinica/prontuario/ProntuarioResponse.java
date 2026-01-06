package com.upsaude.api.response.clinica.prontuario;
import com.upsaude.api.response.paciente.PacienteResponse;
import com.upsaude.api.response.profissional.ProfissionaisSaudeResponse;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProntuarioResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteResponse paciente;
    private ProfissionaisSaudeResponse profissionalCriador;
    private OffsetDateTime dataAbertura;
    private List<AlergiaPacienteResponse> alergias;
    private List<VacinacaoPacienteResponse> vacinacoes;
    private List<ExamePacienteResponse> exames;
    private List<DoencaPacienteResponse> doencas;
}

