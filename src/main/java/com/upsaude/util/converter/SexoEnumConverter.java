package com.upsaude.util.converter;

import com.upsaude.enums.SexoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class SexoEnumConverter implements AttributeConverter<SexoEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(SexoEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public SexoEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return SexoEnum.fromCodigo(dbData);
    }
}

