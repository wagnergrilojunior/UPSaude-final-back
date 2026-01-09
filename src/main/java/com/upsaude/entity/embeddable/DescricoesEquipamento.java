package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DescricoesEquipamento {

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "caracteristicas", columnDefinition = "TEXT")
    private String caracteristicas;

    @Column(name = "aplicacoes", columnDefinition = "TEXT")
    private String aplicacoes;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}
