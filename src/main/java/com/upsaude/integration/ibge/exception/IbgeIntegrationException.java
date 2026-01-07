package com.upsaude.integration.ibge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção genérica para erros de integração com a API IBGE
 */
@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class IbgeIntegrationException extends RuntimeException {

    public IbgeIntegrationException(String mensagem) {
        super(mensagem);
    }

    public IbgeIntegrationException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

