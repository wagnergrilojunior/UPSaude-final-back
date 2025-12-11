package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.TipoReacaoAlergicaEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class TipoReacaoAlergicaEnumDeserializer extends JsonDeserializer<TipoReacaoAlergicaEnum> {
    @Override
    public TipoReacaoAlergicaEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            TipoReacaoAlergicaEnum result = TipoReacaoAlergicaEnum.fromCodigo(codigo);
            if (result != null) return result;
        }
        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) return null;
        String strValue = value.trim();
        try {
            Integer codigo = Integer.parseInt(strValue);
            TipoReacaoAlergicaEnum result = TipoReacaoAlergicaEnum.fromCodigo(codigo);
            if (result != null) return result;
        } catch (NumberFormatException e) {}
        TipoReacaoAlergicaEnum result = TipoReacaoAlergicaEnum.fromDescricao(strValue);
        if (result != null) return result;
        try {
            return TipoReacaoAlergicaEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException("Valor inválido para TipoReacaoAlergicaEnum: '" + strValue + "'");
        }
    }
}
