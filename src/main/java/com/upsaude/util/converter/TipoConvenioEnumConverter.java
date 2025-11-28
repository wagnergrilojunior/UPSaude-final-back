package com.upsaude.util.converter;

import com.upsaude.enums.TipoConvenioEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter para TipoConvenioEnum.
 * Converte entre o enum e o código Integer no banco de dados.
 *
 * @author UPSaúde
 */
@Converter(autoApply = false)
public class TipoConvenioEnumConverter implements AttributeConverter<TipoConvenioEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoConvenioEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public TipoConvenioEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoConvenioEnum.fromCodigo(dbData);
    }
}

