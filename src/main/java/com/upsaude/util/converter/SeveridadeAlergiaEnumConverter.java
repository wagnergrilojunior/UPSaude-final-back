package com.upsaude.util.converter;

import com.upsaude.enums.SeveridadeAlergiaEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter para SeveridadeAlergiaEnum.
 * Converte entre o enum e o código Integer no banco de dados.
 *
 * @author UPSaúde
 */
@Converter(autoApply = false)
public class SeveridadeAlergiaEnumConverter implements AttributeConverter<SeveridadeAlergiaEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(SeveridadeAlergiaEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public SeveridadeAlergiaEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return SeveridadeAlergiaEnum.fromCodigo(dbData);
    }
}

