package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.TipoServicoSaudeEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class TipoServicoSaudeEnumDeserializer extends JsonDeserializer<TipoServicoSaudeEnum> {
    @Override
    public TipoServicoSaudeEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            TipoServicoSaudeEnum result = TipoServicoSaudeEnum.fromCodigo(codigo);
            if (result != null) return result;
        }
        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) return null;
        String strValue = value.trim();
        try {
            Integer codigo = Integer.parseInt(strValue);
            TipoServicoSaudeEnum result = TipoServicoSaudeEnum.fromCodigo(codigo);
            if (result != null) return result;
        } catch (NumberFormatException e) {}
        TipoServicoSaudeEnum result = TipoServicoSaudeEnum.fromDescricao(strValue);
        if (result != null) return result;
        try {
            return TipoServicoSaudeEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException("Valor inválido para TipoServicoSaudeEnum: '" + strValue + "'");
        }
    }
}
