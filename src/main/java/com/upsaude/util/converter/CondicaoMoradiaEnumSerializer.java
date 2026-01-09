package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.upsaude.enums.CondicaoMoradiaEnum;

import java.io.IOException;

public class CondicaoMoradiaEnumSerializer extends JsonSerializer<CondicaoMoradiaEnum> {

    @Override
    public void serialize(CondicaoMoradiaEnum value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeNumber(value.getCodigo());
        }
    }
}
