package com.upsaude.api.request;

import com.upsaude.enums.TipoPontoEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Classe de requisição para criação e atualização de ControlePonto.
 *
 * @author UPSaúde
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ControlePontoRequest {

    @NotNull(message = "ID do estabelecimento é obrigatório")
    private UUID estabelecimentoId;

    private UUID profissionalId;
    private UUID medicoId;

    @NotNull(message = "Data e hora do ponto são obrigatórias")
    private OffsetDateTime dataHora;

    @NotNull(message = "Data do ponto é obrigatória")
    private java.time.LocalDate dataPonto;

    @NotNull(message = "Tipo de ponto é obrigatório")
    private TipoPontoEnum tipoPonto;

    private Double latitude;
    private Double longitude;
    private String enderecoIp;
    private String observacoes;
    private String justificativa;
    private Boolean aprovado;
    private UUID aprovadoPor;
}

