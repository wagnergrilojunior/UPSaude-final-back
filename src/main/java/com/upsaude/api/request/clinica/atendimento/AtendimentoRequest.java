package com.upsaude.api.request.clinica.atendimento;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.upsaude.api.request.embeddable.AnamneseAtendimentoRequest;
import com.upsaude.api.request.embeddable.ClassificacaoRiscoAtendimentoRequest;
import com.upsaude.api.request.embeddable.DiagnosticoAtendimentoRequest;
import com.upsaude.api.request.embeddable.InformacoesAtendimentoRequest;
import com.upsaude.api.request.embeddable.ProcedimentosRealizadosAtendimentoRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dados de atendimento")
public class AtendimentoRequest {

    @NotNull(message = "Paciente é obrigatório")
    private UUID paciente;

    @NotNull(message = "Profissional de saúde é obrigatório")
    private UUID profissional;

    private UUID especialidade;
    private UUID equipeSaude;
    private UUID convenio;

    @Valid
    private InformacoesAtendimentoRequest informacoes;

    @Valid
    private AnamneseAtendimentoRequest anamnese;

    @Valid
    private DiagnosticoAtendimentoRequest diagnostico;

    @Valid
    private ProcedimentosRealizadosAtendimentoRequest procedimentosRealizados;

    @Valid
    private ClassificacaoRiscoAtendimentoRequest classificacaoRisco;

    @Size(max = 1000, message = "Anotações deve ter no máximo 1000 caracteres")
    private String anotacoes;

    @Size(max = 1000, message = "Observações internas deve ter no máximo 1000 caracteres")
    private String observacoesInternas;

    // Campos financeiros
    private UUID competenciaFinanceira;

    @Valid
    private List<AtendimentoProcedimentoRequest> procedimentos;
}
