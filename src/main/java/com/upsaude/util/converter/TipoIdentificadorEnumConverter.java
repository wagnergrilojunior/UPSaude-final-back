package com.upsaude.util.converter;

import com.upsaude.enums.TipoIdentificadorEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class TipoIdentificadorEnumConverter implements AttributeConverter<TipoIdentificadorEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoIdentificadorEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public TipoIdentificadorEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoIdentificadorEnum.fromCodigo(dbData);
    }
}

