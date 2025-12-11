package com.upsaude.util.converter;

import com.upsaude.enums.StatusConsultaEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class StatusConsultaEnumConverter implements AttributeConverter<StatusConsultaEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(StatusConsultaEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public StatusConsultaEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return StatusConsultaEnum.fromCodigo(dbData);
    }
}
