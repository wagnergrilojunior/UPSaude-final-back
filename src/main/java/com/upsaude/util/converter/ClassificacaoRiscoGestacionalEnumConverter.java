package com.upsaude.util.converter;

import com.upsaude.enums.ClassificacaoRiscoGestacionalEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class ClassificacaoRiscoGestacionalEnumConverter implements AttributeConverter<ClassificacaoRiscoGestacionalEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ClassificacaoRiscoGestacionalEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public ClassificacaoRiscoGestacionalEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return ClassificacaoRiscoGestacionalEnum.fromCodigo(dbData);
    }
}
