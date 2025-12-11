package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.ZonaDomicilioEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class ZonaDomicilioEnumDeserializer extends JsonDeserializer<ZonaDomicilioEnum> {
    @Override
    public ZonaDomicilioEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        // Se for número inteiro, tenta usar como código
        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            ZonaDomicilioEnum result = ZonaDomicilioEnum.fromCodigo(codigo);
            if (result != null) {
                return result;
            }
        }
        
        // Se for string, tenta converter
        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        
        String strValue = value.trim();
        
        // Tenta como número (código) se for string numérica
        try {
            Integer codigo = Integer.parseInt(strValue);
            ZonaDomicilioEnum result = ZonaDomicilioEnum.fromCodigo(codigo);
            if (result != null) {
                return result;
            }
        } catch (NumberFormatException e) {
            // Não é número, continua para tentar como string
        }
        
        // Tenta por descrição
        ZonaDomicilioEnum result = ZonaDomicilioEnum.fromDescricao(strValue);
        if (result != null) {
            return result;
        }
        
        // Tenta por nome do enum (case-insensitive)
        try {
            return ZonaDomicilioEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Se não encontrar, lança exceção para forçar validação
            throw new InvalidArgumentException(
                String.format("Valor inválido para ZonaDomicilioEnum: '%s'. Valores válidos: URBANA, RURAL, PERIURBANA", strValue)
            );
        }
    }
}
