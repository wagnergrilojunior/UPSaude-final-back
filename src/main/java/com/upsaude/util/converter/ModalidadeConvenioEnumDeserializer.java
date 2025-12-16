package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.ModalidadeConvenioEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class ModalidadeConvenioEnumDeserializer extends JsonDeserializer<ModalidadeConvenioEnum> {
    @Override
    public ModalidadeConvenioEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            ModalidadeConvenioEnum result = ModalidadeConvenioEnum.fromCodigo(codigo);
            if (result != null) return result;
        }
        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) return null;
        String strValue = value.trim();
        try {
            Integer codigo = Integer.parseInt(strValue);
            ModalidadeConvenioEnum result = ModalidadeConvenioEnum.fromCodigo(codigo);
            if (result != null) return result;
        } catch (NumberFormatException e) {}
        ModalidadeConvenioEnum result = ModalidadeConvenioEnum.fromDescricao(strValue);
        if (result != null) return result;
        try {
            return ModalidadeConvenioEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException("Valor inválido para ModalidadeConvenioEnum: '" + strValue + "'");
        }
    }
}
