package com.upsaude.exception.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiFieldError {
    private String campo;
    private String mensagem;
    private ApiErrorCode codigo;
    private Object valorRejeitado;

    public ApiFieldError() {}

    public ApiFieldError(String campo, String mensagem) {
        this.campo = campo;
        this.mensagem = mensagem;
    }

    public ApiFieldError(String campo, String mensagem, ApiErrorCode codigo, Object valorRejeitado) {
        this.campo = campo;
        this.mensagem = mensagem;
        this.codigo = codigo;
        this.valorRejeitado = valorRejeitado;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public ApiErrorCode getCodigo() {
        return codigo;
    }

    public void setCodigo(ApiErrorCode codigo) {
        this.codigo = codigo;
    }

    public Object getValorRejeitado() {
        return valorRejeitado;
    }

    public void setValorRejeitado(Object valorRejeitado) {
        this.valorRejeitado = valorRejeitado;
    }
}
