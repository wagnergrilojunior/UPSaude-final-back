package com.upsaude.integration.ibge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção específica para timeouts na comunicação com a API IBGE
 */
@ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
public class IbgeTimeoutException extends IbgeIntegrationException {

    public IbgeTimeoutException(String mensagem) {
        super(mensagem);
    }

    public IbgeTimeoutException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

