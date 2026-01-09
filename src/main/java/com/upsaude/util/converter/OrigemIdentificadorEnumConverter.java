package com.upsaude.util.converter;

import com.upsaude.enums.OrigemIdentificadorEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class OrigemIdentificadorEnumConverter implements AttributeConverter<OrigemIdentificadorEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(OrigemIdentificadorEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public OrigemIdentificadorEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return OrigemIdentificadorEnum.fromCodigo(dbData);
    }
}

