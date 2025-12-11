package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.StatusManutencaoEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class StatusManutencaoEnumDeserializer extends JsonDeserializer<StatusManutencaoEnum> {
    @Override
    public StatusManutencaoEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            StatusManutencaoEnum result = StatusManutencaoEnum.fromCodigo(codigo);
            if (result != null) return result;
        }
        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) return null;
        String strValue = value.trim();
        try {
            Integer codigo = Integer.parseInt(strValue);
            StatusManutencaoEnum result = StatusManutencaoEnum.fromCodigo(codigo);
            if (result != null) return result;
        } catch (NumberFormatException e) {}
        StatusManutencaoEnum result = StatusManutencaoEnum.fromDescricao(strValue);
        if (result != null) return result;
        try {
            return StatusManutencaoEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException("Valor inválido para StatusManutencaoEnum: '" + strValue + "'");
        }
    }
}
