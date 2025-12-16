package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.EscolaridadeEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class EscolaridadeEnumDeserializer extends JsonDeserializer<EscolaridadeEnum> {
    @Override
    public EscolaridadeEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            EscolaridadeEnum result = EscolaridadeEnum.fromCodigo(codigo);
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
            EscolaridadeEnum result = EscolaridadeEnum.fromCodigo(codigo);
            if (result != null) {
                return result;
            }
        } catch (NumberFormatException e) {

        }

        EscolaridadeEnum result = EscolaridadeEnum.fromDescricao(strValue);
        if (result != null) {
            return result;
        }

        try {
            return EscolaridadeEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {

            throw new InvalidArgumentException(
                String.format("Valor inválido para EscolaridadeEnum: '%s'. Use um valor válido do enum.", strValue)
            );
        }
    }
}
