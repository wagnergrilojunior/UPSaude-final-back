package com.upsaude.util.converter;

import com.upsaude.enums.ClasseAtendimentoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class ClasseAtendimentoEnumConverter implements AttributeConverter<ClasseAtendimentoEnum, String> {

    @Override
    public String convertToDatabaseColumn(ClasseAtendimentoEnum classe) {
        if (classe == null) {
            return null;
        }
        return classe.getCodigoRnds();
    }

    @Override
    public ClasseAtendimentoEnum convertToEntityAttribute(String codigoRnds) {
        if (codigoRnds == null) {
            return null;
        }

        return Stream.of(ClasseAtendimentoEnum.values())
                .filter(c -> c.getCodigoRnds().equals(codigoRnds))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
