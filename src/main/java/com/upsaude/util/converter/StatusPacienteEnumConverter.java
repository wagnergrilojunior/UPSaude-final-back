package com.upsaude.util.converter;

import com.upsaude.enums.StatusPacienteEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class StatusPacienteEnumConverter implements AttributeConverter<StatusPacienteEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(StatusPacienteEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public StatusPacienteEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return StatusPacienteEnum.fromCodigo(dbData);
    }
}

