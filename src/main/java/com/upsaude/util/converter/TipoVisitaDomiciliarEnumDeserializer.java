package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.TipoVisitaDomiciliarEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class TipoVisitaDomiciliarEnumDeserializer extends JsonDeserializer<TipoVisitaDomiciliarEnum> {
    @Override
    public TipoVisitaDomiciliarEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            TipoVisitaDomiciliarEnum result = TipoVisitaDomiciliarEnum.fromCodigo(codigo);
            if (result != null) return result;
        }
        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) return null;
        String strValue = value.trim();
        try {
            Integer codigo = Integer.parseInt(strValue);
            TipoVisitaDomiciliarEnum result = TipoVisitaDomiciliarEnum.fromCodigo(codigo);
            if (result != null) return result;
        } catch (NumberFormatException e) {}
        TipoVisitaDomiciliarEnum result = TipoVisitaDomiciliarEnum.fromDescricao(strValue);
        if (result != null) return result;
        try {
            return TipoVisitaDomiciliarEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException("Valor inválido para TipoVisitaDomiciliarEnum: '" + strValue + "'");
        }
    }
}
