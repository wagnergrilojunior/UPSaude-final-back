package com.upsaude.util.converter;

import com.upsaude.enums.TipoServicoAgendamentoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class TipoServicoAgendamentoEnumConverter implements AttributeConverter<TipoServicoAgendamentoEnum, String> {

    @Override
    public String convertToDatabaseColumn(TipoServicoAgendamentoEnum tipo) {
        if (tipo == null) {
            return null;
        }
        return tipo.name();
    }

    @Override
    public TipoServicoAgendamentoEnum convertToEntityAttribute(String name) {
        if (name == null) {
            return null;
        }

        return Stream.of(TipoServicoAgendamentoEnum.values())
                .filter(c -> c.name().equals(name))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
