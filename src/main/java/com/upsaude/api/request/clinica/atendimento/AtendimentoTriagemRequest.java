package com.upsaude.api.request.clinica.atendimento;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.upsaude.api.request.embeddable.AnamneseAtendimentoRequest;
import com.upsaude.api.request.embeddable.ClassificacaoRiscoAtendimentoRequest;
import com.upsaude.api.request.embeddable.DiagnosticoAtendimentoRequest;
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
@Schema(description = "Dados de triagem para atendimento")
public class AtendimentoTriagemRequest {

    @Valid
    @Schema(description = "Dados de anamnese")
    private AnamneseAtendimentoRequest anamnese;

    @Valid
    @Schema(description = "Dados de classificação de risco")
    private ClassificacaoRiscoAtendimentoRequest classificacaoRisco;

    @Valid
    @Schema(description = "Dados de diagnóstico")
    private DiagnosticoAtendimentoRequest diagnostico;
}
