package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.upsaude.enums.OrientacaoSexualEnum;
import java.io.IOException;

/**
 * Serializador customizado para OrientacaoSexualEnum.
 * Retorna a descrição do enum ao invés do nome.
 */
public class OrientacaoSexualEnumSerializer extends JsonSerializer<OrientacaoSexualEnum> {

    @Override
    public void serialize(OrientacaoSexualEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeString(value.getDescricao());
        }
    }
}
