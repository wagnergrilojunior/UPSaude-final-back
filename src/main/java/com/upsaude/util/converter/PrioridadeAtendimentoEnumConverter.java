package com.upsaude.util.converter;

import com.upsaude.enums.PrioridadeAtendimentoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class PrioridadeAtendimentoEnumConverter implements AttributeConverter<PrioridadeAtendimentoEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(PrioridadeAtendimentoEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public PrioridadeAtendimentoEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return PrioridadeAtendimentoEnum.fromCodigo(dbData);
    }
}
