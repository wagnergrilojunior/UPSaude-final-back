package com.upsaude.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada quando há um conflito com o estado atual do recurso.
 * Retorna status HTTP 409 (Conflict).
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {

    /**
     * Construtor com mensagem de erro.
     *
     * @param mensagem mensagem descrevendo o erro
     */
    public ConflictException(String mensagem) {
        super(mensagem);
    }

    /**
     * Construtor com mensagem de erro e causa.
     *
     * @param mensagem mensagem descrevendo o erro
     * @param causa exceção que causou este erro
     */
    public ConflictException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

