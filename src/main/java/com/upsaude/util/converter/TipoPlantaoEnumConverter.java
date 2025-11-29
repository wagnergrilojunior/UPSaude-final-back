package com.upsaude.util.converter;

import com.upsaude.enums.TipoPlantaoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter para TipoPlantaoEnum.
 * Converte entre o enum e o código Integer no banco de dados.
 *
 * @author UPSaúde
 */
@Converter(autoApply = false)
public class TipoPlantaoEnumConverter implements AttributeConverter<TipoPlantaoEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoPlantaoEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public TipoPlantaoEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoPlantaoEnum.fromCodigo(dbData);
    }
}

