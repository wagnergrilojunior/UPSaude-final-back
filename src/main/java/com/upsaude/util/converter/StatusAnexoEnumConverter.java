package com.upsaude.util.converter;

import com.upsaude.enums.StatusAnexoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class StatusAnexoEnumConverter implements AttributeConverter<StatusAnexoEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(StatusAnexoEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public StatusAnexoEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return StatusAnexoEnum.fromCodigo(dbData);
    }
}
