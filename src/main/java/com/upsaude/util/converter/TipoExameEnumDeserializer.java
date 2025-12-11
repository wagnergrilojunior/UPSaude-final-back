package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.TipoExameEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class TipoExameEnumDeserializer extends JsonDeserializer<TipoExameEnum> {
    @Override
    public TipoExameEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            TipoExameEnum result = TipoExameEnum.fromCodigo(codigo);
            if (result != null) return result;
        }
        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) return null;
        String strValue = value.trim();
        try {
            Integer codigo = Integer.parseInt(strValue);
            TipoExameEnum result = TipoExameEnum.fromCodigo(codigo);
            if (result != null) return result;
        } catch (NumberFormatException e) {}
        TipoExameEnum result = TipoExameEnum.fromDescricao(strValue);
        if (result != null) return result;
        try {
            return TipoExameEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException("Valor inválido para TipoExameEnum: '" + strValue + "'");
        }
    }
}
