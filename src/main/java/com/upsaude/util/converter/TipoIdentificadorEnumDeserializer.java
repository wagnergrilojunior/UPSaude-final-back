package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.TipoIdentificadorEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class TipoIdentificadorEnumDeserializer extends JsonDeserializer<TipoIdentificadorEnum> {
    @Override
    public TipoIdentificadorEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            TipoIdentificadorEnum result = TipoIdentificadorEnum.fromCodigo(codigo);
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
            TipoIdentificadorEnum result = TipoIdentificadorEnum.fromCodigo(codigo);
            if (result != null) {
                return result;
            }
        } catch (NumberFormatException e) {
        }

        TipoIdentificadorEnum result = TipoIdentificadorEnum.fromDescricao(strValue);
        if (result != null) {
            return result;
        }

        try {
            return TipoIdentificadorEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException(
                    String.format("Valor inválido para TipoIdentificadorEnum: '%s'.", strValue));
        }
    }
}
