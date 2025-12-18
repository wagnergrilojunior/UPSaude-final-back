package com.upsaude.api.response.clinica.atendimento;
import com.upsaude.api.response.agendamento.AgendamentoResponse;
import com.upsaude.api.response.paciente.PacienteResponse;

import java.time.OffsetDateTime;
import java.util.UUID;
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
public class CheckInAtendimentoResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private AgendamentoResponse agendamento;
    private AtendimentoResponse atendimento;
    private PacienteResponse paciente;
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
