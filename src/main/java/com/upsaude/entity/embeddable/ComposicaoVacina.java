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
public class ComposicaoVacina {

    public ComposicaoVacina() {
        this.composicao = "";
        this.tecnologia = "";
        this.adjuvante = "";
        this.conservante = "";
    }

    @Column(name = "composicao", columnDefinition = "TEXT")
    private String composicao;

    @Column(name = "tecnologia", length = 100)
    private String tecnologia;

    @Column(name = "adjuvante", length = 100)
    private String adjuvante;

    @Column(name = "conservante", length = 100)
    private String conservante;
}
