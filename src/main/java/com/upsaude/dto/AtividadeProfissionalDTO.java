package com.upsaude.dto;

import com.upsaude.enums.TipoAtividadeProfissionalEnum;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtividadeProfissionalDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private ProfissionaisSaudeDTO profissional;
    private MedicosDTO medico;
    private PacienteDTO paciente;
    private AtendimentoDTO atendimento;
    private CirurgiaDTO cirurgia;
    private OffsetDateTime dataHora;
    private LocalDate dataAtividade;
    private TipoAtividadeProfissionalEnum tipoAtividade;
    private String descricao;
    private Integer duracaoMinutos;
    private OffsetDateTime dataHoraInicio;
    private OffsetDateTime dataHoraFim;
    private String localRealizacao;
    private String setor;
    private Integer quantidadeAtendimentos;
    private String observacoes;
    private String observacoesInternas;
}
