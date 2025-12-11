package com.upsaude.util.converter;

import com.upsaude.enums.TipoProfissionalEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class TipoProfissionalEnumConverter implements AttributeConverter<TipoProfissionalEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoProfissionalEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public TipoProfissionalEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoProfissionalEnum.fromCodigo(dbData);
    }
}
