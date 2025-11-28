package com.upsaude.util.converter;

import com.upsaude.enums.TipoReacaoAlergicaEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter para TipoReacaoAlergicaEnum.
 * Converte entre o enum e o código Integer no banco de dados.
 *
 * @author UPSaúde
 */
@Converter(autoApply = false)
public class TipoReacaoAlergicaEnumConverter implements AttributeConverter<TipoReacaoAlergicaEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoReacaoAlergicaEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public TipoReacaoAlergicaEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoReacaoAlergicaEnum.fromCodigo(dbData);
    }
}

