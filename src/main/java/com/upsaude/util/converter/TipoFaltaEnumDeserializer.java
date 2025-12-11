package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.TipoFaltaEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class TipoFaltaEnumDeserializer extends JsonDeserializer<TipoFaltaEnum> {
    @Override
    public TipoFaltaEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            TipoFaltaEnum result = TipoFaltaEnum.fromCodigo(codigo);
            if (result != null) return result;
        }
        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) return null;
        String strValue = value.trim();
        try {
            Integer codigo = Integer.parseInt(strValue);
            TipoFaltaEnum result = TipoFaltaEnum.fromCodigo(codigo);
            if (result != null) return result;
        } catch (NumberFormatException e) {}
        TipoFaltaEnum result = TipoFaltaEnum.fromDescricao(strValue);
        if (result != null) return result;
        try {
            return TipoFaltaEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException("Valor inválido para TipoFaltaEnum: '" + strValue + "'");
        }
    }
}
