package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Embeddable
@Data
@Builder
@AllArgsConstructor
public class CoberturaConvenio {

    public CoberturaConvenio() {
        this.coberturaNacional = false;
        this.coberturaRegional = false;
        this.coberturaLocal = false;
        this.coberturaEmergencia = false;
        this.coberturaUrgencia = false;
        this.coberturaInternacao = false;
        this.coberturaAmbulatorial = false;
        this.coberturaExames = false;
        this.coberturaCirurgias = false;
        this.coberturaMedicamentos = false;
        this.coberturaOdontologia = false;
        this.coberturaFisioterapia = false;
        this.coberturaPsicologia = false;
        this.coberturaNutricao = false;
        this.coberturaFonoaudiologia = false;
        this.coberturaTerapiaOcupacional = false;
        this.coberturaTransplantes = false;
        this.coberturaQuimioterapia = false;
        this.coberturaRadioterapia = false;
        this.coberturaHemodialise = false;
        this.coberturaObstetricia = false;
        this.coberturaPediatria = false;
        this.coberturaGeriatria = false;
        this.coberturaPsiquiatria = false;
        this.coberturaOutras = "";
    }

    @Column(name = "cobertura_nacional", nullable = false)
    @Builder.Default
    private Boolean coberturaNacional = false;

    @Column(name = "cobertura_regional", nullable = false)
    @Builder.Default
    private Boolean coberturaRegional = false;

    @Column(name = "cobertura_local", nullable = false)
    @Builder.Default
    private Boolean coberturaLocal = false;

    @Column(name = "cobertura_emergencia", nullable = false)
    @Builder.Default
    private Boolean coberturaEmergencia = false;

    @Column(name = "cobertura_urgencia", nullable = false)
    @Builder.Default
    private Boolean coberturaUrgencia = false;

    @Column(name = "cobertura_internacao", nullable = false)
    @Builder.Default
    private Boolean coberturaInternacao = false;

    @Column(name = "cobertura_ambulatorial", nullable = false)
    @Builder.Default
    private Boolean coberturaAmbulatorial = false;

    @Column(name = "cobertura_exames", nullable = false)
    @Builder.Default
    private Boolean coberturaExames = false;

    @Column(name = "cobertura_cirurgias", nullable = false)
    @Builder.Default
    private Boolean coberturaCirurgias = false;

    @Column(name = "cobertura_medicamentos", nullable = false)
    @Builder.Default
    private Boolean coberturaMedicamentos = false;

    @Column(name = "cobertura_odontologia", nullable = false)
    @Builder.Default
    private Boolean coberturaOdontologia = false;

    @Column(name = "cobertura_fisioterapia", nullable = false)
    @Builder.Default
    private Boolean coberturaFisioterapia = false;

    @Column(name = "cobertura_psicologia", nullable = false)
    @Builder.Default
    private Boolean coberturaPsicologia = false;

    @Column(name = "cobertura_nutricao", nullable = false)
    @Builder.Default
    private Boolean coberturaNutricao = false;

    @Column(name = "cobertura_fonoaudiologia", nullable = false)
    @Builder.Default
    private Boolean coberturaFonoaudiologia = false;

    @Column(name = "cobertura_terapia_ocupacional", nullable = false)
    @Builder.Default
    private Boolean coberturaTerapiaOcupacional = false;

    @Column(name = "cobertura_transplantes", nullable = false)
    @Builder.Default
    private Boolean coberturaTransplantes = false;

    @Column(name = "cobertura_quimioterapia", nullable = false)
    @Builder.Default
    private Boolean coberturaQuimioterapia = false;

    @Column(name = "cobertura_radioterapia", nullable = false)
    @Builder.Default
    private Boolean coberturaRadioterapia = false;

    @Column(name = "cobertura_hemodialise", nullable = false)
    @Builder.Default
    private Boolean coberturaHemodialise = false;

    @Column(name = "cobertura_obstetricia", nullable = false)
    @Builder.Default
    private Boolean coberturaObstetricia = false;

    @Column(name = "cobertura_pediatria", nullable = false)
    @Builder.Default
    private Boolean coberturaPediatria = false;

    @Column(name = "cobertura_geriatria", nullable = false)
    @Builder.Default
    private Boolean coberturaGeriatria = false;

    @Column(name = "cobertura_psiquiatria", nullable = false)
    @Builder.Default
    private Boolean coberturaPsiquiatria = false;

    @Column(name = "cobertura_outras", columnDefinition = "TEXT")
    private String coberturaOutras;
}
