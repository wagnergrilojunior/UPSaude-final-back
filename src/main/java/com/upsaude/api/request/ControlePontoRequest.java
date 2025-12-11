package com.upsaude.api.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.TipoPontoEnum;
import com.upsaude.util.converter.TipoPontoEnumDeserializer;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ControlePontoRequest {
    private UUID profissional;
    private UUID medico;
    private OffsetDateTime dataHora;
    private LocalDate dataPonto;
    @JsonDeserialize(using = TipoPontoEnumDeserializer.class)
    private TipoPontoEnum tipoPonto;
    private Double latitude;
    private Double longitude;
    private String enderecoIp;
    private String observacoes;
    private String justificativa;
    private Boolean aprovado;
    private UUID aprovadoPor;
    private OffsetDateTime dataAprovacao;
}
