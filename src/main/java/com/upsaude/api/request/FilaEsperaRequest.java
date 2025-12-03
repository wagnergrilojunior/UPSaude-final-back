package com.upsaude.api.request;

import com.upsaude.enums.PrioridadeAtendimentoEnum;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilaEsperaRequest {
    private UUID paciente;
    private UUID profissional;
    private UUID medico;
    private UUID especialidade;
    private UUID agendamento;
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
