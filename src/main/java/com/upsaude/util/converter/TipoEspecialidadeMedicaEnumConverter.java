package com.upsaude.util.converter;

import com.upsaude.enums.TipoEspecialidadeMedicaEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class TipoEspecialidadeMedicaEnumConverter implements AttributeConverter<TipoEspecialidadeMedicaEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoEspecialidadeMedicaEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public TipoEspecialidadeMedicaEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoEspecialidadeMedicaEnum.fromCodigo(dbData);
    }
}
