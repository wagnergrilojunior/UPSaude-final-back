package com.upsaude.api.request.embeddable;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoberturaConvenioRequest {
    @NotNull(message = "Cobertura nacional é obrigatória")
    @Builder.Default
    private Boolean coberturaNacional = false;

    @NotNull(message = "Cobertura regional é obrigatória")
    @Builder.Default
    private Boolean coberturaRegional = false;

    @NotNull(message = "Cobertura local é obrigatória")
    @Builder.Default
    private Boolean coberturaLocal = false;

    @NotNull(message = "Cobertura emergência é obrigatória")
    @Builder.Default
    private Boolean coberturaEmergencia = false;

    @NotNull(message = "Cobertura urgência é obrigatória")
    @Builder.Default
    private Boolean coberturaUrgencia = false;

    @NotNull(message = "Cobertura internação é obrigatória")
    @Builder.Default
    private Boolean coberturaInternacao = false;

    @NotNull(message = "Cobertura ambulatorial é obrigatória")
    @Builder.Default
    private Boolean coberturaAmbulatorial = false;

    @NotNull(message = "Cobertura exames é obrigatória")
    @Builder.Default
    private Boolean coberturaExames = false;

    @NotNull(message = "Cobertura cirurgias é obrigatória")
    @Builder.Default
    private Boolean coberturaCirurgias = false;

    @NotNull(message = "Cobertura medicamentos é obrigatória")
    @Builder.Default
    private Boolean coberturaMedicamentos = false;

    @NotNull(message = "Cobertura odontologia é obrigatória")
    @Builder.Default
    private Boolean coberturaOdontologia = false;

    @NotNull(message = "Cobertura fisioterapia é obrigatória")
    @Builder.Default
    private Boolean coberturaFisioterapia = false;

    @NotNull(message = "Cobertura psicologia é obrigatória")
    @Builder.Default
    private Boolean coberturaPsicologia = false;

    @NotNull(message = "Cobertura nutrição é obrigatória")
    @Builder.Default
    private Boolean coberturaNutricao = false;

    @NotNull(message = "Cobertura fonoaudiologia é obrigatória")
    @Builder.Default
    private Boolean coberturaFonoaudiologia = false;

    @NotNull(message = "Cobertura terapia ocupacional é obrigatória")
    @Builder.Default
    private Boolean coberturaTerapiaOcupacional = false;

    @NotNull(message = "Cobertura transplantes é obrigatória")
    @Builder.Default
    private Boolean coberturaTransplantes = false;

    @NotNull(message = "Cobertura quimioterapia é obrigatória")
    @Builder.Default
    private Boolean coberturaQuimioterapia = false;

    @NotNull(message = "Cobertura radioterapia é obrigatória")
    @Builder.Default
    private Boolean coberturaRadioterapia = false;

    @NotNull(message = "Cobertura hemodiálise é obrigatória")
    @Builder.Default
    private Boolean coberturaHemodialise = false;

    @NotNull(message = "Cobertura obstetrícia é obrigatória")
    @Builder.Default
    private Boolean coberturaObstetricia = false;

    @NotNull(message = "Cobertura pediatria é obrigatória")
    @Builder.Default
    private Boolean coberturaPediatria = false;

    @NotNull(message = "Cobertura geriatria é obrigatória")
    @Builder.Default
    private Boolean coberturaGeriatria = false;

    @NotNull(message = "Cobertura psiquiatria é obrigatória")
    @Builder.Default
    private Boolean coberturaPsiquiatria = false;

    private String coberturaOutras;
}
