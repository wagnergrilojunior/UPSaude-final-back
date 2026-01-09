package com.upsaude.api.request.agendamento;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.PrioridadeAtendimentoEnum;
import com.upsaude.util.converter.PrioridadeAtendimentoEnumDeserializer;
import com.upsaude.validation.annotation.EmailValido;
import com.upsaude.validation.annotation.TelefoneValido;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de fila espera")
public class FilaEsperaRequest {
    private UUID estabelecimento;

    @NotNull(message = "Paciente é obrigatório")
    private UUID paciente;

    private UUID profissional;
    private UUID medico;
    private UUID agendamento;

    @NotNull(message = "Data de entrada na fila é obrigatória")
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
    @TelefoneValido
    private String telefoneContato;
    @EmailValido
    private String emailContato;
}
