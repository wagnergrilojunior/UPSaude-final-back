package com.upsaude.dto.embeddable;

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
public class ClassificacaoMedicamentoDTO {
    private ClasseTerapeuticaEnum classeTerapeutica;
    private FormaFarmaceuticaEnum formaFarmaceutica;
    private String categoria;
    private String subcategoria;
    private String codigoAtc;
}
