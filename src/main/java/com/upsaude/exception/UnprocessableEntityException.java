package com.upsaude.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada quando a entidade não pode ser processada devido a erros de validação.
 * Retorna status HTTP 422 (Unprocessable Entity).
 */
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableEntityException extends RuntimeException {

    /**
     * Construtor com mensagem de erro.
     *
     * @param mensagem mensagem descrevendo o erro
     */
    public UnprocessableEntityException(String mensagem) {
        super(mensagem);
    }

    /**
     * Construtor com mensagem de erro e causa.
     *
     * @param mensagem mensagem descrevendo o erro
     * @param causa exceção que causou este erro
     */
    public UnprocessableEntityException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

