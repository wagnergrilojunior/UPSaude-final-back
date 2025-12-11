package com.upsaude.util.converter;

import com.upsaude.enums.TipoProcedimentoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoProcedimentoEnumConverter implements AttributeConverter<TipoProcedimentoEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoProcedimentoEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public TipoProcedimentoEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoProcedimentoEnum.fromCodigo(dbData);
    }
}
