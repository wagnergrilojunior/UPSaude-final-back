package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.TipoDoencaEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class TipoDoencaEnumDeserializer extends JsonDeserializer<TipoDoencaEnum> {
    @Override
    public TipoDoencaEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            TipoDoencaEnum result = TipoDoencaEnum.fromCodigo(codigo);
            if (result != null) return result;
        }
        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) return null;
        String strValue = value.trim();
        try {
            Integer codigo = Integer.parseInt(strValue);
            TipoDoencaEnum result = TipoDoencaEnum.fromCodigo(codigo);
            if (result != null) return result;
        } catch (NumberFormatException e) {}
        TipoDoencaEnum result = TipoDoencaEnum.fromDescricao(strValue);
        if (result != null) return result;
        try {
            return TipoDoencaEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException("Valor inválido para TipoDoencaEnum: '" + strValue + "'");
        }
    }
}
