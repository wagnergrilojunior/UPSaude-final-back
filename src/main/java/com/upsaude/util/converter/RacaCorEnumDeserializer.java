package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.RacaCorEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class RacaCorEnumDeserializer extends JsonDeserializer<RacaCorEnum> {
    @Override
    public RacaCorEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            RacaCorEnum result = RacaCorEnum.fromCodigo(codigo);
            if (result != null) {
                return result;
            }
        }

        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        String strValue = value.trim();

        try {
            Integer codigo = Integer.parseInt(strValue);
            RacaCorEnum result = RacaCorEnum.fromCodigo(codigo);
            if (result != null) {
                return result;
            }
        } catch (NumberFormatException e) {

        }

        RacaCorEnum result = RacaCorEnum.fromDescricao(strValue);
        if (result != null) {
            return result;
        }

        try {
            return RacaCorEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {

            throw new InvalidArgumentException(
                String.format("Valor inválido para RacaCorEnum: '%s'. Valores válidos: BRANCA, PRETA, PARDA, AMARELA, INDIGENA", strValue)
            );
        }
    }
}
