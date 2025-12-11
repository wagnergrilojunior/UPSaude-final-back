package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.EstadoCivilEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class EstadoCivilEnumDeserializer extends JsonDeserializer<EstadoCivilEnum> {
    @Override
    public EstadoCivilEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            EstadoCivilEnum result = EstadoCivilEnum.fromCodigo(codigo);
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
            EstadoCivilEnum result = EstadoCivilEnum.fromCodigo(codigo);
            if (result != null) {
                return result;
            }
        } catch (NumberFormatException e) {

        }

        EstadoCivilEnum result = EstadoCivilEnum.fromDescricao(strValue);
        if (result != null) {
            return result;
        }

        try {
            return EstadoCivilEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {

            throw new InvalidArgumentException(
                String.format("Valor inválido para EstadoCivilEnum: '%s'. Valores válidos: SOLTEIRO, CASADO, DIVORCIADO, VIUVO, SEPARADO, UNIAO_ESTAVEL", strValue)
            );
        }
    }
}
