package com.upsaude.entity.embeddable;

import com.upsaude.enums.StatusConsultaEnum;
import com.upsaude.enums.TipoConsultaEnum;
import com.upsaude.util.converter.StatusConsultaEnumConverter;
import com.upsaude.util.converter.TipoConsultaEnumConverter;
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
public class InformacoesConsulta {

    public InformacoesConsulta() {
        this.motivo = "";
        this.localAtendimento = "";
        this.numeroConsulta = "";
    }

    @Column(name = "data_consulta", nullable = false)
    private OffsetDateTime dataConsulta;

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

    @Convert(converter = TipoConsultaEnumConverter.class)
    @Column(name = "tipo_consulta")
    private TipoConsultaEnum tipoConsulta;

    @Convert(converter = StatusConsultaEnumConverter.class)
    @Column(name = "status_consulta", nullable = false)
    @Builder.Default
    private StatusConsultaEnum statusConsulta = StatusConsultaEnum.AGENDADA;

    @Column(name = "motivo", columnDefinition = "TEXT")
    private String motivo;

    @Column(name = "local_atendimento", length = 255)
    private String localAtendimento;

    @Column(name = "numero_consulta", length = 50)
    private String numeroConsulta;
}
