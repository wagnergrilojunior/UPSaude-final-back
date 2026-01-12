package com.upsaude.util.converter;

import com.upsaude.enums.TipoAgendamentoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class TipoAgendamentoEnumConverter implements AttributeConverter<TipoAgendamentoEnum, String> {

    @Override
    public String convertToDatabaseColumn(TipoAgendamentoEnum tipo) {
        if (tipo == null) {
            return null;
        }
        return tipo.getCodigoRnds();
    }

    @Override
    public TipoAgendamentoEnum convertToEntityAttribute(String codigoRnds) {
        if (codigoRnds == null) {
            return null;
        }

        return Stream.of(TipoAgendamentoEnum.values())
                .filter(c -> c.getCodigoRnds().equals(codigoRnds))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
