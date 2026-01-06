package com.upsaude.api.request.embeddable;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Descrições e observações do equipamento")
public class DescricoesEquipamentoRequest {

    private String descricao;

    private String caracteristicas;

    private String aplicacoes;

    private String observacoes;
}
