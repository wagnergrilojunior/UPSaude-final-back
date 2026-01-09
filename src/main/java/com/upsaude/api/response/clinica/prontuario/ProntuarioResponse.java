package com.upsaude.api.response.clinica.prontuario;
import com.upsaude.api.response.clinica.atendimento.PacienteAtendimentoResponse;
import com.upsaude.api.response.clinica.atendimento.ProfissionalAtendimentoResponse;

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
    private PacienteAtendimentoResponse paciente;
    private ProfissionalAtendimentoResponse profissionalCriador;
    private OffsetDateTime dataAbertura;
    private List<AlergiaPacienteResponse> alergias;
    private List<VacinacaoPacienteResponse> vacinacoes;
    private List<ExamePacienteResponse> exames;
    private List<DoencaPacienteResponse> doencas;
}

