package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.TipoAlergiaEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

/**
 * Deserializer para TipoAlergiaEnum.
 * Aceita apenas valores do enum como string (MEDICAMENTO, ALIMENTO, etc).
 */
public class TipoAlergiaEnumDeserializer extends JsonDeserializer<TipoAlergiaEnum> {
    @Override
    public TipoAlergiaEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        String strValue = value.trim().toUpperCase();
        try {
            return TipoAlergiaEnum.valueOf(strValue);
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException(
                String.format("Valor inválido para TipoAlergiaEnum: '%s'. Valores válidos: %s", 
                    value, java.util.Arrays.toString(TipoAlergiaEnum.values()))
            );
        }
    }
}
