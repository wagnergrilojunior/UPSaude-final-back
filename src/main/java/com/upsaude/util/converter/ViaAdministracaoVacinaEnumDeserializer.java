package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.ViaAdministracaoVacinaEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class ViaAdministracaoVacinaEnumDeserializer extends JsonDeserializer<ViaAdministracaoVacinaEnum> {
    @Override
    public ViaAdministracaoVacinaEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            ViaAdministracaoVacinaEnum result = ViaAdministracaoVacinaEnum.fromCodigo(codigo);
            if (result != null) return result;
        }
        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) return null;
        String strValue = value.trim();
        try {
            Integer codigo = Integer.parseInt(strValue);
            ViaAdministracaoVacinaEnum result = ViaAdministracaoVacinaEnum.fromCodigo(codigo);
            if (result != null) return result;
        } catch (NumberFormatException e) {}
        ViaAdministracaoVacinaEnum result = ViaAdministracaoVacinaEnum.fromDescricao(strValue);
        if (result != null) return result;
        try {
            return ViaAdministracaoVacinaEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException("Valor inválido para ViaAdministracaoVacinaEnum: '" + strValue + "'");
        }
    }
}
