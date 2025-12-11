package com.upsaude.api.request.embeddable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.StatusConsultaEnum;
import com.upsaude.enums.TipoConsultaEnum;
import com.upsaude.util.converter.StatusConsultaEnumDeserializer;
import com.upsaude.util.converter.TipoConsultaEnumDeserializer;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InformacoesConsultaRequest {
    @NotNull(message = "Data da consulta é obrigatória")
    private OffsetDateTime dataConsulta;
    
    private OffsetDateTime dataAgendamento;
    
    private OffsetDateTime dataInicio;
    
    private OffsetDateTime dataFim;
    
    private Integer duracaoMinutos;
    
    private Integer duracaoRealMinutos;
    
    @JsonDeserialize(using = TipoConsultaEnumDeserializer.class)
    private TipoConsultaEnum tipoConsulta;
    
    @NotNull(message = "Status da consulta é obrigatório")
    @Builder.Default
    @JsonDeserialize(using = StatusConsultaEnumDeserializer.class)
    private StatusConsultaEnum statusConsulta = StatusConsultaEnum.AGENDADA;
    
    private String motivo;
    
    @Size(max = 255, message = "Local do atendimento deve ter no máximo 255 caracteres")
    private String localAtendimento;
    
    @Size(max = 50, message = "Número da consulta deve ter no máximo 50 caracteres")
    private String numeroConsulta;
}
