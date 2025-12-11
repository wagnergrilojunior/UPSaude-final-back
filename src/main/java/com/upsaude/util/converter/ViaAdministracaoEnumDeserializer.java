package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.ViaAdministracaoEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class ViaAdministracaoEnumDeserializer extends JsonDeserializer<ViaAdministracaoEnum> {
    @Override
    public ViaAdministracaoEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            ViaAdministracaoEnum result = ViaAdministracaoEnum.fromCodigo(codigo);
            if (result != null) return result;
        }
        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) return null;
        String strValue = value.trim();
        try {
            Integer codigo = Integer.parseInt(strValue);
            ViaAdministracaoEnum result = ViaAdministracaoEnum.fromCodigo(codigo);
            if (result != null) return result;
        } catch (NumberFormatException e) {}
        ViaAdministracaoEnum result = ViaAdministracaoEnum.fromDescricao(strValue);
        if (result != null) return result;
        try {
            return ViaAdministracaoEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException("Valor inválido para ViaAdministracaoEnum: '" + strValue + "'");
        }
    }
}
