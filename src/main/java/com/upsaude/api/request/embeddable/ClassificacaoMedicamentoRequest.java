package com.upsaude.api.request.embeddable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.ClasseTerapeuticaEnum;
import com.upsaude.enums.FormaFarmaceuticaEnum;
import com.upsaude.util.converter.ClasseTerapeuticaEnumDeserializer;
import com.upsaude.util.converter.FormaFarmaceuticaEnumDeserializer;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de classificacao medicamento")
public class ClassificacaoMedicamentoRequest {
    @JsonDeserialize(using = ClasseTerapeuticaEnumDeserializer.class)
    private ClasseTerapeuticaEnum classeTerapeutica;

    @JsonDeserialize(using = FormaFarmaceuticaEnumDeserializer.class)
    private FormaFarmaceuticaEnum formaFarmaceutica;

    @Size(max = 100, message = "Categoria deve ter no máximo 100 caracteres")
    private String categoria;

    @Size(max = 100, message = "Subcategoria deve ter no máximo 100 caracteres")
    private String subcategoria;

    @Size(max = 50, message = "Código ATC deve ter no máximo 50 caracteres")
    private String codigoAtc;
}
