package com.upsaude.util.converter;

import com.upsaude.enums.TipoNotificacaoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter para TipoNotificacaoEnum.
 * Converte entre o enum e o código Integer no banco de dados.
 *
 * @author UPSaúde
 */
@Converter(autoApply = false)
public class TipoNotificacaoEnumConverter implements AttributeConverter<TipoNotificacaoEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoNotificacaoEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public TipoNotificacaoEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoNotificacaoEnum.fromCodigo(dbData);
    }
}

