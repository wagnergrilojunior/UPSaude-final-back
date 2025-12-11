package com.upsaude.util.converter;

import com.upsaude.enums.StatusAgendamentoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class StatusAgendamentoEnumConverter implements AttributeConverter<StatusAgendamentoEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(StatusAgendamentoEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public StatusAgendamentoEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return StatusAgendamentoEnum.fromCodigo(dbData);
    }
}
