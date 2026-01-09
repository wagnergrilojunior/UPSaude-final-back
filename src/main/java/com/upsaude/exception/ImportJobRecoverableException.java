package com.upsaude.exception;

/**
 * Exceção para falhas recuperáveis durante processamento de ImportJob.
 * Exemplos: falha de rede/timeout no Supabase, erro transitório do banco, pool de conexões exaurido.
 *
 * Quando lançada por um worker, o job deve ser re-enfileirado com backoff.
 */
public class ImportJobRecoverableException extends RuntimeException {

    public ImportJobRecoverableException(String message) {
        super(message);
    }

    public ImportJobRecoverableException(String message, Throwable cause) {
        super(message, cause);
    }
}


