package com.upsaude.exception;

/**
 * Exceção para erros de sincronização com o CNES.
 *
 * <p>Usada para encapsular erros durante o processo de sincronização de dados do CNES.
 */
public class CnesSincronizacaoException extends RuntimeException {

    public CnesSincronizacaoException(String message) {
        super(message);
    }

    public CnesSincronizacaoException(String message, Throwable cause) {
        super(message, cause);
    }
}

