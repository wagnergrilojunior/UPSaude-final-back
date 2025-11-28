package com.upsaude.util.converter;

import com.upsaude.enums.TipoDeficienciaEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter para TipoDeficienciaEnum.
 * Converte entre o enum e o código Integer no banco de dados.
 *
 * @author UPSaúde
 */
@Converter(autoApply = false)
public class TipoDeficienciaEnumConverter implements AttributeConverter<TipoDeficienciaEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoDeficienciaEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public TipoDeficienciaEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoDeficienciaEnum.fromCodigo(dbData);
    }
}

