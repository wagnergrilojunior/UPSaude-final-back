package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.TipoVacinaEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class TipoVacinaEnumDeserializer extends JsonDeserializer<TipoVacinaEnum> {
    @Override
    public TipoVacinaEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            TipoVacinaEnum result = TipoVacinaEnum.fromCodigo(codigo);
            if (result != null) return result;
        }
        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) return null;
        String strValue = value.trim();
        try {
            Integer codigo = Integer.parseInt(strValue);
            TipoVacinaEnum result = TipoVacinaEnum.fromCodigo(codigo);
            if (result != null) return result;
        } catch (NumberFormatException e) {}
        TipoVacinaEnum result = TipoVacinaEnum.fromDescricao(strValue);
        if (result != null) return result;
        try {
            return TipoVacinaEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException("Valor inválido para TipoVacinaEnum: '" + strValue + "'");
        }
    }
}
