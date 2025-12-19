package com.upsaude.api.response.embeddable;

import com.upsaude.enums.ClasseTerapeuticaEnum;
import com.upsaude.enums.FormaFarmaceuticaEnum;
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
public class ClassificacaoMedicamentoResponse {
    private ClasseTerapeuticaEnum classeTerapeutica;
    private FormaFarmaceuticaEnum formaFarmaceutica;
    private String codigoAtc;
}
