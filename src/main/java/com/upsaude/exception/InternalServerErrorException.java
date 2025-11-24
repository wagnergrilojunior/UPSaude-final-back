package com.upsaude.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada quando ocorre um erro interno do servidor.
 * Retorna status HTTP 500 (Internal Server Error).
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends RuntimeException {

    /**
     * Construtor com mensagem de erro.
     *
     * @param mensagem mensagem descrevendo o erro
     */
    public InternalServerErrorException(String mensagem) {
        super(mensagem);
    }

    /**
     * Construtor com mensagem de erro e causa.
     *
     * @param mensagem mensagem descrevendo o erro
     * @param causa exceção que causou este erro
     */
    public InternalServerErrorException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

