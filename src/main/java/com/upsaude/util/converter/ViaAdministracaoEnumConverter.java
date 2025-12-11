package com.upsaude.util.converter;

import com.upsaude.enums.ViaAdministracaoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class ViaAdministracaoEnumConverter implements AttributeConverter<ViaAdministracaoEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ViaAdministracaoEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public ViaAdministracaoEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return ViaAdministracaoEnum.fromCodigo(dbData);
    }
}
