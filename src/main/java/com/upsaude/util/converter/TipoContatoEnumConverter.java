package com.upsaude.util.converter;

import com.upsaude.enums.TipoContatoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class TipoContatoEnumConverter implements AttributeConverter<TipoContatoEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoContatoEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public TipoContatoEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoContatoEnum.fromCodigo(dbData);
    }
}

