package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.TipoMetodoContraceptivoEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class TipoMetodoContraceptivoEnumDeserializer extends JsonDeserializer<TipoMetodoContraceptivoEnum> {
    @Override
    public TipoMetodoContraceptivoEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            TipoMetodoContraceptivoEnum result = TipoMetodoContraceptivoEnum.fromCodigo(codigo);
            if (result != null) return result;
        }
        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) return null;
        String strValue = value.trim();
        try {
            Integer codigo = Integer.parseInt(strValue);
            TipoMetodoContraceptivoEnum result = TipoMetodoContraceptivoEnum.fromCodigo(codigo);
            if (result != null) return result;
        } catch (NumberFormatException e) {}
        TipoMetodoContraceptivoEnum result = TipoMetodoContraceptivoEnum.fromDescricao(strValue);
        if (result != null) return result;
        try {
            return TipoMetodoContraceptivoEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException("Valor inválido para TipoMetodoContraceptivoEnum: '" + strValue + "'");
        }
    }
}
