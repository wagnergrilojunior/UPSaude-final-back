package com.upsaude.dto.embeddable;

import com.upsaude.enums.UnidadeMedidaEnum;
import com.upsaude.enums.ViaAdministracaoEnum;
import java.math.BigDecimal;
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
public class DosagemAdministracaoMedicamentoDTO {
    private String dosagem;
    private UnidadeMedidaEnum unidadeMedida;
    private ViaAdministracaoEnum viaAdministracao;
    private BigDecimal concentracao;
    private String unidadeConcentracao;
    private String posologiaPadrao;
    private String instrucoesUso;
}
