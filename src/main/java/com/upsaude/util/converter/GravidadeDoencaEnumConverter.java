package com.upsaude.util.converter;

import com.upsaude.enums.GravidadeDoencaEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class GravidadeDoencaEnumConverter implements AttributeConverter<GravidadeDoencaEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(GravidadeDoencaEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public GravidadeDoencaEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return GravidadeDoencaEnum.fromCodigo(dbData);
    }
}
