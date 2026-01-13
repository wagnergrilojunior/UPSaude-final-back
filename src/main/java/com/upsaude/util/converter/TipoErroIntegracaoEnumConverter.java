package com.upsaude.util.converter;

import com.upsaude.enums.TipoErroIntegracaoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class TipoErroIntegracaoEnumConverter implements AttributeConverter<TipoErroIntegracaoEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoErroIntegracaoEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public TipoErroIntegracaoEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoErroIntegracaoEnum.fromCodigo(dbData);
    }
}
