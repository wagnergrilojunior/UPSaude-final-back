package com.upsaude.api.request.atendimento;

import com.upsaude.entity.agendamento.Agendamento;

import com.upsaude.entity.paciente.Paciente;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de check-in atendimento")
public class CheckInAtendimentoRequest {
    @NotNull(message = "Agendamento é obrigatório")
    private UUID agendamento;

    private UUID atendimento;

    @NotNull(message = "Paciente é obrigatório")
    private UUID paciente;

    @NotNull(message = "Data do check-in é obrigatória")
    private OffsetDateTime dataCheckin;
    private OffsetDateTime dataCheckout;
    @NotNull(message = "Tipo de check-in é obrigatório")
    @Size(max = 50, message = "Tipo check-in deve ter no máximo 50 caracteres")
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
