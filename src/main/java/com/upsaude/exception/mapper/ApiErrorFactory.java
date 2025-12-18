package com.upsaude.exception.mapper;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.upsaude.exception.InvalidArgumentException;
import com.upsaude.exception.model.ApiErrorCode;
import com.upsaude.exception.model.ApiErrorResponse;
import com.upsaude.exception.model.ApiFieldError;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public final class ApiErrorFactory {

    private ApiErrorFactory() {}

    public static ApiErrorResponse badRequest(String path, ApiErrorCode code, String titulo, String mensagem) {
        ApiErrorResponse r = ApiErrorResponse.of(HttpStatus.BAD_REQUEST.value(), path);
        r.setCodigo(code != null ? code : ApiErrorCode.BAD_REQUEST);
        r.setMensagensCompat(titulo != null ? titulo : "Requisição Inválida", mensagem);
        return r;
    }

    public static ApiErrorResponse fromMissingParameter(String path, MissingServletRequestParameterException ex) {
        String msg = String.format("Parâmetro obrigatório ausente: '%s'", ex.getParameterName());
        ApiErrorResponse r = badRequest(path, ApiErrorCode.MISSING_PARAMETER, "Requisição Inválida", msg);
        r.addError(new ApiFieldError(ex.getParameterName(), msg, ApiErrorCode.MISSING_PARAMETER, null));
        return r;
    }

    public static ApiErrorResponse fromTypeMismatch(String path, MethodArgumentTypeMismatchException ex) {
        String nome = ex.getName();
        Object valor = ex.getValue();
        Class<?> required = ex.getRequiredType();
        String valorRecebido = valor != null ? valor.toString() : "null";

        ApiErrorCode code = ApiErrorCode.INVALID_TYPE;
        String msg;

        if (required != null && UUID.class.equals(required)) {
            code = ApiErrorCode.INVALID_UUID;
            msg = String.format("ID inválido: '%s'. O ID deve ser um UUID válido no formato xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx", valorRecebido);
        } else if (required != null && required.isEnum()) {
            code = ApiErrorCode.INVALID_ENUM;
            msg = String.format("Valor inválido para '%s': '%s'. Valores válidos: %s",
                nome, valorRecebido, valoresEnum(required));
        } else if (required != null && isData(required)) {
            code = ApiErrorCode.INVALID_DATE;
            msg = String.format("Data inválida para '%s': '%s'", nome, valorRecebido);
        } else if (required != null && Number.class.isAssignableFrom(required)) {
            code = ApiErrorCode.INVALID_NUMBER;
            msg = String.format("Número inválido para '%s': '%s'", nome, valorRecebido);
        } else {
            String tipoEsperado = required != null ? required.getSimpleName() : "desconhecido";
            msg = String.format("Parâmetro '%s' com valor '%s' não pode ser convertido para %s", nome, valorRecebido, tipoEsperado);
        }

        ApiErrorResponse r = badRequest(path, code, "Requisição Inválida", msg);
        r.addError(new ApiFieldError(nome, msg, code, valorRecebido));
        return r;
    }

    public static ApiErrorResponse fromBindException(String path, BindException ex) {
        ApiErrorResponse r = badRequest(path, ApiErrorCode.BIND_ERROR, "Requisição Inválida", "Erro de validação");
        if (ex.getBindingResult() != null) {
            for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
                String campo = fe.getField();
                String msg = fe.getDefaultMessage() != null ? fe.getDefaultMessage() : "Erro de validação";
                r.addError(new ApiFieldError(campo, msg, ApiErrorCode.BIND_ERROR, fe.getRejectedValue()));
            }
        }
        return r;
    }

    public static ApiErrorResponse fromConstraintViolation(String path, ConstraintViolationException ex) {
        ApiErrorResponse r = badRequest(path, ApiErrorCode.CONSTRAINT_VIOLATION, "Requisição Inválida", "Erro de validação");
        for (ConstraintViolation<?> v : ex.getConstraintViolations()) {
            String campo = v.getPropertyPath() != null ? v.getPropertyPath().toString() : "param";
            String msg = v.getMessage();
            Object rejected = v.getInvalidValue();
            r.addError(new ApiFieldError(campo, msg, ApiErrorCode.CONSTRAINT_VIOLATION, rejected));
        }
        return r;
    }

    /**
     * Converte erros de JSON (Jackson) para códigos padronizados + campo afetado quando possível.
     */
    public static ApiErrorResponse fromJackson(String path, Throwable rootCause) {
        if (rootCause instanceof JsonParseException) {
            return badRequest(path, ApiErrorCode.INVALID_JSON, "Requisição Inválida",
                "JSON inválido. Verifique a estrutura e tente novamente.");
        }

        if (rootCause instanceof UnrecognizedPropertyException upe) {
            String campo = upe.getPropertyName();
            String msg = String.format("Campo não reconhecido: '%s'", campo);
            ApiErrorResponse r = badRequest(path, ApiErrorCode.INVALID_REQUEST_BODY, "Requisição Inválida", msg);
            r.addError(new ApiFieldError(campo, msg, ApiErrorCode.INVALID_REQUEST_BODY, null));
            return r;
        }

        if (rootCause instanceof InvalidDefinitionException ide) {
            // Erro de definição/serialização normalmente não é culpa do cliente, mas pode ocorrer como estrutura inválida.
            return badRequest(path, ApiErrorCode.INVALID_REQUEST_BODY, "Requisição Inválida",
                "Não foi possível processar o corpo da requisição.");
        }

        if (rootCause instanceof InvalidFormatException ife) {
            Class<?> target = ife.getTargetType();
            String campo = caminhoJackson(ife);
            Object valor = ife.getValue();

            ApiErrorCode code = ApiErrorCode.INVALID_TYPE;
            String msg = "Tipo inválido no corpo da requisição.";

            if (target != null && target.isEnum()) {
                code = ApiErrorCode.INVALID_ENUM;
                msg = String.format("Valor inválido para '%s': '%s'. Valores válidos: %s",
                    campoOrFallback(campo), String.valueOf(valor), valoresEnum(target));
            } else if (target != null && UUID.class.equals(target)) {
                code = ApiErrorCode.INVALID_UUID;
                msg = String.format("UUID inválido para '%s': '%s'", campoOrFallback(campo), String.valueOf(valor));
            } else if (target != null && isData(target)) {
                code = ApiErrorCode.INVALID_DATE;
                msg = String.format("Data inválida para '%s': '%s'", campoOrFallback(campo), String.valueOf(valor));
            } else if (target != null && isNumero(target)) {
                code = ApiErrorCode.INVALID_NUMBER;
                msg = String.format("Número inválido para '%s': '%s'", campoOrFallback(campo), String.valueOf(valor));
            }

            ApiErrorResponse r = badRequest(path, code, "Requisição Inválida", msg);
            if (campo != null) {
                r.addError(new ApiFieldError(campo, msg, code, valor));
            }
            return r;
        }

        if (rootCause instanceof MismatchedInputException mie) {
            String campo = caminhoJackson(mie);
            String msg = "Corpo da requisição inválido. Verifique os campos enviados.";
            ApiErrorResponse r = badRequest(path, ApiErrorCode.INVALID_REQUEST_BODY, "Requisição Inválida", msg);
            if (campo != null) {
                r.addError(new ApiFieldError(campo, msg, ApiErrorCode.INVALID_REQUEST_BODY, null));
            }
            return r;
        }

        // Caso Jackson encapsule uma InvalidArgumentException (deserializers atuais) como causa raiz
        if (rootCause instanceof InvalidArgumentException iae) {
            return fromInvalidArgument(path, iae);
        }

        // fallback Jackson
        return badRequest(path, ApiErrorCode.INVALID_REQUEST_BODY, "Requisição Inválida",
            "Erro ao processar o JSON da requisição. Verifique os tipos e formatos enviados.");
    }

    public static ApiErrorResponse fromInvalidArgument(String path, InvalidArgumentException ex) {
        // Heurística simples: se mensagem já indica enum, mapeia como INVALID_ENUM.
        ApiErrorCode code = ApiErrorCode.INVALID_ARGUMENT;
        String msg = ex.getMessage() != null ? ex.getMessage() : "Argumento inválido.";
        if (msg.toLowerCase().contains("paginação inválida")) {
            code = ApiErrorCode.INVALID_PAGINATION;
        }
        if (msg.toLowerCase().contains("valores válidos") || msg.toLowerCase().contains("enum")) {
            code = ApiErrorCode.INVALID_ENUM;
        }
        return badRequest(path, code, "Requisição Inválida", msg);
    }

    private static boolean isData(Class<?> c) {
        return c != null && (LocalDate.class.equals(c) || OffsetDateTime.class.equals(c) || Instant.class.equals(c));
    }

    private static boolean isNumero(Class<?> c) {
        return c != null && (Number.class.isAssignableFrom(c)
            || c.equals(int.class) || c.equals(long.class) || c.equals(double.class) || c.equals(float.class));
    }

    private static String valoresEnum(Class<?> enumType) {
        if (enumType == null || !enumType.isEnum()) return "";
        Object[] constants = enumType.getEnumConstants();
        if (constants == null) return "";
        return Arrays.stream(constants).map(Object::toString).collect(Collectors.joining(", "));
    }

    private static String caminhoJackson(JsonMappingException ex) {
        if (ex == null) return null;
        List<JsonMappingException.Reference> path = ex.getPath();
        if (path == null || path.isEmpty()) return null;
        return path.stream()
            .map(ref -> ref.getFieldName() != null ? ref.getFieldName() : String.valueOf(ref.getIndex()))
            .collect(Collectors.joining("."));
    }

    private static String campoOrFallback(String campo) {
        return (campo == null || campo.isBlank()) ? "campo" : campo;
    }
}

