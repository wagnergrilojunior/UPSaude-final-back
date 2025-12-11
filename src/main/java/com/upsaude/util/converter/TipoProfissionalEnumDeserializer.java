package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.TipoProfissionalEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class TipoProfissionalEnumDeserializer extends JsonDeserializer<TipoProfissionalEnum> {
    @Override
    public TipoProfissionalEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            TipoProfissionalEnum result = TipoProfissionalEnum.fromCodigo(codigo);
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
            TipoProfissionalEnum result = TipoProfissionalEnum.fromCodigo(codigo);
            if (result != null) {
                return result;
            }
        } catch (NumberFormatException e) {

        }

        TipoProfissionalEnum result = TipoProfissionalEnum.fromDescricao(strValue);
        if (result != null) {
            return result;
        }

        try {
            return TipoProfissionalEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {

            throw new InvalidArgumentException(
                String.format("Valor inválido para TipoProfissionalEnum: '%s'. Use um valor válido do enum.", strValue)
            );
        }
    }
}
