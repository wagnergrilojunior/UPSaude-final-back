package com.upsaude.dto.embeddable;

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
public class TratamentoPadraoDoencaDTO {
    private String tratamentoPadrao;
    private String medicamentosComuns;
    private String procedimentosComuns;
    private String cuidadosEspeciais;
    private String prevencao;
}
