package com.upsaude.api.response.embeddable;

import com.upsaude.enums.EscolaridadeEnum;
import com.upsaude.enums.EstadoCivilEnum;
import com.upsaude.enums.IdentidadeGeneroEnum;
import com.upsaude.enums.NacionalidadeEnum;
import com.upsaude.enums.RacaCorEnum;
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
public class DadosDemograficosProfissionalResponse {
    private EstadoCivilEnum estadoCivil;
    private EscolaridadeEnum escolaridade;
    private IdentidadeGeneroEnum identidadeGenero;
    private RacaCorEnum racaCor;
    private NacionalidadeEnum nacionalidade;
    private String naturalidade;
}
