package com.upsaude.entity.embeddable;

import com.upsaude.enums.StatusAtendimentoEnum;
import com.upsaude.enums.TipoAtendimentoEnum;
import com.upsaude.util.converter.StatusAtendimentoEnumConverter;
import com.upsaude.util.converter.TipoAtendimentoEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

/**
 * Classe embeddable para informações básicas do atendimento.
 *
 * @author UPSaúde
 */
@Embeddable
@Data
@Builder
@AllArgsConstructor
public class InformacoesAtendimento {

    public InformacoesAtendimento() {
        this.motivo = "";
        this.localAtendimento = "";
        this.numeroAtendimento = "";
    }

    @Column(name = "data_hora", nullable = false)
    @NotNull(message = "Data e hora do atendimento são obrigatórias")
    private OffsetDateTime dataHora; // Data e hora do atendimento

    @Column(name = "data_agendamento")
    private OffsetDateTime dataAgendamento; // Data em que foi agendado

    @Column(name = "data_inicio")
    private OffsetDateTime dataInicio; // Data e hora de início real do atendimento

    @Column(name = "data_fim")
    private OffsetDateTime dataFim; // Data e hora de término real do atendimento

    @Column(name = "duracao_minutos")
    private Integer duracaoMinutos; // Duração prevista em minutos

    @Column(name = "duracao_real_minutos")
    private Integer duracaoRealMinutos; // Duração real em minutos

    @Convert(converter = TipoAtendimentoEnumConverter.class)
    @Column(name = "tipo_atendimento")
    private TipoAtendimentoEnum tipoAtendimento;

    @Convert(converter = StatusAtendimentoEnumConverter.class)
    @Column(name = "status_atendimento", nullable = false)
    @Builder.Default
    private StatusAtendimentoEnum statusAtendimento = StatusAtendimentoEnum.AGENDADO;

    @Column(name = "motivo", columnDefinition = "TEXT")
    private String motivo; // Motivo do atendimento / queixa principal

    @Column(name = "local_atendimento", length = 255)
    private String localAtendimento; // Local onde foi realizado (ex: Sala 1, Consultório 2, Domicílio)

    @Column(name = "numero_atendimento", length = 50)
    private String numeroAtendimento; // Número do atendimento (controle interno)
}

