package com.upsaude.util.converter;

import com.upsaude.enums.FormaFarmaceuticaEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class FormaFarmaceuticaEnumConverter implements AttributeConverter<FormaFarmaceuticaEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(FormaFarmaceuticaEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public FormaFarmaceuticaEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return FormaFarmaceuticaEnum.fromCodigo(dbData);
    }
}
