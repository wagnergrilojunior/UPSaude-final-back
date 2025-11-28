package com.upsaude.util.converter;

import com.upsaude.enums.StatusReceitaEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter para StatusReceitaEnum.
 * Converte entre o enum e o código Integer no banco de dados.
 *
 * @author UPSaúde
 */
@Converter(autoApply = false)
public class StatusReceitaEnumConverter implements AttributeConverter<StatusReceitaEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(StatusReceitaEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public StatusReceitaEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return StatusReceitaEnum.fromCodigo(dbData);
    }
}

