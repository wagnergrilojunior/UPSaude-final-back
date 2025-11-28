package com.upsaude.util.converter;

import com.upsaude.enums.ClasseTerapeuticaEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter para ClasseTerapeuticaEnum.
 * Converte entre o enum e o código Integer no banco de dados.
 *
 * @author UPSaúde
 */
@Converter(autoApply = false)
public class ClasseTerapeuticaEnumConverter implements AttributeConverter<ClasseTerapeuticaEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ClasseTerapeuticaEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public ClasseTerapeuticaEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return ClasseTerapeuticaEnum.fromCodigo(dbData);
    }
}

