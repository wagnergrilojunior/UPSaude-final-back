package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.OrientacaoSexualEnum;
import java.io.IOException;

public class OrientacaoSexualEnumDeserializer extends JsonDeserializer<OrientacaoSexualEnum> {
    @Override
    public OrientacaoSexualEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            OrientacaoSexualEnum result = OrientacaoSexualEnum.fromCodigo(codigo);
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
            OrientacaoSexualEnum result = OrientacaoSexualEnum.fromCodigo(codigo);
            if (result != null) {
                return result;
            }
        } catch (NumberFormatException e) {

        }

        OrientacaoSexualEnum result = OrientacaoSexualEnum.fromDescricao(strValue);
        if (result != null) {
            return result;
        }

        try {
            return OrientacaoSexualEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {

            return null;
        }
    }
}
