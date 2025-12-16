package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.CondicaoMoradiaEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class CondicaoMoradiaEnumDeserializer extends JsonDeserializer<CondicaoMoradiaEnum> {
    @Override
    public CondicaoMoradiaEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            CondicaoMoradiaEnum result = CondicaoMoradiaEnum.fromCodigo(codigo);
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
            CondicaoMoradiaEnum result = CondicaoMoradiaEnum.fromCodigo(codigo);
            if (result != null) {
                return result;
            }
        } catch (NumberFormatException e) {

        }

        CondicaoMoradiaEnum result = CondicaoMoradiaEnum.fromDescricao(strValue);
        if (result != null) {
            return result;
        }

        try {
            return CondicaoMoradiaEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {

            throw new InvalidArgumentException(
                String.format("Valor inválido para CondicaoMoradiaEnum: '%s'. Valores válidos: PROPRIO_QUITADO, PROPRIO_FINANCIADO, ALUGADO, CEDIDO, INVADIDO, OUTRO, IGNORADO, NAO_INFORMADO", strValue)
            );
        }
    }
}
