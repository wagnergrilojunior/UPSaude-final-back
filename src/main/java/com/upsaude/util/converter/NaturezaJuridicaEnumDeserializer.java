package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.NaturezaJuridicaEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class NaturezaJuridicaEnumDeserializer extends JsonDeserializer<NaturezaJuridicaEnum> {
    @Override
    public NaturezaJuridicaEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        // Se for número inteiro, tenta usar como código
        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            NaturezaJuridicaEnum result = NaturezaJuridicaEnum.fromCodigo(codigo);
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
            NaturezaJuridicaEnum result = NaturezaJuridicaEnum.fromCodigo(codigo);
            if (result != null) {
                return result;
            }
        } catch (NumberFormatException e) {
            // Não é número, continua para tentar como string
        }
        
        // Tenta por descrição
        NaturezaJuridicaEnum result = NaturezaJuridicaEnum.fromDescricao(strValue);
        if (result != null) {
            return result;
        }
        
        // Tenta por nome do enum (case-insensitive)
        try {
            return NaturezaJuridicaEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Se não encontrar, lança exceção para forçar validação
            throw new InvalidArgumentException(
                String.format("Valor inválido para NaturezaJuridicaEnum: '%s'. Valores válidos: ADMINISTRACAO_PUBLICA_DIRETA, ADMINISTRACAO_PUBLICA_INDIRETA, ENTIDADE_EMPRESARIAL_PUBLICA, SOCIEDADE_ECONOMIA_MISTA, ORGANIZACAO_SOCIAL, ENTIDADE_FILANTROPICA, EMPRESA_PRIVADA, COOPERATIVA, ASSOCIACAO, FUNDACAO, OUTRO", strValue)
            );
        }
    }
}
