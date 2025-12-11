package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.TipoDeficienciaEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class TipoDeficienciaEnumDeserializer extends JsonDeserializer<TipoDeficienciaEnum> {
    @Override
    public TipoDeficienciaEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            TipoDeficienciaEnum result = TipoDeficienciaEnum.fromCodigo(codigo);
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
            TipoDeficienciaEnum result = TipoDeficienciaEnum.fromCodigo(codigo);
            if (result != null) {
                return result;
            }
        } catch (NumberFormatException e) {

        }

        TipoDeficienciaEnum result = TipoDeficienciaEnum.fromDescricao(strValue);
        if (result != null) {
            return result;
        }

        try {
            return TipoDeficienciaEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {

            throw new InvalidArgumentException(
                String.format("Valor inválido para TipoDeficienciaEnum: '%s'. Use um valor válido do enum.", strValue)
            );
        }
    }
}
