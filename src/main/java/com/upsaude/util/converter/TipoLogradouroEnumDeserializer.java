package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.TipoLogradouroEnum;
import java.io.IOException;

public class TipoLogradouroEnumDeserializer extends JsonDeserializer<TipoLogradouroEnum> {
    @Override
    public TipoLogradouroEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            TipoLogradouroEnum result = TipoLogradouroEnum.fromCodigo(codigo);
            if (result != null) {
                return result;
            }
        }

        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        String strValue = value.trim();

        try {
            Integer codigo = Integer.parseInt(strValue);
            TipoLogradouroEnum result = TipoLogradouroEnum.fromCodigo(codigo);
            if (result != null) {
                return result;
            }
        } catch (NumberFormatException e) {

        }

        TipoLogradouroEnum result = TipoLogradouroEnum.fromDescricao(strValue);
        if (result != null) {
            return result;
        }

        try {
            return TipoLogradouroEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {

            return null;
        }
    }
}
