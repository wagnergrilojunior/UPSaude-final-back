package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.enums.SituacaoFamiliarEnum;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;

public class SituacaoFamiliarEnumDeserializer extends JsonDeserializer<SituacaoFamiliarEnum> {
    @Override
    public SituacaoFamiliarEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            SituacaoFamiliarEnum result = SituacaoFamiliarEnum.fromCodigo(codigo);
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
            SituacaoFamiliarEnum result = SituacaoFamiliarEnum.fromCodigo(codigo);
            if (result != null) {
                return result;
            }
        } catch (NumberFormatException e) {

        }

        SituacaoFamiliarEnum result = SituacaoFamiliarEnum.fromDescricao(strValue);
        if (result != null) {
            return result;
        }

        try {
            return SituacaoFamiliarEnum.valueOf(strValue.toUpperCase());
        } catch (IllegalArgumentException e) {

            throw new InvalidArgumentException(
                String.format("Valor inválido para SituacaoFamiliarEnum: '%s'. Valores válidos: SOZINHO, COM_FAMILIA, INSTITUCIONALIZADO, RUA, OUTRO, IGNORADO, NAO_INFORMADO", strValue)
            );
        }
    }
}
