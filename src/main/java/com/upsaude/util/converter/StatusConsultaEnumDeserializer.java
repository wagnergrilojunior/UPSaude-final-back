package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.StatusConsultaEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class StatusConsultaEnumDeserializer extends JsonDeserializer<StatusConsultaEnum> {
    @Override
    public StatusConsultaEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            StatusConsultaEnum result = StatusConsultaEnum.fromCodigo(codigo);
            if (result != null) return result;
        }
        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) return null;
        String strValue = value.trim();
        try {
            Integer codigo = Integer.parseInt(strValue);
            StatusConsultaEnum result = StatusConsultaEnum.fromCodigo(codigo);
            if (result != null) return result;
        } catch (NumberFormatException e) {}
        StatusConsultaEnum result = StatusConsultaEnum.fromDescricao(strValue);
        if (result != null) return result;
        try {
            return StatusConsultaEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException("Valor inválido para StatusConsultaEnum: '" + strValue + "'");
        }
    }
}
