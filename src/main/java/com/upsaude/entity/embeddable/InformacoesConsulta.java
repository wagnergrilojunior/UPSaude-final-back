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
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Classe embeddable para informações básicas da consulta.
 *
 * @author UPSaúde
 */
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InformacoesConsulta {

    @Column(name = "data_consulta", nullable = false)
    private OffsetDateTime dataConsulta; // Data e hora da consulta

    @Column(name = "data_agendamento")
    private OffsetDateTime dataAgendamento; // Data em que foi agendada

    @Column(name = "data_inicio")
    private OffsetDateTime dataInicio; // Data e hora de início real da consulta

    @Column(name = "data_fim")
    private OffsetDateTime dataFim; // Data e hora de término real da consulta

    @Column(name = "duracao_minutos")
    private Integer duracaoMinutos; // Duração prevista em minutos

    @Column(name = "duracao_real_minutos")
    private Integer duracaoRealMinutos; // Duração real em minutos

    @Convert(converter = TipoConsultaEnumConverter.class)
    @Column(name = "tipo_consulta")
    private TipoConsultaEnum tipoConsulta;

    @Convert(converter = StatusConsultaEnumConverter.class)
    @Column(name = "status_consulta", nullable = false)
    @Builder.Default
    private StatusConsultaEnum statusConsulta = StatusConsultaEnum.AGENDADA;

    @Column(name = "motivo", columnDefinition = "TEXT")
    private String motivo; // Motivo da consulta / queixa principal

    @Column(name = "local_atendimento", length = 255)
    private String localAtendimento; // Local onde foi realizada (ex: Consultório 1, Sala 2)

    @Column(name = "numero_consulta", length = 50)
    private String numeroConsulta; // Número da consulta (controle interno)
}

