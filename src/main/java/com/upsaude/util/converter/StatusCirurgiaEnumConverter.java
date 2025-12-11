package com.upsaude.util.converter;

import com.upsaude.enums.StatusCirurgiaEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class StatusCirurgiaEnumConverter implements AttributeConverter<StatusCirurgiaEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(StatusCirurgiaEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public StatusCirurgiaEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return StatusCirurgiaEnum.fromCodigo(dbData);
    }
}
