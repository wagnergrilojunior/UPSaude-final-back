package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.upsaude.enums.RacaCorEnum;
import java.io.IOException;

public class RacaCorEnumSerializer extends JsonSerializer<RacaCorEnum> {

    @Override
    public void serialize(RacaCorEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeNumber(value.getCodigo());
        }
    }
}
