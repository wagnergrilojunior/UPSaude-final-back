package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.upsaude.enums.TipoLogradouroEnum;
import java.io.IOException;

public class TipoLogradouroEnumSerializer extends JsonSerializer<TipoLogradouroEnum> {

    @Override
    public void serialize(TipoLogradouroEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeString(value.getDescricao());
        }
    }
}
