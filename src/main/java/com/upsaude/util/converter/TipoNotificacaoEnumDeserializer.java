package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.TipoNotificacaoEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class TipoNotificacaoEnumDeserializer extends JsonDeserializer<TipoNotificacaoEnum> {
    @Override
    public TipoNotificacaoEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            TipoNotificacaoEnum result = TipoNotificacaoEnum.fromCodigo(codigo);
            if (result != null) return result;
        }
        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) return null;
        String strValue = value.trim();
        try {
            Integer codigo = Integer.parseInt(strValue);
            TipoNotificacaoEnum result = TipoNotificacaoEnum.fromCodigo(codigo);
            if (result != null) return result;
        } catch (NumberFormatException e) {}
        TipoNotificacaoEnum result = TipoNotificacaoEnum.fromDescricao(strValue);
        if (result != null) return result;
        try {
            return TipoNotificacaoEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException("Valor inválido para TipoNotificacaoEnum: '" + strValue + "'");
        }
    }
}
