package com.upsaude.util.converter;

import com.upsaude.enums.StatusCirurgiaEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter para StatusCirurgiaEnum.
 * Converte entre o enum e o código Integer no banco de dados.
 *
 * @author UPSaúde
 */
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

