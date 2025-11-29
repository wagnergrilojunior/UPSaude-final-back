package com.upsaude.util.converter;

import com.upsaude.enums.TipoAtividadeProfissionalEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter para TipoAtividadeProfissionalEnum.
 * Converte entre o enum e o código Integer no banco de dados.
 *
 * @author UPSaúde
 */
@Converter(autoApply = false)
public class TipoAtividadeProfissionalEnumConverter implements AttributeConverter<TipoAtividadeProfissionalEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoAtividadeProfissionalEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public TipoAtividadeProfissionalEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoAtividadeProfissionalEnum.fromCodigo(dbData);
    }
}

