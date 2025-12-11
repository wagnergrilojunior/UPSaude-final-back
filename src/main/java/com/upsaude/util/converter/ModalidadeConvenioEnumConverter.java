package com.upsaude.util.converter;

import com.upsaude.enums.ModalidadeConvenioEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class ModalidadeConvenioEnumConverter implements AttributeConverter<ModalidadeConvenioEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ModalidadeConvenioEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public ModalidadeConvenioEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return ModalidadeConvenioEnum.fromCodigo(dbData);
    }
}
