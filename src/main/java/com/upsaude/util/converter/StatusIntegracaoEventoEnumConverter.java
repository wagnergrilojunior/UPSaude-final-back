package com.upsaude.util.converter;

import com.upsaude.enums.StatusIntegracaoEventoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class StatusIntegracaoEventoEnumConverter implements AttributeConverter<StatusIntegracaoEventoEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(StatusIntegracaoEventoEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public StatusIntegracaoEventoEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return StatusIntegracaoEventoEnum.fromCodigo(dbData);
    }
}
