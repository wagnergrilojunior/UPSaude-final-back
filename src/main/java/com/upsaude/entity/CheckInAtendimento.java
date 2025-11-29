package com.upsaude.entity;

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

/**
 * Entidade que representa o check-in/check-out de um atendimento.
 * Registra a entrada e saída do paciente no atendimento.
 *
 * @author UPSaúde
 */
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

    // ========== RELACIONAMENTOS ==========

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agendamento_id", nullable = false)
    @NotNull(message = "Agendamento é obrigatório")
    private Agendamento agendamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atendimento_id")
    private Atendimento atendimento; // Link para atendimento quando iniciado

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    @NotNull(message = "Paciente é obrigatório")
    private Paciente paciente;

    // ========== DADOS DO CHECK-IN ==========

    @Column(name = "data_checkin", nullable = false)
    @NotNull(message = "Data do check-in é obrigatória")
    private OffsetDateTime dataCheckin; // Quando fez check-in

    @Column(name = "data_checkout")
    private OffsetDateTime dataCheckout; // Quando fez check-out

    @Column(name = "tipo_checkin", nullable = false, length = 50)
    @NotNull(message = "Tipo de check-in é obrigatório")
    private String tipoCheckin; // PRESENCIAL, ONLINE, AUTOMATICO

    @Column(name = "eh_presencial")
    private Boolean ehPresencial; // Se compareceu presencialmente

    // ========== CONTROLE DE ATRASO ==========

    @Column(name = "horario_previsto")
    private OffsetDateTime horarioPrevisto; // Horário previsto do agendamento

    @Column(name = "tempo_antecedencia_minutos")
    private Integer tempoAntecedenciaMinutos; // Tempo de antecedência (positivo) ou atraso (negativo) em minutos

    @Column(name = "esta_atrasado")
    private Boolean estaAtrasado; // Se chegou atrasado

    @Column(name = "tempo_atraso_minutos")
    private Integer tempoAtrasoMinutos; // Tempo de atraso em minutos (se atrasado)

    // ========== LOCALIZAÇÃO ==========

    @Column(name = "latitude")
    private Double latitude; // Para validação de geolocalização

    @Column(name = "longitude")
    private Double longitude; // Para validação de geolocalização

    @Column(name = "endereco_ip", length = 45)
    private String enderecoIp; // IP do check-in (para online)

    @Column(name = "user_agent", length = 500)
    private String userAgent; // User agent do navegador (para online)

    // ========== INFORMAÇÕES ==========

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "acompanhante_presente")
    private Boolean acompanhantePresente; // Se veio com acompanhante

    @Column(name = "numero_acompanhantes")
    private Integer numeroAcompanhantes; // Número de acompanhantes

    // ========== AUDITORIA ==========

    @Column(name = "checkin_realizado_por")
    private java.util.UUID checkinRealizadoPor; // ID do usuário que realizou check-in (se não foi o paciente)

    @Column(name = "checkout_realizado_por")
    private java.util.UUID checkoutRealizadoPor; // ID do usuário que realizou check-out (se não foi o paciente)
}

