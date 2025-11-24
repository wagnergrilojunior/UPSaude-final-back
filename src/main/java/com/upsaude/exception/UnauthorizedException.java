package com.upsaude.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada quando o usuário não está autenticado.
 * Retorna status HTTP 401 (Unauthorized).
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {

    /**
     * Construtor com mensagem de erro.
     *
     * @param mensagem mensagem descrevendo o erro
     */
    public UnauthorizedException(String mensagem) {
        super(mensagem);
    }

    /**
     * Construtor com mensagem de erro e causa.
     *
     * @param mensagem mensagem descrevendo o erro
     * @param causa exceção que causou este erro
     */
    public UnauthorizedException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

