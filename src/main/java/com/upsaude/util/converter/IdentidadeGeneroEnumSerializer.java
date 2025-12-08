package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.upsaude.enums.IdentidadeGeneroEnum;
import java.io.IOException;

/**
 * Serializador customizado para IdentidadeGeneroEnum.
 * Retorna a descrição do enum ao invés do nome.
 */
public class IdentidadeGeneroEnumSerializer extends JsonSerializer<IdentidadeGeneroEnum> {

    @Override
    public void serialize(IdentidadeGeneroEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeString(value.getDescricao());
        }
    }
}
