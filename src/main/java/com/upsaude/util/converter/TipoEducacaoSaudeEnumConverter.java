package com.upsaude.util.converter;

import com.upsaude.enums.TipoEducacaoSaudeEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class TipoEducacaoSaudeEnumConverter implements AttributeConverter<TipoEducacaoSaudeEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoEducacaoSaudeEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public TipoEducacaoSaudeEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoEducacaoSaudeEnum.fromCodigo(dbData);
    }
}

