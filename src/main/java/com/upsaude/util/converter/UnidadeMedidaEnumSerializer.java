package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.upsaude.enums.UnidadeMedidaEnum;
import java.io.IOException;

public class UnidadeMedidaEnumSerializer extends JsonSerializer<UnidadeMedidaEnum> {

    @Override
    public void serialize(UnidadeMedidaEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeString(value.getDescricao());
        }
    }
}

