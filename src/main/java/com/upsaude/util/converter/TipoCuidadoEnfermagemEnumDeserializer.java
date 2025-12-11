package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.TipoCuidadoEnfermagemEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class TipoCuidadoEnfermagemEnumDeserializer extends JsonDeserializer<TipoCuidadoEnfermagemEnum> {
    @Override
    public TipoCuidadoEnfermagemEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            TipoCuidadoEnfermagemEnum result = TipoCuidadoEnfermagemEnum.fromCodigo(codigo);
            if (result != null) return result;
        }
        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) return null;
        String strValue = value.trim();
        try {
            Integer codigo = Integer.parseInt(strValue);
            TipoCuidadoEnfermagemEnum result = TipoCuidadoEnfermagemEnum.fromCodigo(codigo);
            if (result != null) return result;
        } catch (NumberFormatException e) {}
        TipoCuidadoEnfermagemEnum result = TipoCuidadoEnfermagemEnum.fromDescricao(strValue);
        if (result != null) return result;
        try {
            return TipoCuidadoEnfermagemEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException("Valor inválido para TipoCuidadoEnfermagemEnum: '" + strValue + "'");
        }
    }
}
