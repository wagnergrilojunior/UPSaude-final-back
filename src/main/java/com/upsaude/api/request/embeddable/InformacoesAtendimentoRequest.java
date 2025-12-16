package com.upsaude.api.request.embeddable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.StatusAtendimentoEnum;
import com.upsaude.enums.TipoAtendimentoEnum;
import com.upsaude.util.converter.StatusAtendimentoEnumDeserializer;
import com.upsaude.util.converter.TipoAtendimentoEnumDeserializer;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de informacoes atendimento")
public class InformacoesAtendimentoRequest {
    @NotNull(message = "Data e hora do atendimento são obrigatórias")
    private OffsetDateTime dataHora;

    private OffsetDateTime dataAgendamento;

    private OffsetDateTime dataInicio;

    private OffsetDateTime dataFim;

    private Integer duracaoMinutos;

    private Integer duracaoRealMinutos;

    @JsonDeserialize(using = TipoAtendimentoEnumDeserializer.class)
    private TipoAtendimentoEnum tipoAtendimento;

    @NotNull(message = "Status do atendimento é obrigatório")
    @Builder.Default
    @JsonDeserialize(using = StatusAtendimentoEnumDeserializer.class)
    private StatusAtendimentoEnum statusAtendimento = StatusAtendimentoEnum.AGENDADO;

    private String motivo;

    @Size(max = 255, message = "Local do atendimento deve ter no máximo 255 caracteres")
    private String localAtendimento;

    @Size(max = 50, message = "Número do atendimento deve ter no máximo 50 caracteres")
    private String numeroAtendimento;
}
