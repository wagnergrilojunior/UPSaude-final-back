package com.upsaude.util.converter;

import com.upsaude.enums.StatusReceitaEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class StatusReceitaEnumConverter implements AttributeConverter<StatusReceitaEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(StatusReceitaEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public StatusReceitaEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return StatusReceitaEnum.fromCodigo(dbData);
    }
}
