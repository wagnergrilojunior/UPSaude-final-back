package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.StatusAtendimentoEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class StatusAtendimentoEnumDeserializer extends JsonDeserializer<StatusAtendimentoEnum> {
    @Override
    public StatusAtendimentoEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            StatusAtendimentoEnum result = StatusAtendimentoEnum.fromCodigo(codigo);
            if (result != null) return result;
        }
        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) return null;
        String strValue = value.trim();
        try {
            Integer codigo = Integer.parseInt(strValue);
            StatusAtendimentoEnum result = StatusAtendimentoEnum.fromCodigo(codigo);
            if (result != null) return result;
        } catch (NumberFormatException e) {}
        StatusAtendimentoEnum result = StatusAtendimentoEnum.fromDescricao(strValue);
        if (result != null) return result;
        try {
            return StatusAtendimentoEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException("Valor inválido para StatusAtendimentoEnum: '" + strValue + "'");
        }
    }
}
