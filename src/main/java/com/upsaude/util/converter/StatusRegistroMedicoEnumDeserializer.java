package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.StatusRegistroMedicoEnum;
import java.io.IOException;

public class StatusRegistroMedicoEnumDeserializer extends JsonDeserializer<StatusRegistroMedicoEnum> {
    @Override
    public StatusRegistroMedicoEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            StatusRegistroMedicoEnum result = StatusRegistroMedicoEnum.fromCodigo(codigo);
            if (result != null) {
                return result;
            }
        }

        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        String strValue = value.trim();

        try {
            Integer codigo = Integer.parseInt(strValue);
            StatusRegistroMedicoEnum result = StatusRegistroMedicoEnum.fromCodigo(codigo);
            if (result != null) {
                return result;
            }
        } catch (NumberFormatException e) {

        }

        StatusRegistroMedicoEnum result = StatusRegistroMedicoEnum.fromDescricao(strValue);
        if (result != null) {
            return result;
        }

        try {
            return StatusRegistroMedicoEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {

            return null;
        }
    }
}
