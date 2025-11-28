package com.upsaude.util.converter;

import com.upsaude.enums.StatusRegistroMedicoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter para StatusRegistroMedicoEnum.
 * Converte entre o enum e o código Integer no banco de dados.
 *
 * @author UPSaúde
 */
@Converter(autoApply = false)
public class StatusRegistroMedicoEnumConverter implements AttributeConverter<StatusRegistroMedicoEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(StatusRegistroMedicoEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public StatusRegistroMedicoEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return StatusRegistroMedicoEnum.fromCodigo(dbData);
    }
}

