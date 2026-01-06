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
@Schema(description = "Dados para criação de atendimento")
public class AtendimentoCreateRequest {

    @NotNull(message = "ID do paciente é obrigatório")
    @Schema(description = "ID do paciente", required = true)
    private UUID pacienteId;

    @NotNull(message = "ID do profissional é obrigatório")
    @Schema(description = "ID do profissional de saúde", required = true)
    private UUID profissionalId;

    @Schema(description = "ID da equipe de saúde")
    private UUID equipeSaudeId;

    @Schema(description = "ID do convênio")
    private UUID convenioId;

    @Size(max = 50, message = "Tipo de atendimento deve ter no máximo 50 caracteres")
    @Schema(description = "Tipo de atendimento")
    private String tipoAtendimento;

    @Size(max = 1000, message = "Motivo deve ter no máximo 1000 caracteres")
    @Schema(description = "Motivo do atendimento")
    private String motivo;

    @Size(max = 255, message = "Local de atendimento deve ter no máximo 255 caracteres")
    @Schema(description = "Local de atendimento")
    private String localAtendimento;
}
