package com.upsaude.api.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.TipoPlantaoEnum;
import com.upsaude.util.converter.TipoPlantaoEnumDeserializer;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlantaoRequest {
    private UUID estabelecimento;
    private UUID profissional;
    private UUID medico;
    private OffsetDateTime dataHoraInicio;
    private OffsetDateTime dataHoraFim;
    private OffsetDateTime dataHoraFimPrevisto;
    @JsonDeserialize(using = TipoPlantaoEnumDeserializer.class)
    private TipoPlantaoEnum tipoPlantao;
    private String setor;
    private Integer leitoInicio;
    private Integer leitoFim;
    private BigDecimal valorPlantao;
    private Boolean temHoraExtra;
    private BigDecimal valorHoraExtra;
    private Boolean confirmado;
    private OffsetDateTime dataConfirmacao;
    private Boolean cancelado;
    private OffsetDateTime dataCancelamento;
    private String motivoCancelamento;
    private String observacoes;
}
