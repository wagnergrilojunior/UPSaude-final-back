package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.ClasseTerapeuticaEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class ClasseTerapeuticaEnumDeserializer extends JsonDeserializer<ClasseTerapeuticaEnum> {
    @Override
    public ClasseTerapeuticaEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            ClasseTerapeuticaEnum result = ClasseTerapeuticaEnum.fromCodigo(codigo);
            if (result != null) return result;
        }
        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) return null;
        String strValue = value.trim();
        try {
            Integer codigo = Integer.parseInt(strValue);
            ClasseTerapeuticaEnum result = ClasseTerapeuticaEnum.fromCodigo(codigo);
            if (result != null) return result;
        } catch (NumberFormatException e) {}
        ClasseTerapeuticaEnum result = ClasseTerapeuticaEnum.fromDescricao(strValue);
        if (result != null) return result;
        try {
            return ClasseTerapeuticaEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException("Valor inválido para ClasseTerapeuticaEnum: '" + strValue + "'");
        }
    }
}
