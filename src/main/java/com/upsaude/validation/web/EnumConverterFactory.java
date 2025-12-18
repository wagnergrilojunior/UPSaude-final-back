package com.upsaude.validation.web;

import com.upsaude.exception.InvalidArgumentException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Conversão global e genérica de String -> Enum para query/path variables.
 * - case-insensitive
 * - erro padronizado via InvalidArgumentException (HTTP 400)
 */
public class EnumConverterFactory implements ConverterFactory<String, Enum> {

    @Override
    public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToEnumConverter<>(targetType);
    }

    private static final class StringToEnumConverter<T extends Enum<T>> implements Converter<String, T> {
        private final Class<T> enumType;

        private StringToEnumConverter(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(String source) {
            if (source == null) return null;
            String value = source.trim();
            if (value.isEmpty()) return null;

            // tenta por nome (case-insensitive)
            for (T c : enumType.getEnumConstants()) {
                if (c.name().equalsIgnoreCase(value)) {
                    return c;
                }
            }

            // tenta por toString() (caso enums usem descricao)
            String lower = value.toLowerCase(Locale.ROOT);
            for (T c : enumType.getEnumConstants()) {
                if (String.valueOf(c).toLowerCase(Locale.ROOT).equals(lower)) {
                    return c;
                }
            }

            String valores = Arrays.stream(enumType.getEnumConstants())
                .map(Object::toString)
                .collect(Collectors.joining(", "));

            throw new InvalidArgumentException(
                String.format("Valor inválido para %s: '%s'. Valores válidos: %s",
                    enumType.getSimpleName(), value, valores)
            );
        }
    }
}

