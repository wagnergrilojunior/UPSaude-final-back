package com.upsaude.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada quando um argumento inválido é fornecido.
 * Retorna status HTTP 400 (Bad Request).
 * 
 * Usada para casos onde um valor específico não é válido (ex: enum inválido,
 * formato incorreto, valor fora do range permitido).
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidArgumentException extends RuntimeException {

    /**
     * Construtor com mensagem de erro.
     *
     * @param mensagem mensagem descrevendo o erro
     */
    public InvalidArgumentException(String mensagem) {
        super(mensagem);
    }

    /**
     * Construtor com mensagem de erro e causa.
     *
     * @param mensagem mensagem descrevendo o erro
     * @param causa exceção que causou este erro
     */
    public InvalidArgumentException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
