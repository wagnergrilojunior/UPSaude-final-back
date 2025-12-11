package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class StatusAtivoEnumDeserializer extends JsonDeserializer<StatusAtivoEnum> {
    @Override
    public StatusAtivoEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            StatusAtivoEnum result = StatusAtivoEnum.fromCodigo(codigo);
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
            StatusAtivoEnum result = StatusAtivoEnum.fromCodigo(codigo);
            if (result != null) {
                return result;
            }
        } catch (NumberFormatException e) {

        }

        StatusAtivoEnum result = StatusAtivoEnum.fromDescricao(strValue);
        if (result != null) {
            return result;
        }

        try {
            return StatusAtivoEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {

            throw new InvalidArgumentException(
                String.format("Valor inválido para StatusAtivoEnum: '%s'. Valores válidos: ATIVO, SUSPENSO, INATIVO", strValue)
            );
        }
    }
}
