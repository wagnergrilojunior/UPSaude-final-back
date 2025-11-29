package com.upsaude.util.converter;

import com.upsaude.enums.TipoPontoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter para TipoPontoEnum.
 * Converte entre o enum e o código Integer no banco de dados.
 *
 * @author UPSaúde
 */
@Converter(autoApply = false)
public class TipoPontoEnumConverter implements AttributeConverter<TipoPontoEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoPontoEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public TipoPontoEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoPontoEnum.fromCodigo(dbData);
    }
}

