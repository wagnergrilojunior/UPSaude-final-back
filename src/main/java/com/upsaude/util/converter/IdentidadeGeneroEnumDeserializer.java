package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.IdentidadeGeneroEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class IdentidadeGeneroEnumDeserializer extends JsonDeserializer<IdentidadeGeneroEnum> {
    @Override
    public IdentidadeGeneroEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            IdentidadeGeneroEnum result = IdentidadeGeneroEnum.fromCodigo(codigo);
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
            IdentidadeGeneroEnum result = IdentidadeGeneroEnum.fromCodigo(codigo);
            if (result != null) {
                return result;
            }
        } catch (NumberFormatException e) {

        }

        IdentidadeGeneroEnum result = IdentidadeGeneroEnum.fromDescricao(strValue);
        if (result != null) {
            return result;
        }

        try {
            return IdentidadeGeneroEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {

            throw new InvalidArgumentException(
                String.format("Valor inválido para IdentidadeGeneroEnum: '%s'. Valores válidos: CIS, TRANS, NAO_BINARIO, OUTRO", strValue)
            );
        }
    }
}
