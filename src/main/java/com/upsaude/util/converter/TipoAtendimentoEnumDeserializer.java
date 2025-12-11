package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.TipoAtendimentoEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class TipoAtendimentoEnumDeserializer extends JsonDeserializer<TipoAtendimentoEnum> {
    @Override
    public TipoAtendimentoEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            TipoAtendimentoEnum result = TipoAtendimentoEnum.fromCodigo(codigo);
            if (result != null) return result;
        }
        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) return null;
        String strValue = value.trim();
        try {
            Integer codigo = Integer.parseInt(strValue);
            TipoAtendimentoEnum result = TipoAtendimentoEnum.fromCodigo(codigo);
            if (result != null) return result;
        } catch (NumberFormatException e) {}
        TipoAtendimentoEnum result = TipoAtendimentoEnum.fromDescricao(strValue);
        if (result != null) return result;
        try {
            return TipoAtendimentoEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException("Valor inválido para TipoAtendimentoEnum: '" + strValue + "'");
        }
    }
}
