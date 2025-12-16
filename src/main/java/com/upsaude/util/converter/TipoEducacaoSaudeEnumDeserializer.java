package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.TipoEducacaoSaudeEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class TipoEducacaoSaudeEnumDeserializer extends JsonDeserializer<TipoEducacaoSaudeEnum> {
    @Override
    public TipoEducacaoSaudeEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            TipoEducacaoSaudeEnum result = TipoEducacaoSaudeEnum.fromCodigo(codigo);
            if (result != null) return result;
        }
        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) return null;
        String strValue = value.trim();
        try {
            Integer codigo = Integer.parseInt(strValue);
            TipoEducacaoSaudeEnum result = TipoEducacaoSaudeEnum.fromCodigo(codigo);
            if (result != null) return result;
        } catch (NumberFormatException e) {}
        TipoEducacaoSaudeEnum result = TipoEducacaoSaudeEnum.fromDescricao(strValue);
        if (result != null) return result;
        try {
            return TipoEducacaoSaudeEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException("Valor inválido para TipoEducacaoSaudeEnum: '" + strValue + "'");
        }
    }
}
