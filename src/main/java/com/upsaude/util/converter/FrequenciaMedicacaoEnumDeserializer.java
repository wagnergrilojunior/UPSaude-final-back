package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.FrequenciaMedicacaoEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class FrequenciaMedicacaoEnumDeserializer extends JsonDeserializer<FrequenciaMedicacaoEnum> {
    @Override
    public FrequenciaMedicacaoEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            FrequenciaMedicacaoEnum result = FrequenciaMedicacaoEnum.fromCodigo(codigo);
            if (result != null) return result;
        }
        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) return null;
        String strValue = value.trim();
        try {
            Integer codigo = Integer.parseInt(strValue);
            FrequenciaMedicacaoEnum result = FrequenciaMedicacaoEnum.fromCodigo(codigo);
            if (result != null) return result;
        } catch (NumberFormatException e) {}
        FrequenciaMedicacaoEnum result = FrequenciaMedicacaoEnum.fromDescricao(strValue);
        if (result != null) return result;
        try {
            return FrequenciaMedicacaoEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException("Valor inválido para FrequenciaMedicacaoEnum: '" + strValue + "'");
        }
    }
}
