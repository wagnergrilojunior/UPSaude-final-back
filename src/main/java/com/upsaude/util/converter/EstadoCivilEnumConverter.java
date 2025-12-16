package com.upsaude.util.converter;

import com.upsaude.enums.EstadoCivilEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class EstadoCivilEnumConverter implements AttributeConverter<EstadoCivilEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EstadoCivilEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public EstadoCivilEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return EstadoCivilEnum.fromCodigo(dbData);
    }
}
