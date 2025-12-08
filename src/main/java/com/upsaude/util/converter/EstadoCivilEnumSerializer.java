package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.upsaude.enums.EstadoCivilEnum;
import java.io.IOException;

/**
 * Serializador customizado para EstadoCivilEnum.
 * Retorna a descrição do enum ao invés do nome.
 */
public class EstadoCivilEnumSerializer extends JsonSerializer<EstadoCivilEnum> {

    @Override
    public void serialize(EstadoCivilEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeString(value.getDescricao());
        }
    }
}
