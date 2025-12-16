package com.upsaude.util.converter;

import com.upsaude.enums.TipoEnderecoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class TipoEnderecoEnumConverter implements AttributeConverter<TipoEnderecoEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoEnderecoEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public TipoEnderecoEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoEnderecoEnum.fromCodigo(dbData);
    }
}
