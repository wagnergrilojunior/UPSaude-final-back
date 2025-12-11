package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.TipoResponsavelEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class TipoResponsavelEnumDeserializer extends JsonDeserializer<TipoResponsavelEnum> {
    @Override
    public TipoResponsavelEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            TipoResponsavelEnum result = TipoResponsavelEnum.fromCodigo(codigo);
            if (result != null) return result;
        }
        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) return null;
        String strValue = value.trim();
        try {
            Integer codigo = Integer.parseInt(strValue);
            TipoResponsavelEnum result = TipoResponsavelEnum.fromCodigo(codigo);
            if (result != null) return result;
        } catch (NumberFormatException e) {}
        TipoResponsavelEnum result = TipoResponsavelEnum.fromDescricao(strValue);
        if (result != null) return result;
        try {
            return TipoResponsavelEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException("Valor inválido para TipoResponsavelEnum: '" + strValue + "'");
        }
    }
}
