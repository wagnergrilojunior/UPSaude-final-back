package com.upsaude.util.converter;

import com.upsaude.enums.TipoFaltaEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter para TipoFaltaEnum.
 * Converte entre o enum e o código Integer no banco de dados.
 *
 * @author UPSaúde
 */
@Converter(autoApply = false)
public class TipoFaltaEnumConverter implements AttributeConverter<TipoFaltaEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoFaltaEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public TipoFaltaEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoFaltaEnum.fromCodigo(dbData);
    }
}

