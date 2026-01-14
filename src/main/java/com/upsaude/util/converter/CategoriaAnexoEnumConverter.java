package com.upsaude.util.converter;

import com.upsaude.enums.CategoriaAnexoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class CategoriaAnexoEnumConverter implements AttributeConverter<CategoriaAnexoEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(CategoriaAnexoEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public CategoriaAnexoEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return CategoriaAnexoEnum.fromCodigo(dbData);
    }
}
