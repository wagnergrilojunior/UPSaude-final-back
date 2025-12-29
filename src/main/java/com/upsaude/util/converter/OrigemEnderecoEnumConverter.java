package com.upsaude.util.converter;

import com.upsaude.enums.OrigemEnderecoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class OrigemEnderecoEnumConverter implements AttributeConverter<OrigemEnderecoEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(OrigemEnderecoEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public OrigemEnderecoEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return OrigemEnderecoEnum.fromCodigo(dbData);
    }
}

