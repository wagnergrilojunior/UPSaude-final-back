package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.TipoUsuarioSistemaEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;
import java.util.Optional;

public class TipoUsuarioSistemaEnumDeserializer extends JsonDeserializer<TipoUsuarioSistemaEnum> {
    @Override
    public TipoUsuarioSistemaEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            Optional<TipoUsuarioSistemaEnum> result = TipoUsuarioSistemaEnum.fromCodigo(codigo);
            if (result.isPresent()) return result.get();
        }
        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) return null;
        String strValue = value.trim();
        try {
            Integer codigo = Integer.parseInt(strValue);
            Optional<TipoUsuarioSistemaEnum> result = TipoUsuarioSistemaEnum.fromCodigo(codigo);
            if (result.isPresent()) return result.get();
        } catch (NumberFormatException e) {}
        Optional<TipoUsuarioSistemaEnum> result = TipoUsuarioSistemaEnum.fromSlug(strValue);
        if (result.isPresent()) return result.get();
        result = TipoUsuarioSistemaEnum.fromName(strValue);
        if (result.isPresent()) return result.get();
        try {
            return TipoUsuarioSistemaEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException("Valor inválido para TipoUsuarioSistemaEnum: '" + strValue + "'");
        }
    }
}
