package com.upsaude.util.converter;

import com.upsaude.enums.TipoEntidadeCnesEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class TipoEntidadeCnesEnumConverter implements AttributeConverter<TipoEntidadeCnesEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoEntidadeCnesEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public TipoEntidadeCnesEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoEntidadeCnesEnum.fromCodigo(dbData);
    }
}

