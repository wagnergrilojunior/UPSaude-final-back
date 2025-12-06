package com.upsaude.dto.embeddable;

import com.upsaude.enums.StatusAtendimentoEnum;
import com.upsaude.enums.TipoAtendimentoEnum;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InformacoesAtendimentoDTO {
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
