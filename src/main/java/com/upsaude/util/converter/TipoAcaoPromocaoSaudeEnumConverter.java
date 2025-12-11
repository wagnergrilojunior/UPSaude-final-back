package com.upsaude.util.converter;

import com.upsaude.enums.TipoAcaoPromocaoSaudeEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class TipoAcaoPromocaoSaudeEnumConverter implements AttributeConverter<TipoAcaoPromocaoSaudeEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoAcaoPromocaoSaudeEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public TipoAcaoPromocaoSaudeEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoAcaoPromocaoSaudeEnum.fromCodigo(dbData);
    }
}
