package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.upsaude.enums.TipoControleMedicamentoEnum;
import java.io.IOException;

public class TipoControleMedicamentoEnumSerializer extends JsonSerializer<TipoControleMedicamentoEnum> {

    @Override
    public void serialize(TipoControleMedicamentoEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeString(value.getDescricao());
        }
    }
}

