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
public class ClassificacaoRiscoAtendimento {

    public ClassificacaoRiscoAtendimento() {
        this.classificacaoRisco = "";
        this.prioridade = "";
        this.gravidade = "";
        this.necessitaObservacao = false;
        this.necessitaInternacao = false;
    }

    @Column(name = "classificacao_risco", length = 50)
    private String classificacaoRisco;

    @Column(name = "prioridade", length = 50)
    private String prioridade;

    @Column(name = "gravidade", length = 50)
    private String gravidade;

    @Column(name = "necessita_observacao", nullable = false)
    @Builder.Default
    private Boolean necessitaObservacao = false;

    @Column(name = "necessita_internacao", nullable = false)
    @Builder.Default
    private Boolean necessitaInternacao = false;
}
