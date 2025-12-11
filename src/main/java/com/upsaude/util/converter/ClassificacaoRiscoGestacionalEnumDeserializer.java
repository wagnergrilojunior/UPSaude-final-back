package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.ClassificacaoRiscoGestacionalEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class ClassificacaoRiscoGestacionalEnumDeserializer extends JsonDeserializer<ClassificacaoRiscoGestacionalEnum> {
    @Override
    public ClassificacaoRiscoGestacionalEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            ClassificacaoRiscoGestacionalEnum result = ClassificacaoRiscoGestacionalEnum.fromCodigo(codigo);
            if (result != null) return result;
        }
        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) return null;
        String strValue = value.trim();
        try {
            Integer codigo = Integer.parseInt(strValue);
            ClassificacaoRiscoGestacionalEnum result = ClassificacaoRiscoGestacionalEnum.fromCodigo(codigo);
            if (result != null) return result;
        } catch (NumberFormatException e) {}
        ClassificacaoRiscoGestacionalEnum result = ClassificacaoRiscoGestacionalEnum.fromDescricao(strValue);
        if (result != null) return result;
        try {
            return ClassificacaoRiscoGestacionalEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException("Valor inválido para ClassificacaoRiscoGestacionalEnum: '" + strValue + "'");
        }
    }
}
