package com.upsaude.util.converter;

import com.upsaude.enums.StatusPreNatalEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class StatusPreNatalEnumConverter implements AttributeConverter<StatusPreNatalEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(StatusPreNatalEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public StatusPreNatalEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return StatusPreNatalEnum.fromCodigo(dbData);
    }
}
