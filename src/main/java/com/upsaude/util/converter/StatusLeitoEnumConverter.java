package com.upsaude.util.converter;

import com.upsaude.enums.StatusLeitoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class StatusLeitoEnumConverter implements AttributeConverter<StatusLeitoEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(StatusLeitoEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public StatusLeitoEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return StatusLeitoEnum.fromCodigo(dbData);
    }
}

