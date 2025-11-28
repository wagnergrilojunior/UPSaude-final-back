package com.upsaude.util.converter;

import com.upsaude.enums.TipoConsultaEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter para TipoConsultaEnum.
 * Converte entre o enum e o código Integer no banco de dados.
 *
 * @author UPSaúde
 */
@Converter(autoApply = false)
public class TipoConsultaEnumConverter implements AttributeConverter<TipoConsultaEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoConsultaEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public TipoConsultaEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoConsultaEnum.fromCodigo(dbData);
    }
}

