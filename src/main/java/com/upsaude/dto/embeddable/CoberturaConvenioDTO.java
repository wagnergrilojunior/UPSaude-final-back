package com.upsaude.dto.embeddable;

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
public class CoberturaConvenioDTO {
    private Boolean coberturaNacional;
    private Boolean coberturaRegional;
    private Boolean coberturaLocal;
    private Boolean coberturaEmergencia;
    private Boolean coberturaUrgencia;
    private Boolean coberturaInternacao;
    private Boolean coberturaAmbulatorial;
    private Boolean coberturaExames;
    private Boolean coberturaCirurgias;
    private Boolean coberturaMedicamentos;
    private Boolean coberturaOdontologia;
    private Boolean coberturaFisioterapia;
    private Boolean coberturaPsicologia;
    private Boolean coberturaNutricao;
    private Boolean coberturaFonoaudiologia;
    private Boolean coberturaTerapiaOcupacional;
    private Boolean coberturaTransplantes;
    private Boolean coberturaQuimioterapia;
    private Boolean coberturaRadioterapia;
    private Boolean coberturaHemodialise;
    private Boolean coberturaObstetricia;
    private Boolean coberturaPediatria;
    private Boolean coberturaGeriatria;
    private Boolean coberturaPsiquiatria;
    private String coberturaOutras;
}
