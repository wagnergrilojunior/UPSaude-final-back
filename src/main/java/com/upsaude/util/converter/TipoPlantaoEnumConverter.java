package com.upsaude.util.converter;

import com.upsaude.enums.TipoPlantaoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

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
