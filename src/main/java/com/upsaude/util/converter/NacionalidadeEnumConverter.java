package com.upsaude.util.converter;

import com.upsaude.enums.NacionalidadeEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class NacionalidadeEnumConverter implements AttributeConverter<NacionalidadeEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(NacionalidadeEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public NacionalidadeEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return NacionalidadeEnum.fromCodigo(dbData);
    }
}
