package com.upsaude.util.converter;

import com.upsaude.enums.TargetTypeAnexoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class TargetTypeAnexoEnumConverter implements AttributeConverter<TargetTypeAnexoEnum, String> {

    @Override
    public String convertToDatabaseColumn(TargetTypeAnexoEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public TargetTypeAnexoEnum convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return TargetTypeAnexoEnum.fromCodigo(dbData);
    }
}
