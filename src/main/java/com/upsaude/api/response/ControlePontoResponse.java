package com.upsaude.api.response;

import com.upsaude.enums.TipoPontoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Classe de resposta para ControlePonto.
 *
 * @author UPSaúde
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ControlePontoResponse {
    private UUID id;
    private UUID estabelecimentoId;
    private String estabelecimentoNome;
    private UUID profissionalId;
    private String profissionalNome;
    private UUID medicoId;
    private String medicoNome;
    private OffsetDateTime dataHora;
    private java.time.LocalDate dataPonto;
    private TipoPontoEnum tipoPonto;
    private String tipoPontoDescricao;
    private Double latitude;
    private Double longitude;
    private String enderecoIp;
    private String observacoes;
    private String justificativa;
    private Boolean aprovado;
    private UUID aprovadoPor;
    private String aprovadoPorNome;
    private OffsetDateTime dataAprovacao;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
}

