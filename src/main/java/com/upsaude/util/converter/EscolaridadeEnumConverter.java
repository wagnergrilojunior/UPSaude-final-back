package com.upsaude.util.converter;

import com.upsaude.enums.EscolaridadeEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class EscolaridadeEnumConverter implements AttributeConverter<EscolaridadeEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EscolaridadeEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public EscolaridadeEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return EscolaridadeEnum.fromCodigo(dbData);
    }
}
