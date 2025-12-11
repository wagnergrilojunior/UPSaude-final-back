package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.TipoEspecialidadeMedicaEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class TipoEspecialidadeMedicaEnumDeserializer extends JsonDeserializer<TipoEspecialidadeMedicaEnum> {
    @Override
    public TipoEspecialidadeMedicaEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            TipoEspecialidadeMedicaEnum result = TipoEspecialidadeMedicaEnum.fromCodigo(codigo);
            if (result != null) return result;
        }
        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) return null;
        String strValue = value.trim();
        try {
            Integer codigo = Integer.parseInt(strValue);
            TipoEspecialidadeMedicaEnum result = TipoEspecialidadeMedicaEnum.fromCodigo(codigo);
            if (result != null) return result;
        } catch (NumberFormatException e) {}
        TipoEspecialidadeMedicaEnum result = TipoEspecialidadeMedicaEnum.fromDescricao(strValue);
        if (result != null) return result;
        try {
            return TipoEspecialidadeMedicaEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException("Valor inválido para TipoEspecialidadeMedicaEnum: '" + strValue + "'");
        }
    }
}
