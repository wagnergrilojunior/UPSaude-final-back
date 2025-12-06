package com.upsaude.dto.embeddable;

import com.upsaude.enums.StatusConsultaEnum;
import com.upsaude.enums.TipoConsultaEnum;
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
public class InformacoesConsultaDTO {
    private OffsetDateTime dataConsulta;
    private OffsetDateTime dataAgendamento;
    private OffsetDateTime dataInicio;
    private OffsetDateTime dataFim;
    private Integer duracaoMinutos;
    private Integer duracaoRealMinutos;
    private TipoConsultaEnum tipoConsulta;
    private StatusConsultaEnum statusConsulta;
    private String motivo;
    private String localAtendimento;
    private String numeroConsulta;
}
