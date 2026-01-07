package com.upsaude.exception;

/**
 * Exceção para falhas de integração SOAP com o SOA-CNES (DATASUS).
 *
 * <p>Usada para encapsular timeouts, falhas SOAP (SoapFault) e erros de parsing/marshalling.
 */
public class CnesSoapException extends RuntimeException {

    public CnesSoapException(String message) {
        super(message);
    }

    public CnesSoapException(String message, Throwable cause) {
        super(message, cause);
    }
}

