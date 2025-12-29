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
public class InformacoesAdicionaisTenant {

    @Column(name = "horario_funcionamento", columnDefinition = "TEXT")
    private String horarioFuncionamento;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}

