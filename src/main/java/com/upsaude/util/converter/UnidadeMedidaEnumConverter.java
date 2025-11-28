package com.upsaude.util.converter;

import com.upsaude.enums.UnidadeMedidaEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter para UnidadeMedidaEnum.
 * Converte entre o enum e o código Integer no banco de dados.
 *
 * @author UPSaúde
 */
@Converter(autoApply = false)
public class UnidadeMedidaEnumConverter implements AttributeConverter<UnidadeMedidaEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(UnidadeMedidaEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public UnidadeMedidaEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return UnidadeMedidaEnum.fromCodigo(dbData);
    }
}

