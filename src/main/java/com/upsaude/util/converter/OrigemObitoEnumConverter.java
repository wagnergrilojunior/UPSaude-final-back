package com.upsaude.util.converter;

import com.upsaude.enums.OrigemObitoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class OrigemObitoEnumConverter implements AttributeConverter<OrigemObitoEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(OrigemObitoEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public OrigemObitoEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return OrigemObitoEnum.fromCodigo(dbData);
    }
}

