package com.upsaude.util.converter;

import com.upsaude.enums.TipoDoencaEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter para TipoDoencaEnum.
 * Converte entre o enum e o código Integer no banco de dados.
 *
 * @author UPSaúde
 */
@Converter(autoApply = false)
public class TipoDoencaEnumConverter implements AttributeConverter<TipoDoencaEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoDoencaEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public TipoDoencaEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoDoencaEnum.fromCodigo(dbData);
    }
}

