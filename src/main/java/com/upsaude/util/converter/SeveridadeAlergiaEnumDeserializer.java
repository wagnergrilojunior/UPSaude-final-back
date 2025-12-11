package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.SeveridadeAlergiaEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class SeveridadeAlergiaEnumDeserializer extends JsonDeserializer<SeveridadeAlergiaEnum> {
    @Override
    public SeveridadeAlergiaEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            SeveridadeAlergiaEnum result = SeveridadeAlergiaEnum.fromCodigo(codigo);
            if (result != null) return result;
        }
        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) return null;
        String strValue = value.trim();
        try {
            Integer codigo = Integer.parseInt(strValue);
            SeveridadeAlergiaEnum result = SeveridadeAlergiaEnum.fromCodigo(codigo);
            if (result != null) return result;
        } catch (NumberFormatException e) {}
        SeveridadeAlergiaEnum result = SeveridadeAlergiaEnum.fromDescricao(strValue);
        if (result != null) return result;
        try {
            return SeveridadeAlergiaEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException("Valor inválido para SeveridadeAlergiaEnum: '" + strValue + "'");
        }
    }
}
