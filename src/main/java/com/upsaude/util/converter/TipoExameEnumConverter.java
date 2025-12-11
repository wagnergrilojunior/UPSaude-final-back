package com.upsaude.util.converter;

import com.upsaude.enums.TipoExameEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoExameEnumConverter implements AttributeConverter<TipoExameEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoExameEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public TipoExameEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoExameEnum.fromCodigo(dbData);
    }
}
