package com.upsaude.util.converter;

import com.upsaude.enums.TipoVisitaDomiciliarEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class TipoVisitaDomiciliarEnumConverter implements AttributeConverter<TipoVisitaDomiciliarEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoVisitaDomiciliarEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public TipoVisitaDomiciliarEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoVisitaDomiciliarEnum.fromCodigo(dbData);
    }
}

