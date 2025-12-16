package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.TipoProcedimentoEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class TipoProcedimentoEnumDeserializer extends JsonDeserializer<TipoProcedimentoEnum> {
    @Override
    public TipoProcedimentoEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            TipoProcedimentoEnum result = TipoProcedimentoEnum.fromCodigo(codigo);
            if (result != null) return result;
        }
        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) return null;
        String strValue = value.trim();
        try {
            Integer codigo = Integer.parseInt(strValue);
            TipoProcedimentoEnum result = TipoProcedimentoEnum.fromCodigo(codigo);
            if (result != null) return result;
        } catch (NumberFormatException e) {}
        TipoProcedimentoEnum result = TipoProcedimentoEnum.fromDescricao(strValue);
        if (result != null) return result;
        try {
            return TipoProcedimentoEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException("Valor inválido para TipoProcedimentoEnum: '" + strValue + "'");
        }
    }
}
