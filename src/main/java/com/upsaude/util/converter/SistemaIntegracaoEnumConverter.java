package com.upsaude.util.converter;

import com.upsaude.enums.SistemaIntegracaoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class SistemaIntegracaoEnumConverter implements AttributeConverter<SistemaIntegracaoEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(SistemaIntegracaoEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public SistemaIntegracaoEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return SistemaIntegracaoEnum.fromCodigo(dbData);
    }
}

