package com.upsaude.util.converter;

import com.upsaude.enums.StatusManutencaoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class StatusManutencaoEnumConverter implements AttributeConverter<StatusManutencaoEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(StatusManutencaoEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public StatusManutencaoEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return StatusManutencaoEnum.fromCodigo(dbData);
    }
}
