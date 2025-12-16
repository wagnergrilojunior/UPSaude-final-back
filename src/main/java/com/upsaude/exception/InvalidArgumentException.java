package com.upsaude.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidArgumentException extends RuntimeException {

    public InvalidArgumentException(String mensagem) {
        super(mensagem);
    }

    public InvalidArgumentException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
