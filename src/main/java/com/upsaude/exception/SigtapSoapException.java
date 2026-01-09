package com.upsaude.exception;

/**
 * Exce??o para falhas de integra??o SOAP com o SOA-SIGTAP (DATASUS).
 *
 * <p>Usada para encapsular timeouts, falhas SOAP (SoapFault) e erros de parsing/marshalling.
 */
public class SigtapSoapException extends RuntimeException {

    public SigtapSoapException(String message) {
        super(message);
    }

    public SigtapSoapException(String message, Throwable cause) {
        super(message, cause);
    }
}

