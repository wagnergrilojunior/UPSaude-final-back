package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.TipoEstabelecimentoEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class TipoEstabelecimentoEnumDeserializer extends JsonDeserializer<TipoEstabelecimentoEnum> {
    @Override
    public TipoEstabelecimentoEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        // Se for número inteiro, tenta usar como código
        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            TipoEstabelecimentoEnum result = TipoEstabelecimentoEnum.fromCodigo(codigo);
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
            TipoEstabelecimentoEnum result = TipoEstabelecimentoEnum.fromCodigo(codigo);
            if (result != null) {
                return result;
            }
        } catch (NumberFormatException e) {
            // Não é número, continua para tentar como string
        }
        
        // Tenta por descrição
        TipoEstabelecimentoEnum result = TipoEstabelecimentoEnum.fromDescricao(strValue);
        if (result != null) {
            return result;
        }
        
        // Tenta por nome do enum (case-insensitive)
        try {
            return TipoEstabelecimentoEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Se não encontrar, lança exceção para forçar validação
            throw new InvalidArgumentException(
                String.format("Valor inválido para TipoEstabelecimentoEnum: '%s'. Valores válidos: HOSPITAL, CLINICA, POSTO_SAUDE, UBS, UPA, LABORATORIO, FARMACIA, OUTRO", strValue)
            );
        }
    }
}
