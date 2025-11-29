package com.upsaude.dto;

import com.upsaude.enums.TipoPontoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * DTO (Data Transfer Object) para ControlePonto.
 *
 * @author UPSaúde
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ControlePontoDTO {
    private UUID id;
    private UUID estabelecimentoId;
    private UUID profissionalId;
    private UUID medicoId;
    private OffsetDateTime dataHora;
    private java.time.LocalDate dataPonto;
    private TipoPontoEnum tipoPonto;
    private Double latitude;
    private Double longitude;
    private String enderecoIp;
    private String observacoes;
    private String justificativa;
    private Boolean aprovado;
    private UUID aprovadoPor;
    private OffsetDateTime dataAprovacao;
    private Boolean active;
}

