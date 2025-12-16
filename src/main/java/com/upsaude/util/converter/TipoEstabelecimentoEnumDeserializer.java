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

        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            TipoEstabelecimentoEnum result = TipoEstabelecimentoEnum.fromCodigo(codigo);
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
            TipoEstabelecimentoEnum result = TipoEstabelecimentoEnum.fromCodigo(codigo);
            if (result != null) {
                return result;
            }
        } catch (NumberFormatException e) {

        }

        TipoEstabelecimentoEnum result = TipoEstabelecimentoEnum.fromDescricao(strValue);
        if (result != null) {
            return result;
        }

        try {
            return TipoEstabelecimentoEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {

            throw new InvalidArgumentException(
                String.format("Valor inválido para TipoEstabelecimentoEnum: '%s'. Valores válidos: HOSPITAL, CLINICA, POSTO_SAUDE, UBS, UPA, LABORATORIO, FARMACIA, OUTRO", strValue)
            );
        }
    }
}
