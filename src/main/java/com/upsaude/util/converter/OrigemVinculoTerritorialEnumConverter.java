package com.upsaude.util.converter;

import com.upsaude.enums.OrigemVinculoTerritorialEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class OrigemVinculoTerritorialEnumConverter implements AttributeConverter<OrigemVinculoTerritorialEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(OrigemVinculoTerritorialEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public OrigemVinculoTerritorialEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return OrigemVinculoTerritorialEnum.fromCodigo(dbData);
    }
}

