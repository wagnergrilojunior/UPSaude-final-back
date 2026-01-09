package com.upsaude.util.converter;

import com.upsaude.enums.TipoAlergiaEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter para TipoAlergiaEnum.
 * O banco armazena como String (EnumType.STRING), então este converter não é mais necessário.
 * Mantido para compatibilidade, mas não é usado automaticamente.
 */
@Converter(autoApply = false)
public class TipoAlergiaEnumConverter implements AttributeConverter<TipoAlergiaEnum, String> {

    @Override
    public String convertToDatabaseColumn(TipoAlergiaEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.name();
    }

    @Override
    public TipoAlergiaEnum convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().isEmpty()) {
            return null;
        }
        try {
            return TipoAlergiaEnum.valueOf(dbData.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
