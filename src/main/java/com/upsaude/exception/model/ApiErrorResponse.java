package com.upsaude.exception.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelo único e compatível de erro.
 *
 * Compatibilidade:
 * - Mantém chaves já existentes no projeto: timestamp/status/erro/mensagem/path e status/message/errors
 * - Adiciona: codigo e errors com codigo/valorRejeitado (opcionais)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse {
    private LocalDateTime timestamp;
    private Integer status;

    // legado: título e mensagem
    private String erro;
    private String mensagem;

    // legado: resposta de validação usa "message"
    private String message;

    private String path;

    // novo
    private ApiErrorCode codigo;

    // legado + novo: lista de erros por campo (mantém nome "errors")
    private List<ApiFieldError> errors;

    public ApiErrorResponse() {}

    public static ApiErrorResponse of(Integer status, String path) {
        ApiErrorResponse r = new ApiErrorResponse();
        r.timestamp = LocalDateTime.now();
        r.status = status;
        r.path = path;
        return r;
    }

    public ApiErrorResponse addError(ApiFieldError e) {
        if (e == null) return this;
        if (this.errors == null) this.errors = new ArrayList<>();
        this.errors.add(e);
        return this;
    }

    public ApiErrorResponse setMensagensCompat(String titulo, String msg) {
        this.erro = titulo;
        this.mensagem = msg;
        this.message = msg; // compat: duplicar
        return this;
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public String getErro() { return erro; }
    public void setErro(String erro) { this.erro = erro; }
    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
    public ApiErrorCode getCodigo() { return codigo; }
    public void setCodigo(ApiErrorCode codigo) { this.codigo = codigo; }
    public List<ApiFieldError> getErrors() { return errors; }
    public void setErrors(List<ApiFieldError> errors) { this.errors = errors; }
}
