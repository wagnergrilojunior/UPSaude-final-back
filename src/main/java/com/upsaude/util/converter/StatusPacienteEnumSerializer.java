package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.upsaude.enums.StatusPacienteEnum;
import java.io.IOException;

/**
 * Serializador customizado para StatusPacienteEnum.
 * Retorna a descrição do enum ao invés do nome.
 */
public class StatusPacienteEnumSerializer extends JsonSerializer<StatusPacienteEnum> {

    @Override
    public void serialize(StatusPacienteEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeString(value.getDescricao());
        }
    }
}
