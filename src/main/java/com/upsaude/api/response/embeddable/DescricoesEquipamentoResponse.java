package com.upsaude.api.response.embeddable;

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
public class DescricoesEquipamentoResponse {

    private String descricao;

    private String caracteristicas;

    private String aplicacoes;

    private String observacoes;
}
