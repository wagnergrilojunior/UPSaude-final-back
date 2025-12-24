package com.upsaude.exception;

/**
 * Exceção para falhas fatais durante processamento de ImportJob.
 * Exemplos: arquivo inválido, layout ausente, tipo não suportado, payload inconsistente.
 *
 * Quando lançada por um worker, o job deve ser marcado como ERRO sem retry.
 */
public class ImportJobFatalException extends RuntimeException {

    public ImportJobFatalException(String message) {
        super(message);
    }

    public ImportJobFatalException(String message, Throwable cause) {
        super(message, cause);
    }
}


