package com.upsaude.entity.clinica.atendimento;
import com.upsaude.entity.BaseEntity;

import com.upsaude.entity.agendamento.Agendamento;

import com.upsaude.entity.clinica.atendimento.Atendimento;

import com.upsaude.entity.paciente.Paciente;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;

@Entity
@Table(name = "checkin_atendimento", schema = "public",
       indexes = {
           @Index(name = "idx_checkin_agendamento", columnList = "agendamento_id"),
           @Index(name = "idx_checkin_atendimento", columnList = "atendimento_id"),
           @Index(name = "idx_checkin_data_checkin", columnList = "data_checkin"),
           @Index(name = "idx_checkin_paciente", columnList = "paciente_id"),
           @Index(name = "idx_checkin_estabelecimento", columnList = "estabelecimento_id")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class CheckInAtendimento extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agendamento_id", nullable = false)
    @NotNull(message = "Agendamento é obrigatório")
    private Agendamento agendamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atendimento_id")
    private Atendimento atendimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    @NotNull(message = "Paciente é obrigatório")
    private Paciente paciente;

    @Column(name = "data_checkin", nullable = false)
    @NotNull(message = "Data do check-in é obrigatória")
    private OffsetDateTime dataCheckin;

    @Column(name = "data_checkout")
    private OffsetDateTime dataCheckout;

    @Column(name = "tipo_checkin", nullable = false, length = 50)
    @NotNull(message = "Tipo de check-in é obrigatório")
    private String tipoCheckin;

    @Column(name = "eh_presencial")
    private Boolean ehPresencial;

    @Column(name = "horario_previsto")
    private OffsetDateTime horarioPrevisto;

    @Column(name = "tempo_antecedencia_minutos")
    private Integer tempoAntecedenciaMinutos;

    @Column(name = "esta_atrasado")
    private Boolean estaAtrasado;

    @Column(name = "tempo_atraso_minutos")
    private Integer tempoAtrasoMinutos;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "endereco_ip", length = 45)
    private String enderecoIp;

    @Column(name = "user_agent", length = 500)
    private String userAgent;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "acompanhante_presente")
    private Boolean acompanhantePresente;

    @Column(name = "numero_acompanhantes")
    private Integer numeroAcompanhantes;

    @Column(name = "checkin_realizado_por")
    private java.util.UUID checkinRealizadoPor;

    @Column(name = "checkout_realizado_por")
    private java.util.UUID checkoutRealizadoPor;
}
