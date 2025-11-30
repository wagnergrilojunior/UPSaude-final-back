package com.upsaude.util.converter;

import com.upsaude.enums.TipoMetodoContraceptivoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class TipoMetodoContraceptivoEnumConverter implements AttributeConverter<TipoMetodoContraceptivoEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoMetodoContraceptivoEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public TipoMetodoContraceptivoEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoMetodoContraceptivoEnum.fromCodigo(dbData);
    }
}

