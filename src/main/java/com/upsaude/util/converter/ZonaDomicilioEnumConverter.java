package com.upsaude.util.converter;

import com.upsaude.enums.ZonaDomicilioEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class ZonaDomicilioEnumConverter implements AttributeConverter<ZonaDomicilioEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ZonaDomicilioEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public ZonaDomicilioEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return ZonaDomicilioEnum.fromCodigo(dbData);
    }
}

