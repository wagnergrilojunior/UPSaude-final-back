package com.upsaude.util.converter;

import com.upsaude.enums.TipoVinculoProfissionalEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter para TipoVinculoProfissionalEnum.
 * Converte entre o enum e o código Integer no banco de dados.
 *
 * @author UPSaúde
 */
@Converter(autoApply = false)
public class TipoVinculoProfissionalEnumConverter implements AttributeConverter<TipoVinculoProfissionalEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoVinculoProfissionalEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCodigo();
    }

    @Override
    public TipoVinculoProfissionalEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoVinculoProfissionalEnum.fromCodigo(dbData);
    }
}

