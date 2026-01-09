package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.upsaude.enums.SituacaoFamiliarEnum;

import java.io.IOException;

public class SituacaoFamiliarEnumSerializer extends JsonSerializer<SituacaoFamiliarEnum> {

    @Override
    public void serialize(SituacaoFamiliarEnum value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeNumber(value.getCodigo());
        }
    }
}
