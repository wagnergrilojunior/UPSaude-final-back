package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.PrioridadeAtendimentoEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class PrioridadeAtendimentoEnumDeserializer extends JsonDeserializer<PrioridadeAtendimentoEnum> {
    @Override
    public PrioridadeAtendimentoEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            PrioridadeAtendimentoEnum result = PrioridadeAtendimentoEnum.fromCodigo(codigo);
            if (result != null) return result;
        }
        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) return null;
        String strValue = value.trim();
        try {
            Integer codigo = Integer.parseInt(strValue);
            PrioridadeAtendimentoEnum result = PrioridadeAtendimentoEnum.fromCodigo(codigo);
            if (result != null) return result;
        } catch (NumberFormatException e) {}
        PrioridadeAtendimentoEnum result = PrioridadeAtendimentoEnum.fromDescricao(strValue);
        if (result != null) return result;
        try {
            return PrioridadeAtendimentoEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException("Valor inválido para PrioridadeAtendimentoEnum: '" + strValue + "'");
        }
    }
}
