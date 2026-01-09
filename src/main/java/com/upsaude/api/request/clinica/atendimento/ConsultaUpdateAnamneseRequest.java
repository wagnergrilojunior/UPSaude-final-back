package com.upsaude.api.request.clinica.atendimento;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.upsaude.api.request.embeddable.AnamneseConsultaRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dados de anamnese para atualização de consulta")
public class ConsultaUpdateAnamneseRequest {

    @Valid
    @Schema(description = "Dados de anamnese", required = true)
    private AnamneseConsultaRequest anamnese;
}
