package com.upsaude.util.converter;

import com.upsaude.enums.ClassificacaoRiscoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class ClassificacaoRiscoEnumConverter implements AttributeConverter<ClassificacaoRiscoEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ClassificacaoRiscoEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public ClassificacaoRiscoEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return ClassificacaoRiscoEnum.fromCodigo(dbData);
    }
}

