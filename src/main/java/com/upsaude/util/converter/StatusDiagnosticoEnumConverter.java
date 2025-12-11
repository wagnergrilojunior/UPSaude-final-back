package com.upsaude.util.converter;

import com.upsaude.enums.StatusDiagnosticoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class StatusDiagnosticoEnumConverter implements AttributeConverter<StatusDiagnosticoEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(StatusDiagnosticoEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public StatusDiagnosticoEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return StatusDiagnosticoEnum.fromCodigo(dbData);
    }
}
