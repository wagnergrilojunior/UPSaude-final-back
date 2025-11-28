package com.upsaude.util.converter;

import com.upsaude.enums.TipoControleMedicamentoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter para TipoControleMedicamentoEnum.
 * Converte entre o enum e o código Integer no banco de dados.
 *
 * @author UPSaúde
 */
@Converter(autoApply = false)
public class TipoControleMedicamentoEnumConverter implements AttributeConverter<TipoControleMedicamentoEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoControleMedicamentoEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public TipoControleMedicamentoEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoControleMedicamentoEnum.fromCodigo(dbData);
    }
}

