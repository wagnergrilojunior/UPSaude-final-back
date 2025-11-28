package com.upsaude.util.converter;

import com.upsaude.enums.TipoAtendimentoPreferencialEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class TipoAtendimentoPreferencialEnumConverter implements AttributeConverter<TipoAtendimentoPreferencialEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoAtendimentoPreferencialEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public TipoAtendimentoPreferencialEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoAtendimentoPreferencialEnum.fromCodigo(dbData);
    }
}

