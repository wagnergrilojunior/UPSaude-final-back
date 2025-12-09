package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.TipoDeficienciaEnum;
import java.io.IOException;

public class TipoDeficienciaEnumDeserializer extends JsonDeserializer<TipoDeficienciaEnum> {
    @Override
    public TipoDeficienciaEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        // Se for número inteiro, tenta usar como código
        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            TipoDeficienciaEnum result = TipoDeficienciaEnum.fromCodigo(codigo);
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
            TipoDeficienciaEnum result = TipoDeficienciaEnum.fromCodigo(codigo);
            if (result != null) {
                return result;
            }
        } catch (NumberFormatException e) {
            // Não é número, continua para tentar como string
        }
        
        // Tenta por descrição
        TipoDeficienciaEnum result = TipoDeficienciaEnum.fromDescricao(strValue);
        if (result != null) {
            return result;
        }
        
        // Tenta por nome do enum (case-insensitive)
        try {
            return TipoDeficienciaEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Se não encontrar, retorna null (não lança exceção)
            return null;
        }
    }
}
