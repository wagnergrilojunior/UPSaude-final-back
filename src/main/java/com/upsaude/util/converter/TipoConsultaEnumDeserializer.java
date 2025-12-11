package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.TipoConsultaEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class TipoConsultaEnumDeserializer extends JsonDeserializer<TipoConsultaEnum> {
    @Override
    public TipoConsultaEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            TipoConsultaEnum result = TipoConsultaEnum.fromCodigo(codigo);
            if (result != null) return result;
        }
        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) return null;
        String strValue = value.trim();
        try {
            Integer codigo = Integer.parseInt(strValue);
            TipoConsultaEnum result = TipoConsultaEnum.fromCodigo(codigo);
            if (result != null) return result;
        } catch (NumberFormatException e) {}
        TipoConsultaEnum result = TipoConsultaEnum.fromDescricao(strValue);
        if (result != null) return result;
        try {
            return TipoConsultaEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException("Valor inválido para TipoConsultaEnum: '" + strValue + "'");
        }
    }
}
