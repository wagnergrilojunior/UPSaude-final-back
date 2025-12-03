package com.upsaude.api.request;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckInAtendimentoRequest {
    private UUID agendamento;
    private UUID atendimento;
    private UUID paciente;
    private OffsetDateTime dataCheckin;
    private OffsetDateTime dataCheckout;
    private String tipoCheckin;
    private Boolean ehPresencial;
    private OffsetDateTime horarioPrevisto;
    private Integer tempoAntecedenciaMinutos;
    private Boolean estaAtrasado;
    private Integer tempoAtrasoMinutos;
    private Double latitude;
    private Double longitude;
    private String enderecoIp;
    private String userAgent;
    private String observacoes;
    private Boolean acompanhantePresente;
    private Integer numeroAcompanhantes;
    private UUID checkinRealizadoPor;
    private UUID checkoutRealizadoPor;
}
