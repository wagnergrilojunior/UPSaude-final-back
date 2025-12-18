package com.upsaude.api.response.prontuario;
import com.upsaude.api.response.cirurgia.CirurgiaResponse;
import com.upsaude.api.response.profissional.ProfissionaisSaudeResponse;
import com.upsaude.api.response.agendamento.AgendamentoResponse;
import com.upsaude.api.response.atendimento.AtendimentoResponse;
import com.upsaude.api.response.paciente.PacienteResponse;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoClinicoResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteResponse paciente;
    private ProfissionaisSaudeResponse profissional;
    private AtendimentoResponse atendimento;
    private AgendamentoResponse agendamento;
    private ExamesResponse exame;
    private ReceitasMedicasResponse receita;
    private CirurgiaResponse cirurgia;
    private OffsetDateTime dataRegistro;
    private String tipoRegistro;
    private String titulo;
    private String descricao;
    private String observacoes;
    private String observacoesInternas;
    private String tags;
    private UUID registradoPor;
    private UUID revisadoPor;
    private OffsetDateTime dataRevisao;
    private Integer versao;
    private Boolean visivelParaPaciente;
    private Boolean compartilhadoOutrosEstabelecimentos;
}
