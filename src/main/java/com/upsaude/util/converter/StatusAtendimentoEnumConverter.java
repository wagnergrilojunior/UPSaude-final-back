package com.upsaude.util.converter;

import com.upsaude.enums.StatusAtendimentoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class StatusAtendimentoEnumConverter implements AttributeConverter<StatusAtendimentoEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(StatusAtendimentoEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public StatusAtendimentoEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return StatusAtendimentoEnum.fromCodigo(dbData);
    }
}
