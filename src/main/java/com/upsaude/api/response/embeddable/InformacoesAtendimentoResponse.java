package com.upsaude.api.response.embeddable;

import com.upsaude.enums.StatusAtendimentoEnum;
import com.upsaude.enums.TipoAtendimentoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InformacoesAtendimentoResponse {
    private OffsetDateTime dataHora;
    private OffsetDateTime dataAgendamento;
    private OffsetDateTime dataInicio;
    private OffsetDateTime dataFim;
    private Integer duracaoMinutos;
    private Integer duracaoRealMinutos;
    private TipoAtendimentoEnum tipoAtendimento;
    private StatusAtendimentoEnum statusAtendimento;
    private String motivo;
    private String localAtendimento;
    private String numeroAtendimento;
}
