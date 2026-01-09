package com.upsaude.api.response.agendamento;
import com.upsaude.api.response.clinica.atendimento.PacienteAtendimentoResponse;
import com.upsaude.api.response.clinica.atendimento.ProfissionalAtendimentoResponse;
import com.upsaude.api.response.clinica.atendimento.MedicoConsultaResponse;

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
    private EstabelecimentoSimplificadoResponse estabelecimento;
    private PacienteAtendimentoResponse paciente;
    private ProfissionalAtendimentoResponse profissional;
    private MedicoConsultaResponse medico;
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
