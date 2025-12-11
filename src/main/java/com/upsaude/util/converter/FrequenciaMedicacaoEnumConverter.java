package com.upsaude.util.converter;

import com.upsaude.enums.FrequenciaMedicacaoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class FrequenciaMedicacaoEnumConverter implements AttributeConverter<FrequenciaMedicacaoEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(FrequenciaMedicacaoEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public FrequenciaMedicacaoEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return FrequenciaMedicacaoEnum.fromCodigo(dbData);
    }
}
