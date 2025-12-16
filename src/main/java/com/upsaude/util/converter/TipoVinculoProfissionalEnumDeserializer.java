package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.TipoVinculoProfissionalEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class TipoVinculoProfissionalEnumDeserializer extends JsonDeserializer<TipoVinculoProfissionalEnum> {
    @Override
    public TipoVinculoProfissionalEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            TipoVinculoProfissionalEnum result = TipoVinculoProfissionalEnum.fromCodigo(codigo);
            if (result != null) return result;
        }
        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) return null;
        String strValue = value.trim();
        try {
            Integer codigo = Integer.parseInt(strValue);
            TipoVinculoProfissionalEnum result = TipoVinculoProfissionalEnum.fromCodigo(codigo);
            if (result != null) return result;
        } catch (NumberFormatException e) {}
        TipoVinculoProfissionalEnum result = TipoVinculoProfissionalEnum.fromDescricao(strValue);
        if (result != null) return result;
        try {
            return TipoVinculoProfissionalEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException("Valor inválido para TipoVinculoProfissionalEnum: '" + strValue + "'");
        }
    }
}
