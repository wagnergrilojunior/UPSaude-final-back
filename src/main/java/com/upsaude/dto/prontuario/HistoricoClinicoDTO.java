package com.upsaude.dto.prontuario;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoClinicoDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteDTO paciente;
    private ProfissionaisSaudeDTO profissional;
    private AtendimentoDTO atendimento;
    private AgendamentoDTO agendamento;
    private ExamesDTO exame;
    private ReceitasMedicasDTO receita;
    private CirurgiaDTO cirurgia;
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
