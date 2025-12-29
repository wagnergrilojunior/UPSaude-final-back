package com.upsaude.util.converter;

import com.upsaude.enums.StatusSincronizacaoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class StatusSincronizacaoEnumConverter implements AttributeConverter<StatusSincronizacaoEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(StatusSincronizacaoEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public StatusSincronizacaoEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return StatusSincronizacaoEnum.fromCodigo(dbData);
    }
}

