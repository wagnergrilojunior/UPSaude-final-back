package com.upsaude.entity.embeddable;

import com.upsaude.enums.ClasseTerapeuticaEnum;
import com.upsaude.enums.FormaFarmaceuticaEnum;
import com.upsaude.util.converter.ClasseTerapeuticaEnumConverter;
import com.upsaude.util.converter.FormaFarmaceuticaEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Embeddable
@Data
@Builder
@AllArgsConstructor
public class ClassificacaoMedicamento {

    public ClassificacaoMedicamento() {
        this.codigoAtc = "";
    }

    @Convert(converter = ClasseTerapeuticaEnumConverter.class)
    @Column(name = "classe_terapeutica")
    private ClasseTerapeuticaEnum classeTerapeutica;

    @Convert(converter = FormaFarmaceuticaEnumConverter.class)
    @Column(name = "forma_farmaceutica")
    private FormaFarmaceuticaEnum formaFarmaceutica;

    @Size(max = 50, message = "Código ATC deve ter no máximo 50 caracteres")
    @Column(name = "codigo_atc", length = 50)
    private String codigoAtc;
}
