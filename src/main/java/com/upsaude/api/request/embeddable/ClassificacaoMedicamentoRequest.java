package com.upsaude.api.request.embeddable;

import com.upsaude.enums.ClasseTerapeuticaEnum;
import com.upsaude.enums.FormaFarmaceuticaEnum;
import jakarta.validation.constraints.Size;
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
public class ClassificacaoMedicamentoRequest {
    private ClasseTerapeuticaEnum classeTerapeutica;
    
    private FormaFarmaceuticaEnum formaFarmaceutica;
    
    @Size(max = 100, message = "Categoria deve ter no máximo 100 caracteres")
    private String categoria;
    
    @Size(max = 100, message = "Subcategoria deve ter no máximo 100 caracteres")
    private String subcategoria;
    
    @Size(max = 50, message = "Código ATC deve ter no máximo 50 caracteres")
    private String codigoAtc;
}
