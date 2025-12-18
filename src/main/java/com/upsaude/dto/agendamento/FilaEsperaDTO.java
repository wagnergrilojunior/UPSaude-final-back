package com.upsaude.dto.agendamento;

import com.upsaude.enums.PrioridadeAtendimentoEnum;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import com.upsaude.dto.profissional.EspecialidadesMedicasDTO;
import com.upsaude.dto.estabelecimento.EstabelecimentosDTO;
import com.upsaude.dto.profissional.MedicosDTO;
import com.upsaude.dto.paciente.PacienteDTO;
import com.upsaude.dto.profissional.ProfissionaisSaudeDTO;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilaEsperaDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private EstabelecimentosDTO estabelecimento;
    private PacienteDTO paciente;
    private ProfissionaisSaudeDTO profissional;
    private MedicosDTO medico;
    private EspecialidadesMedicasDTO especialidade;
    private AgendamentoDTO agendamento;
    private OffsetDateTime dataEntrada;
    private LocalDate dataFimDesejada;
    private PrioridadeAtendimentoEnum prioridade;
    private Integer posicaoFila;
    private String motivo;
    private String observacoes;
    private Boolean notificado;
    private OffsetDateTime dataNotificacao;
    private Integer notificacoesEnviadas;
    private Boolean aceitaQualquerHorario;
    private String telefoneContato;
    private String emailContato;
}
