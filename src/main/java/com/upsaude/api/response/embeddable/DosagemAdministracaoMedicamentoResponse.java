package com.upsaude.api.response.embeddable;

import com.upsaude.enums.UnidadeMedidaEnum;
import com.upsaude.enums.ViaAdministracaoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DosagemAdministracaoMedicamentoResponse {
    private String dosagem;
    private UnidadeMedidaEnum unidadeMedida;
    private ViaAdministracaoEnum viaAdministracao;
    private BigDecimal concentracao;
    private String unidadeConcentracao;
    private String posologiaPadrao;
    private String instrucoesUso;
}
