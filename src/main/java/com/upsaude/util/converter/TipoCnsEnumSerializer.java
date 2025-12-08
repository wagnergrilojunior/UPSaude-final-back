package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.upsaude.enums.TipoCnsEnum;
import java.io.IOException;

/**
 * Serializador customizado para TipoCnsEnum.
 * Retorna a descrição do enum ao invés do nome.
 */
public class TipoCnsEnumSerializer extends JsonSerializer<TipoCnsEnum> {

    @Override
    public void serialize(TipoCnsEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeString(value.getDescricao());
        }
    }
}
