package com.upsaude.util.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.upsaude.exception.InvalidArgumentException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Locale;

/**
 * Deserializer genérico base para enums que seguem o padrão:
 * - fromCodigo(Integer codigo)
 * - fromDescricao(String descricao)
 * - valueOf(String name)
 * 
 * Lança InvalidArgumentException quando o valor é inválido, em vez de retornar null.
 * 
 * @param <E> Tipo do enum
 */
public abstract class BaseEnumDeserializer<E extends Enum<E>> extends JsonDeserializer<E> {

    private final Class<E> enumClass;
    private Method fromCodigoMethod;
    private Method fromDescricaoMethod;

    protected BaseEnumDeserializer(Class<E> enumClass) {
        this.enumClass = enumClass;
        try {
            this.fromCodigoMethod = enumClass.getMethod("fromCodigo", Integer.class);
            this.fromDescricaoMethod = enumClass.getMethod("fromDescricao", String.class);
        } catch (NoSuchMethodException e) {
            // Métodos opcionais, podem não existir em todos os enums
        }
    }

    @Override
    public E deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        // Tenta deserializar como número (código)
        if (p.getCurrentToken().isNumeric()) {
            Integer codigo = p.getIntValue();
            E result = deserializeFromCodigo(codigo);
            if (result != null) {
                return result;
            }
            throw new InvalidArgumentException(
                String.format("Valor inválido para %s: código '%d'. %s", 
                    enumClass.getSimpleName(), codigo, getValidValuesMessage())
            );
        }

        // Tenta deserializar como string
        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        String strValue = value.trim();

        // Tenta como número (string que representa código)
        try {
            Integer codigo = Integer.parseInt(strValue);
            E result = deserializeFromCodigo(codigo);
            if (result != null) {
                return result;
            }
            throw new InvalidArgumentException(
                String.format("Valor inválido para %s: código '%d'. %s", 
                    enumClass.getSimpleName(), codigo, getValidValuesMessage())
            );
        } catch (NumberFormatException e) {
            // Não é número, continua para tentar como string
        }

        // Tenta como descrição
        E result = deserializeFromDescricao(strValue);
        if (result != null) {
            return result;
        }

        // Tenta como nome do enum (valueOf)
        try {
            return Enum.valueOf(enumClass, strValue.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException(
                String.format("Valor inválido para %s: '%s'. %s", 
                    enumClass.getSimpleName(), strValue, getValidValuesMessage())
            );
        }
    }

    private E deserializeFromCodigo(Integer codigo) {
        if (fromCodigoMethod == null || codigo == null) {
            return null;
        }
        try {
            @SuppressWarnings("unchecked")
            E result = (E) fromCodigoMethod.invoke(null, codigo);
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    private E deserializeFromDescricao(String descricao) {
        if (fromDescricaoMethod == null || descricao == null || descricao.trim().isEmpty()) {
            return null;
        }
        try {
            @SuppressWarnings("unchecked")
            E result = (E) fromDescricaoMethod.invoke(null, descricao);
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    private String getValidValuesMessage() {
        try {
            E[] values = enumClass.getEnumConstants();
            if (values != null && values.length > 0) {
                String validValues = Arrays.stream(values)
                    .map(Enum::name)
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("");
                return "Valores válidos: " + validValues;
            }
        } catch (Exception e) {
            // Ignora erros ao obter valores
        }
        return "Verifique os valores válidos do enum.";
    }
}
