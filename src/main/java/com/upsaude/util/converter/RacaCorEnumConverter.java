package com.upsaude.util.converter;

import com.upsaude.enums.RacaCorEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class RacaCorEnumConverter implements AttributeConverter<RacaCorEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(RacaCorEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public RacaCorEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return RacaCorEnum.fromCodigo(dbData);
    }
}
