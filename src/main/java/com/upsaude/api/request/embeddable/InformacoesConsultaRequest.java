package com.upsaude.api.request.embeddable;

import com.upsaude.enums.StatusConsultaEnum;
import com.upsaude.enums.TipoConsultaEnum;
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
    
    private TipoConsultaEnum tipoConsulta;
    
    @NotNull(message = "Status da consulta é obrigatório")
    @Builder.Default
    private StatusConsultaEnum statusConsulta = StatusConsultaEnum.AGENDADA;
    
    private String motivo;
    
    @Size(max = 255, message = "Local do atendimento deve ter no máximo 255 caracteres")
    private String localAtendimento;
    
    @Size(max = 50, message = "Número da consulta deve ter no máximo 50 caracteres")
    private String numeroConsulta;
}
