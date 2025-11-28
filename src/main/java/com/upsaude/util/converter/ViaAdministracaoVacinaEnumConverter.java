package com.upsaude.util.converter;

import com.upsaude.enums.ViaAdministracaoVacinaEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter para ViaAdministracaoVacinaEnum.
 * Converte entre o enum e o código Integer no banco de dados.
 *
 * @author UPSaúde
 */
@Converter(autoApply = false)
public class ViaAdministracaoVacinaEnumConverter implements AttributeConverter<ViaAdministracaoVacinaEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ViaAdministracaoVacinaEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public ViaAdministracaoVacinaEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return ViaAdministracaoVacinaEnum.fromCodigo(dbData);
    }
}

