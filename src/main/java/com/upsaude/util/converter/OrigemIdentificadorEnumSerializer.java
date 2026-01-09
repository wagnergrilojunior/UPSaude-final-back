package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.upsaude.enums.OrigemIdentificadorEnum;

import java.io.IOException;

public class OrigemIdentificadorEnumSerializer extends JsonSerializer<OrigemIdentificadorEnum> {

    @Override
    public void serialize(OrigemIdentificadorEnum value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeNumber(value.getCodigo());
        }
    }
}
