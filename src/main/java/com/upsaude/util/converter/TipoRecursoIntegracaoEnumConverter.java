package com.upsaude.util.converter;

import com.upsaude.enums.TipoRecursoIntegracaoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class TipoRecursoIntegracaoEnumConverter implements AttributeConverter<TipoRecursoIntegracaoEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoRecursoIntegracaoEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public TipoRecursoIntegracaoEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoRecursoIntegracaoEnum.fromCodigo(dbData);
    }
}
