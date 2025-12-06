package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Classe embeddable para informações de cobertura e rede credenciada do convênio.
 *
 * @author UPSaúde
 */
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
    private Boolean coberturaNacional = false; // Se tem cobertura nacional

    @Column(name = "cobertura_regional", nullable = false)
    @Builder.Default
    private Boolean coberturaRegional = false; // Se tem cobertura regional

    @Column(name = "cobertura_local", nullable = false)
    @Builder.Default
    private Boolean coberturaLocal = false; // Se tem cobertura local

    @Column(name = "cobertura_emergencia", nullable = false)
    @Builder.Default
    private Boolean coberturaEmergencia = false; // Se cobre emergências

    @Column(name = "cobertura_urgencia", nullable = false)
    @Builder.Default
    private Boolean coberturaUrgencia = false; // Se cobre urgências

    @Column(name = "cobertura_internacao", nullable = false)
    @Builder.Default
    private Boolean coberturaInternacao = false; // Se cobre internações

    @Column(name = "cobertura_ambulatorial", nullable = false)
    @Builder.Default
    private Boolean coberturaAmbulatorial = false; // Se cobre atendimento ambulatorial

    @Column(name = "cobertura_exames", nullable = false)
    @Builder.Default
    private Boolean coberturaExames = false; // Se cobre exames

    @Column(name = "cobertura_cirurgias", nullable = false)
    @Builder.Default
    private Boolean coberturaCirurgias = false; // Se cobre cirurgias

    @Column(name = "cobertura_medicamentos", nullable = false)
    @Builder.Default
    private Boolean coberturaMedicamentos = false; // Se cobre medicamentos

    @Column(name = "cobertura_odontologia", nullable = false)
    @Builder.Default
    private Boolean coberturaOdontologia = false; // Se cobre odontologia

    @Column(name = "cobertura_fisioterapia", nullable = false)
    @Builder.Default
    private Boolean coberturaFisioterapia = false; // Se cobre fisioterapia

    @Column(name = "cobertura_psicologia", nullable = false)
    @Builder.Default
    private Boolean coberturaPsicologia = false; // Se cobre psicologia

    @Column(name = "cobertura_nutricao", nullable = false)
    @Builder.Default
    private Boolean coberturaNutricao = false; // Se cobre nutrição

    @Column(name = "cobertura_fonoaudiologia", nullable = false)
    @Builder.Default
    private Boolean coberturaFonoaudiologia = false; // Se cobre fonoaudiologia

    @Column(name = "cobertura_terapia_ocupacional", nullable = false)
    @Builder.Default
    private Boolean coberturaTerapiaOcupacional = false; // Se cobre terapia ocupacional

    @Column(name = "cobertura_transplantes", nullable = false)
    @Builder.Default
    private Boolean coberturaTransplantes = false; // Se cobre transplantes

    @Column(name = "cobertura_quimioterapia", nullable = false)
    @Builder.Default
    private Boolean coberturaQuimioterapia = false; // Se cobre quimioterapia

    @Column(name = "cobertura_radioterapia", nullable = false)
    @Builder.Default
    private Boolean coberturaRadioterapia = false; // Se cobre radioterapia

    @Column(name = "cobertura_hemodialise", nullable = false)
    @Builder.Default
    private Boolean coberturaHemodialise = false; // Se cobre hemodiálise

    @Column(name = "cobertura_obstetricia", nullable = false)
    @Builder.Default
    private Boolean coberturaObstetricia = false; // Se cobre obstetrícia

    @Column(name = "cobertura_pediatria", nullable = false)
    @Builder.Default
    private Boolean coberturaPediatria = false; // Se cobre pediatria

    @Column(name = "cobertura_geriatria", nullable = false)
    @Builder.Default
    private Boolean coberturaGeriatria = false; // Se cobre geriatria

    @Column(name = "cobertura_psiquiatria", nullable = false)
    @Builder.Default
    private Boolean coberturaPsiquiatria = false; // Se cobre psiquiatria

    @Column(name = "cobertura_outras", columnDefinition = "TEXT")
    private String coberturaOutras; // Outras coberturas específicas
}

