package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.TipoAlergiaEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class TipoAlergiaEnumDeserializer extends JsonDeserializer<TipoAlergiaEnum> {
    @Override
    public TipoAlergiaEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            TipoAlergiaEnum result = TipoAlergiaEnum.fromCodigo(codigo);
            if (result != null) return result;
        }
        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) return null;
        String strValue = value.trim();
        try {
            Integer codigo = Integer.parseInt(strValue);
            TipoAlergiaEnum result = TipoAlergiaEnum.fromCodigo(codigo);
            if (result != null) return result;
        } catch (NumberFormatException e) {}
        TipoAlergiaEnum result = TipoAlergiaEnum.fromDescricao(strValue);
        if (result != null) return result;
        try {
            return TipoAlergiaEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException("Valor inválido para TipoAlergiaEnum: '" + strValue + "'");
        }
    }
}
