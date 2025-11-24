package com.upsaude.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada quando um recurso não é encontrado.
 * Retorna status HTTP 404 (Not Found).
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    /**
     * Construtor com mensagem de erro.
     *
     * @param mensagem mensagem descrevendo o erro
     */
    public NotFoundException(String mensagem) {
        super(mensagem);
    }

    /**
     * Construtor com mensagem de erro e causa.
     *
     * @param mensagem mensagem descrevendo o erro
     * @param causa exceção que causou este erro
     */
    public NotFoundException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

