package com.upsaude.util.converter;

import com.upsaude.enums.TipoCuidadoEnfermagemEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class TipoCuidadoEnfermagemEnumConverter implements AttributeConverter<TipoCuidadoEnfermagemEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoCuidadoEnfermagemEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public TipoCuidadoEnfermagemEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoCuidadoEnfermagemEnum.fromCodigo(dbData);
    }
}
