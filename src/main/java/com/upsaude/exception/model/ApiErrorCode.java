package com.upsaude.exception.model;

/**
 * Códigos estáveis de erro para consumo do front e logs.
 * Mantém nomes em maiúsculas (snake case) para uso consistente.
 */
public enum ApiErrorCode {
    INVALID_JSON,
    INVALID_REQUEST_BODY,
    INVALID_TYPE,
    INVALID_ENUM,
    INVALID_UUID,
    INVALID_DATE,
    INVALID_NUMBER,
    MISSING_PARAMETER,
    MISSING_HEADER,
    INVALID_PAGINATION,
    VALIDATION_ERROR,
    CONSTRAINT_VIOLATION,
    BIND_ERROR,
    INVALID_ARGUMENT,
    BAD_REQUEST
}
