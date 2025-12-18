package com.upsaude.api.request.agendamento;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.PrioridadeAtendimentoEnum;
import com.upsaude.util.converter.PrioridadeAtendimentoEnumDeserializer;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de fila espera")
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
