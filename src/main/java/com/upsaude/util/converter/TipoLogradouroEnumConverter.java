package com.upsaude.util.converter;

import com.upsaude.enums.TipoLogradouroEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class TipoLogradouroEnumConverter implements AttributeConverter<TipoLogradouroEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoLogradouroEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public TipoLogradouroEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoLogradouroEnum.fromCodigo(dbData);
    }
}
