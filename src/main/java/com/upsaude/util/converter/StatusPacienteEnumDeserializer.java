package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.StatusPacienteEnum;
import java.io.IOException;

public class StatusPacienteEnumDeserializer extends JsonDeserializer<StatusPacienteEnum> {
    @Override
    public StatusPacienteEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            StatusPacienteEnum result = StatusPacienteEnum.fromCodigo(codigo);
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
            StatusPacienteEnum result = StatusPacienteEnum.fromCodigo(codigo);
            if (result != null) {
                return result;
            }
        } catch (NumberFormatException e) {

        }

        StatusPacienteEnum result = StatusPacienteEnum.fromDescricao(strValue);
        if (result != null) {
            return result;
        }

        try {
            return StatusPacienteEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {

            return null;
        }
    }
}
