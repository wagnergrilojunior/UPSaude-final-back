package com.upsaude.util.converter;

import com.upsaude.enums.TipoServicoSaudeEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class TipoServicoSaudeEnumConverter implements AttributeConverter<TipoServicoSaudeEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoServicoSaudeEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public TipoServicoSaudeEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoServicoSaudeEnum.fromCodigo(dbData);
    }
}
