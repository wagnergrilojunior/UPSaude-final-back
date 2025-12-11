package com.upsaude.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableEntityException extends RuntimeException {

    public UnprocessableEntityException(String mensagem) {
        super(mensagem);
    }

    public UnprocessableEntityException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
