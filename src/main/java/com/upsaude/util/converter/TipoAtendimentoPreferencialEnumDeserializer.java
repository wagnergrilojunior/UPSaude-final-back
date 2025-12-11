package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.TipoAtendimentoPreferencialEnum;
import java.io.IOException;

public class TipoAtendimentoPreferencialEnumDeserializer extends JsonDeserializer<TipoAtendimentoPreferencialEnum> {
    @Override
    public TipoAtendimentoPreferencialEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            TipoAtendimentoPreferencialEnum result = TipoAtendimentoPreferencialEnum.fromCodigo(codigo);
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
            TipoAtendimentoPreferencialEnum result = TipoAtendimentoPreferencialEnum.fromCodigo(codigo);
            if (result != null) {
                return result;
            }
        } catch (NumberFormatException e) {

        }

        TipoAtendimentoPreferencialEnum result = TipoAtendimentoPreferencialEnum.fromDescricao(strValue);
        if (result != null) {
            return result;
        }

        try {
            return TipoAtendimentoPreferencialEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {

            return null;
        }
    }
}
