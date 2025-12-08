package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.upsaude.enums.SexoEnum;
import java.io.IOException;

/**
 * Serializador customizado para SexoEnum.
 * Retorna a descrição do enum ao invés do nome.
 */
public class SexoEnumSerializer extends JsonSerializer<SexoEnum> {

    @Override
    public void serialize(SexoEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeString(value.getDescricao());
        }
    }
}
