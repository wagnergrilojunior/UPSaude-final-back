package com.upsaude.util.converter;

import com.upsaude.enums.TipoVacinaEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter para TipoVacinaEnum.
 * Converte entre o enum e o código Integer no banco de dados.
 *
 * @author UPSaúde
 */
@Converter(autoApply = false)
public class TipoVacinaEnumConverter implements AttributeConverter<TipoVacinaEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoVacinaEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public TipoVacinaEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoVacinaEnum.fromCodigo(dbData);
    }
}

