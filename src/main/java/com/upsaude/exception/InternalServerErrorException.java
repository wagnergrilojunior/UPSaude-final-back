package com.upsaude.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends RuntimeException {

    public InternalServerErrorException(String mensagem) {
        super(mensagem);
    }

    public InternalServerErrorException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
