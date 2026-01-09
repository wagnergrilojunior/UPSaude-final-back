package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.upsaude.enums.TipoDeficienciaEnum;

import java.io.IOException;

public class TipoDeficienciaEnumSerializer extends JsonSerializer<TipoDeficienciaEnum> {

    @Override
    public void serialize(TipoDeficienciaEnum value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeNumber(value.getCodigo());
        }
    }
}
