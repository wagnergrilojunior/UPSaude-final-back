package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.FormaFarmaceuticaEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class FormaFarmaceuticaEnumDeserializer extends JsonDeserializer<FormaFarmaceuticaEnum> {
    @Override
    public FormaFarmaceuticaEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            FormaFarmaceuticaEnum result = FormaFarmaceuticaEnum.fromCodigo(codigo);
            if (result != null) return result;
        }
        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) return null;
        String strValue = value.trim();
        try {
            Integer codigo = Integer.parseInt(strValue);
            FormaFarmaceuticaEnum result = FormaFarmaceuticaEnum.fromCodigo(codigo);
            if (result != null) return result;
        } catch (NumberFormatException e) {}
        FormaFarmaceuticaEnum result = FormaFarmaceuticaEnum.fromDescricao(strValue);
        if (result != null) return result;
        try {
            return FormaFarmaceuticaEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException("Valor inválido para FormaFarmaceuticaEnum: '" + strValue + "'");
        }
    }
}
