package com.upsaude.util.converter;

import com.upsaude.enums.StatusManutencaoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter para StatusManutencaoEnum.
 * Converte entre o enum e o código Integer no banco de dados.
 *
 * @author UPSaúde
 */
@Converter(autoApply = false)
public class StatusManutencaoEnumConverter implements AttributeConverter<StatusManutencaoEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(StatusManutencaoEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public StatusManutencaoEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return StatusManutencaoEnum.fromCodigo(dbData);
    }
}

