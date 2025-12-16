package com.upsaude.util.converter;

import com.upsaude.enums.IdentidadeGeneroEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class IdentidadeGeneroEnumConverter implements AttributeConverter<IdentidadeGeneroEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(IdentidadeGeneroEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public IdentidadeGeneroEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return IdentidadeGeneroEnum.fromCodigo(dbData);
    }
}
