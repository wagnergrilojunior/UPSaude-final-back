package com.upsaude.api.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.PrioridadeAtendimentoEnum;
import com.upsaude.util.converter.PrioridadeAtendimentoEnumDeserializer;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilaEsperaRequest {
    private UUID estabelecimento;
    private UUID paciente;
    private UUID profissional;
    private UUID medico;
    private UUID especialidade;
    private UUID agendamento;
    private OffsetDateTime dataEntrada;
    private LocalDate dataFimDesejada;
    @JsonDeserialize(using = PrioridadeAtendimentoEnumDeserializer.class)
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
