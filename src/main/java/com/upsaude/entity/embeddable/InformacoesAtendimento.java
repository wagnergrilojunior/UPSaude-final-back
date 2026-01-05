package com.upsaude.entity.embeddable;

import com.upsaude.enums.StatusAtendimentoEnum;
import com.upsaude.enums.TipoAtendimentoEnum;
import com.upsaude.util.converter.StatusAtendimentoEnumConverter;
import com.upsaude.util.converter.TipoAtendimentoEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

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
    private OffsetDateTime dataHora;

    @Column(name = "data_execucao")
    private OffsetDateTime dataExecucao;

    @Column(name = "cnes_executor", length = 7)
    private String cnesExecutor;

    @Column(name = "data_agendamento")
    private OffsetDateTime dataAgendamento;

    @Column(name = "data_inicio")
    private OffsetDateTime dataInicio;

    @Column(name = "data_fim")
    private OffsetDateTime dataFim;

    @Column(name = "duracao_minutos")
    private Integer duracaoMinutos;

    @Column(name = "duracao_real_minutos")
    private Integer duracaoRealMinutos;

    @Convert(converter = TipoAtendimentoEnumConverter.class)
    @Column(name = "tipo_atendimento")
    private TipoAtendimentoEnum tipoAtendimento;

    @Convert(converter = StatusAtendimentoEnumConverter.class)
    @Column(name = "status_atendimento", nullable = false)
    @Builder.Default
    private StatusAtendimentoEnum statusAtendimento = StatusAtendimentoEnum.AGENDADO;

    @Column(name = "motivo", columnDefinition = "TEXT")
    private String motivo;

    @Column(name = "local_atendimento", length = 255)
    private String localAtendimento;

    @Column(name = "numero_atendimento", length = 50)
    private String numeroAtendimento;
}
