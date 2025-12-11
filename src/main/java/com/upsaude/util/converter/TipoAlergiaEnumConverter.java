package com.upsaude.util.converter;

import com.upsaude.enums.TipoAlergiaEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class TipoAlergiaEnumConverter implements AttributeConverter<TipoAlergiaEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoAlergiaEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public TipoAlergiaEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoAlergiaEnum.fromCodigo(dbData);
    }
}
