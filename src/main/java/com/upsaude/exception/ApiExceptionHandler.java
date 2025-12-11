package com.upsaude.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice(basePackages = "com.upsaude.controller")
public class ApiExceptionHandler {

    private boolean shouldIgnoreEndpoint(String path) {
        if (path == null) {
            return false;
        }

        boolean isActuator = path.startsWith("/api/actuator") ||
                            path.startsWith("/actuator") ||
                            path.contains("/actuator/") ||
                            path.contains("actuator");

        boolean isErrorEndpoint = path.equals("/error") ||
                                 path.equals("/api/error") ||
                                 path.startsWith("/error/") ||
                                 path.startsWith("/api/error/");

        return isActuator || isErrorEndpoint;
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequestException(
            BadRequestException ex, HttpServletRequest request) {

        if (shouldIgnoreEndpoint(request.getRequestURI())) {
            throw new RuntimeException("Actuator or error endpoint exception", ex);
        }
        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Requisição Inválida",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorizedException(
            UnauthorizedException ex, HttpServletRequest request) {

        if (shouldIgnoreEndpoint(request.getRequestURI())) {
            throw new RuntimeException("Actuator or error endpoint exception", ex);
        }
        return buildErrorResponse(
                HttpStatus.UNAUTHORIZED,
                "Não Autorizado",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Map<String, Object>> handleForbiddenException(
            ForbiddenException ex, HttpServletRequest request) {

        if (shouldIgnoreEndpoint(request.getRequestURI())) {
            throw new RuntimeException("Actuator or error endpoint exception", ex);
        }
        return buildErrorResponse(
                HttpStatus.FORBIDDEN,
                "Acesso Proibido",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFoundException(
            NotFoundException ex, HttpServletRequest request) {

        if (shouldIgnoreEndpoint(request.getRequestURI())) {
            throw new RuntimeException("Actuator or error endpoint exception", ex);
        }
        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                "Recurso Não Encontrado",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Map<String, Object>> handleConflictException(
            ConflictException ex, HttpServletRequest request) {

        if (shouldIgnoreEndpoint(request.getRequestURI())) {
            throw new RuntimeException("Actuator or error endpoint exception", ex);
        }
        return buildErrorResponse(
                HttpStatus.CONFLICT,
                "Conflito",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<Map<String, Object>> handleUnprocessableEntityException(
            UnprocessableEntityException ex, HttpServletRequest request) {

        if (shouldIgnoreEndpoint(request.getRequestURI())) {
            throw new RuntimeException("Actuator or error endpoint exception", ex);
        }
        return buildErrorResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "Entidade Não Processável",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<Map<String, Object>> handleInternalServerErrorException(
            InternalServerErrorException ex, HttpServletRequest request) {

        if (shouldIgnoreEndpoint(request.getRequestURI())) {
            throw new RuntimeException("Actuator or error endpoint exception", ex);
        }

        log.error("Erro interno do servidor - Path: {}, Method: {}, Exception: {}",
            request.getRequestURI(), request.getMethod(), ex.getClass().getName(), ex);

        return buildErrorResponseWithStackTrace(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro Interno do Servidor",
                ex.getMessage() != null ? ex.getMessage() : "Ocorreu um erro interno no servidor",
                request.getRequestURI(),
                request.getMethod(),
                ex
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex, HttpServletRequest request) {

        if (shouldIgnoreEndpoint(request.getRequestURI())) {
            RuntimeException runtimeEx = new RuntimeException("Actuator endpoint exception", ex);
            throw runtimeEx;
        }

        String mensagemErro = ex.getMessage();
        if (mensagemErro != null && mensagemErro.contains("JSON parse error:")) {

            int index = mensagemErro.indexOf("JSON parse error:");
            if (index >= 0) {
                mensagemErro = mensagemErro.substring(index);

                if (mensagemErro.length() > 200) {
                    mensagemErro = mensagemErro.substring(0, 200) + "...";
                }
            }
        }

        if (mensagemErro == null || mensagemErro.trim().isEmpty()) {
            mensagemErro = "Erro ao processar JSON da requisição. Verifique os tipos de dados e formatos enviados.";
        }

        log.warn("Erro de deserialização JSON - Path: {}, Method: {}, Message: {}",
            request.getRequestURI(), request.getMethod(), mensagemErro);

        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Requisição Inválida",
                mensagemErro,
                request.getRequestURI()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        if (shouldIgnoreEndpoint(request.getRequestURI())) {
            RuntimeException runtimeEx = new RuntimeException("Actuator endpoint exception", ex);
            throw runtimeEx;
        }

        List<Map<String, String>> erros = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    Map<String, String> erro = new HashMap<>();
                    String campo = error instanceof FieldError
                            ? ((FieldError) error).getField()
                            : error.getObjectName();
                    String mensagem = error.getDefaultMessage();
                    erro.put("campo", campo);
                    erro.put("mensagem", mensagem != null ? mensagem : "Erro de validação");
                    return erro;
                })
                .collect(Collectors.toList());

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("status", HttpStatus.BAD_REQUEST.value());
        resposta.put("message", "Erro de validação");
        resposta.put("errors", erros);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
    }

    @ExceptionHandler({AuthenticationException.class, InsufficientAuthenticationException.class, AuthenticationCredentialsNotFoundException.class})
    public ResponseEntity<Map<String, Object>> handleAuthenticationException(
            Exception ex, HttpServletRequest request) {

        if (shouldIgnoreEndpoint(request.getRequestURI())) {
            throw new RuntimeException(ex);
        }
        return buildErrorResponse(
                HttpStatus.UNAUTHORIZED,
                "Não Autorizado",
                "Token de autenticação inválido ou não fornecido",
                request.getRequestURI()
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDeniedException(
            AccessDeniedException ex, HttpServletRequest request) {

        if (shouldIgnoreEndpoint(request.getRequestURI())) {
            throw new RuntimeException("Actuator or error endpoint exception", ex);
        }
        return buildErrorResponse(
                HttpStatus.FORBIDDEN,
                "Acesso Proibido",
                "Você não tem permissão para acessar este recurso",
                request.getRequestURI()
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex, HttpServletRequest request) {

        if (shouldIgnoreEndpoint(request.getRequestURI())) {
            RuntimeException runtimeEx = new RuntimeException("Actuator endpoint exception", ex);
            throw runtimeEx;
        }

        String mensagemErro = ex.getMessage();
        String nomeParametro = ex.getName();
        String valorRecebido = ex.getValue() != null ? ex.getValue().toString() : "null";
        String tipoEsperado = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "desconhecido";

        if (tipoEsperado.equals("UUID")) {
            mensagemErro = String.format("ID inválido: '%s'. O ID deve ser um UUID válido no formato xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx", valorRecebido);
        } else {
            mensagemErro = String.format("Parâmetro '%s' com valor '%s' não pode ser convertido para %s", nomeParametro, valorRecebido, tipoEsperado);
        }

        log.warn("Erro de tipo de argumento - Path: {}, Method: {}, Parâmetro: {}, Valor: {}, Tipo esperado: {}",
            request.getRequestURI(), request.getMethod(), nomeParametro, valorRecebido, tipoEsperado);

        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Requisição Inválida",
                mensagemErro,
                request.getRequestURI()
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex, HttpServletRequest request) {

        if (shouldIgnoreEndpoint(request.getRequestURI())) {
            RuntimeException runtimeEx = new RuntimeException("Actuator endpoint exception", ex);
            throw runtimeEx;
        }

        String mensagemErro = ex.getMessage();
        Throwable causa = ex.getCause();

        if (causa != null && causa.getMessage() != null) {
            String causaMsg = causa.getMessage();

            if (causaMsg.contains("duplicate key value violates unique constraint")) {
                if (causaMsg.contains("uk_medicos_crm_uf_tenant")) {
                    mensagemErro = "Já existe um médico cadastrado com o mesmo CRM e UF neste tenant. CRM e UF não podem estar vazios quando informados.";
                } else if (causaMsg.contains("uk_medicos_cpf_tenant")) {
                    mensagemErro = "Já existe um médico cadastrado com o mesmo CPF neste tenant.";
                } else {
                    mensagemErro = "Violação de constraint única: já existe um registro com os mesmos valores.";
                }
            } else if (causaMsg.contains("violates foreign key constraint")) {
                mensagemErro = "Referência inválida: o registro referenciado não existe.";
            } else if (causaMsg.contains("violates not-null constraint")) {
                mensagemErro = "Campo obrigatório não pode ser nulo.";
            } else {

                if (causaMsg.length() < 200) {
                    mensagemErro = causaMsg;
                }
            }
        }

        if (mensagemErro == null || mensagemErro.trim().isEmpty() || mensagemErro.equals(ex.getMessage())) {
            mensagemErro = "Violação de integridade de dados. Verifique se os dados enviados não conflitam com registros existentes.";
        }

        log.warn("Erro de integridade de dados - Path: {}, Method: {}, Message: {}",
            request.getRequestURI(), request.getMethod(), mensagemErro);

        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Requisição Inválida",
                mensagemErro,
                request.getRequestURI()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(
            Exception ex, HttpServletRequest request) {

        if (shouldIgnoreEndpoint(request.getRequestURI())) {
            RuntimeException runtimeEx = new RuntimeException("Actuator endpoint exception", ex);
            throw runtimeEx;
        }

        log.error("Erro não tratado - Path: {}, Method: {}, Exception: {}, Message: {}",
            request.getRequestURI(), request.getMethod(), ex.getClass().getName(), ex.getMessage(), ex);

        return buildErrorResponseWithStackTrace(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro Interno do Servidor",
                ex.getMessage() != null ? ex.getMessage() : "Ocorreu um erro inesperado no sistema",
                request.getRequestURI(),
                request.getMethod(),
                ex
        );
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(
            HttpStatus status, String erro, String mensagem, String path) {
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("timestamp", LocalDateTime.now());
        resposta.put("status", status.value());
        resposta.put("erro", erro);
        resposta.put("mensagem", mensagem);
        resposta.put("path", path);

        return ResponseEntity.status(status).body(resposta);
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponseWithStackTrace(
            HttpStatus status, String erro, String mensagem, String path, String method, Throwable ex) {
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("timestamp", LocalDateTime.now());
        resposta.put("status", status.value());
        resposta.put("erro", erro);
        resposta.put("mensagem", mensagem);
        resposta.put("path", path);
        resposta.put("method", method);
        resposta.put("exception", ex.getClass().getName());

        List<String> stackTrace = Arrays.stream(ex.getStackTrace())
            .map(StackTraceElement::toString)
            .collect(Collectors.toList());
        resposta.put("stackTrace", stackTrace);

        if (ex.getCause() != null) {
            Map<String, Object> causa = new HashMap<>();
            causa.put("exception", ex.getCause().getClass().getName());
            causa.put("message", ex.getCause().getMessage());
            List<String> causaStackTrace = Arrays.stream(ex.getCause().getStackTrace())
                .map(StackTraceElement::toString)
                .collect(Collectors.toList());
            causa.put("stackTrace", causaStackTrace);
            resposta.put("causedBy", causa);
        }

        return ResponseEntity.status(status).body(resposta);
    }
}
