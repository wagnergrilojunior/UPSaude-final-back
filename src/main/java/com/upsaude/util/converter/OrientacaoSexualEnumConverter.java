package com.upsaude.util.converter;

import com.upsaude.enums.OrientacaoSexualEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class OrientacaoSexualEnumConverter implements AttributeConverter<OrientacaoSexualEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(OrientacaoSexualEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public OrientacaoSexualEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return OrientacaoSexualEnum.fromCodigo(dbData);
    }
}

