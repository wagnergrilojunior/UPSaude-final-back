package com.upsaude.api.request.clinica.atendimento;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dados para criação de consulta médica")
public class ConsultaCreateRequest {

    @NotNull(message = "ID do paciente é obrigatório")
    @Schema(description = "ID do paciente", required = true)
    private UUID pacienteId;

    @Schema(description = "ID do médico")
    private UUID medicoId;

    @Schema(description = "ID do profissional de saúde")
    private UUID profissionalSaudeId;

    @Schema(description = "ID do atendimento (opcional)")
    private UUID atendimentoId;

    @Schema(description = "ID do convênio")
    private UUID convenioId;

    @Size(max = 50, message = "Tipo de consulta deve ter no máximo 50 caracteres")
    @Schema(description = "Tipo de consulta")
    private String tipoConsulta;

    @Size(max = 1000, message = "Motivo deve ter no máximo 1000 caracteres")
    @Schema(description = "Motivo da consulta")
    private String motivo;

    @Size(max = 255, message = "Local deve ter no máximo 255 caracteres")
    @Schema(description = "Local da consulta")
    private String local;
}

