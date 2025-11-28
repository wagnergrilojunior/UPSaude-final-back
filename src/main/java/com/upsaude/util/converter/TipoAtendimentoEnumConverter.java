package com.upsaude.util.converter;

import com.upsaude.enums.TipoAtendimentoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter para TipoAtendimentoEnum.
 * Converte entre o enum e o código Integer no banco de dados.
 *
 * @author UPSaúde
 */
@Converter(autoApply = false)
public class TipoAtendimentoEnumConverter implements AttributeConverter<TipoAtendimentoEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoAtendimentoEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public TipoAtendimentoEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoAtendimentoEnum.fromCodigo(dbData);
    }
}

