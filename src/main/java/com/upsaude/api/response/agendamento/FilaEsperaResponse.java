package com.upsaude.api.response.agendamento;
import com.upsaude.api.response.estabelecimento.EstabelecimentosResponse;
import com.upsaude.api.response.profissional.EspecialidadesMedicasResponse;
import com.upsaude.api.response.profissional.MedicosResponse;
import com.upsaude.api.response.profissional.ProfissionaisSaudeResponse;
import com.upsaude.api.response.agendamento.AgendamentoResponse;
import com.upsaude.api.response.paciente.PacienteResponse;

import com.upsaude.enums.PrioridadeAtendimentoEnum;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilaEsperaResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private EstabelecimentosResponse estabelecimento;
    private PacienteResponse paciente;
    private ProfissionaisSaudeResponse profissional;
    private MedicosResponse medico;
    private EspecialidadesMedicasResponse especialidade;
    private AgendamentoResponse agendamento;
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
