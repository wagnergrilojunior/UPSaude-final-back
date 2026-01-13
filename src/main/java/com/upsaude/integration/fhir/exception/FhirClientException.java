package com.upsaude.integration.fhir.exception;

public class FhirClientException extends RuntimeException {

    public FhirClientException(String message) {
        super(message);
    }

    public FhirClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public static FhirClientException connectionError(String url, Throwable cause) {
        return new FhirClientException("Erro ao conectar com servidor FHIR: " + url, cause);
    }

    public static FhirClientException parseError(String resource, Throwable cause) {
        return new FhirClientException("Erro ao parsear recurso FHIR: " + resource, cause);
    }

    public static FhirClientException notFound(String resource) {
        return new FhirClientException("Recurso FHIR não encontrado: " + resource);
    }

    public static FhirClientException serverError(int statusCode, String message) {
        return new FhirClientException("Erro do servidor FHIR [" + statusCode + "]: " + message);
    }
}
