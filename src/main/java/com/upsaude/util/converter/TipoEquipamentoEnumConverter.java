package com.upsaude.util.converter;

import com.upsaude.enums.TipoEquipamentoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter para TipoEquipamentoEnum.
 * Converte entre o enum e o código Integer no banco de dados.
 *
 * @author UPSaúde
 */
@Converter(autoApply = false)
public class TipoEquipamentoEnumConverter implements AttributeConverter<TipoEquipamentoEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoEquipamentoEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public TipoEquipamentoEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoEquipamentoEnum.fromCodigo(dbData);
    }
}

