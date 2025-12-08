package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.upsaude.enums.TipoAtendimentoPreferencialEnum;
import java.io.IOException;

/**
 * Serializador customizado para TipoAtendimentoPreferencialEnum.
 * Retorna a descrição do enum ao invés do nome.
 */
public class TipoAtendimentoPreferencialEnumSerializer extends JsonSerializer<TipoAtendimentoPreferencialEnum> {

    @Override
    public void serialize(TipoAtendimentoPreferencialEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeString(value.getDescricao());
        }
    }
}
