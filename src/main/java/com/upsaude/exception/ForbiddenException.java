package com.upsaude.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String mensagem) {
        super(mensagem);
    }

    public ForbiddenException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
