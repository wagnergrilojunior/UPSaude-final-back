package com.upsaude.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Handler global para tratamento de exceções da API UPSaúde.
 * Captura exceções e retorna respostas JSON padronizadas.
 * 
 * IMPORTANTE: Não trata exceções de endpoints do Actuator ou /error.
 * O Actuator usa /error como fallback quando ocorre exceção interna.
 * Esses endpoints devem tratar seus próprios erros normalmente.
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.upsaude.controller")
public class ApiExceptionHandler {

    /**
     * Verifica se o path deve ser ignorado por este handler.
     * Endpoints do Actuator e /error não devem ser tratados por este handler.
     * 
     * @param path caminho da requisição
     * @return true se deve ser ignorado, false caso contrário
     */
    private boolean shouldIgnoreEndpoint(String path) {
        if (path == null) {
            return false;
        }
        // Ignora endpoints do Actuator (todas as variações possíveis)
        boolean isActuator = path.startsWith("/api/actuator") ||
                            path.startsWith("/actuator") ||
                            path.contains("/actuator/") ||
                            path.contains("actuator");
        
        // Ignora endpoint /error usado pelo Spring Boot para tratamento de erros
        // O Actuator usa /error como fallback quando ocorre exceção interna
        boolean isErrorEndpoint = path.equals("/error") ||
                                 path.equals("/api/error") ||
                                 path.startsWith("/error/") ||
                                 path.startsWith("/api/error/");
        
        return isActuator || isErrorEndpoint;
    }

    /**
     * Trata exceções de requisição inválida (400).
     *
     * @param ex exceção lançada
     * @param request requisição HTTP
     * @return resposta JSON com detalhes do erro
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequestException(
            BadRequestException ex, HttpServletRequest request) {
        // Não trata exceções de endpoints do Actuator ou /error
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

    /**
     * Trata exceções de não autorizado (401).
     *
     * @param ex exceção lançada
     * @param request requisição HTTP
     * @return resposta JSON com detalhes do erro
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorizedException(
            UnauthorizedException ex, HttpServletRequest request) {
        // Não trata exceções de endpoints do Actuator ou /error
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

    /**
     * Trata exceções de acesso proibido (403).
     *
     * @param ex exceção lançada
     * @param request requisição HTTP
     * @return resposta JSON com detalhes do erro
     */
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Map<String, Object>> handleForbiddenException(
            ForbiddenException ex, HttpServletRequest request) {
        // Não trata exceções de endpoints do Actuator ou /error
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

    /**
     * Trata exceções de recurso não encontrado (404).
     *
     * @param ex exceção lançada
     * @param request requisição HTTP
     * @return resposta JSON com detalhes do erro
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFoundException(
            NotFoundException ex, HttpServletRequest request) {
        // Não trata exceções de endpoints do Actuator ou /error
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

    /**
     * Trata exceções de conflito (409).
     *
     * @param ex exceção lançada
     * @param request requisição HTTP
     * @return resposta JSON com detalhes do erro
     */
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Map<String, Object>> handleConflictException(
            ConflictException ex, HttpServletRequest request) {
        // Não trata exceções de endpoints do Actuator ou /error
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

    /**
     * Trata exceções de entidade não processável (422).
     *
     * @param ex exceção lançada
     * @param request requisição HTTP
     * @return resposta JSON com detalhes do erro
     */
    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<Map<String, Object>> handleUnprocessableEntityException(
            UnprocessableEntityException ex, HttpServletRequest request) {
        // Não trata exceções de endpoints do Actuator ou /error
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

    /**
     * Trata exceções de erro interno do servidor (500).
     *
     * @param ex exceção lançada
     * @param request requisição HTTP
     * @return resposta JSON com detalhes do erro incluindo stack trace
     */
    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<Map<String, Object>> handleInternalServerErrorException(
            InternalServerErrorException ex, HttpServletRequest request) {
        // Não trata exceções de endpoints do Actuator ou /error
        if (shouldIgnoreEndpoint(request.getRequestURI())) {
            throw new RuntimeException("Actuator or error endpoint exception", ex);
        }
        
        // Log detalhado com stack trace completo
        log.error("Erro interno do servidor - Path: {}, Method: {}, Exception: {}", 
            request.getRequestURI(), request.getMethod(), ex.getClass().getName(), ex);
        
        // Retorna resposta com stack trace
        return buildErrorResponseWithStackTrace(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro Interno do Servidor",
                ex.getMessage() != null ? ex.getMessage() : "Ocorreu um erro interno no servidor",
                request.getRequestURI(),
                request.getMethod(),
                ex
        );
    }

    /**
     * Trata exceções de validação do Spring (MethodArgumentNotValidException).
     * Retorna erro 400 padronizado com lista de erros de validação.
     *
     * @param ex exceção lançada
     * @param request requisição HTTP
     * @return resposta JSON com detalhes do erro no formato padronizado
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        // Não trata exceções de endpoints do Actuator ou /error
        if (shouldIgnoreEndpoint(request.getRequestURI())) {
            RuntimeException runtimeEx = new RuntimeException("Actuator endpoint exception", ex);
            throw runtimeEx; // Re-lança para que o Actuator trate
        }
        
        // Constrói lista de erros no formato padronizado: [{"campo": "...", "mensagem": "..."}]
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

    /**
     * Trata exceções de autenticação do Spring Security (401).
     *
     * @param ex exceção lançada
     * @param request requisição HTTP
     * @return resposta JSON com detalhes do erro
     */
    @ExceptionHandler({AuthenticationException.class, InsufficientAuthenticationException.class, AuthenticationCredentialsNotFoundException.class})
    public ResponseEntity<Map<String, Object>> handleAuthenticationException(
            Exception ex, HttpServletRequest request) {
        // Não trata exceções de endpoints do Actuator ou /error
        if (shouldIgnoreEndpoint(request.getRequestURI())) {
            throw new RuntimeException(ex); // Re-lança para que o Actuator trate
        }
        return buildErrorResponse(
                HttpStatus.UNAUTHORIZED,
                "Não Autorizado",
                "Token de autenticação inválido ou não fornecido",
                request.getRequestURI()
        );
    }

    /**
     * Trata exceções de acesso negado do Spring Security (403).
     *
     * @param ex exceção lançada
     * @param request requisição HTTP
     * @return resposta JSON com detalhes do erro
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDeniedException(
            AccessDeniedException ex, HttpServletRequest request) {
        // Não trata exceções de endpoints do Actuator ou /error
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

    /**
     * Trata exceções genéricas não capturadas.
     *
     * @param ex exceção lançada
     * @param request requisição HTTP
     * @return resposta JSON com detalhes do erro incluindo stack trace
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(
            Exception ex, HttpServletRequest request) {
        // Não trata exceções de endpoints do Actuator ou /error
        if (shouldIgnoreEndpoint(request.getRequestURI())) {
            RuntimeException runtimeEx = new RuntimeException("Actuator endpoint exception", ex);
            throw runtimeEx; // Re-lança para que o Actuator trate
        }
        
        // Log detalhado do erro com stack trace completo
        log.error("Erro não tratado - Path: {}, Method: {}, Exception: {}, Message: {}", 
            request.getRequestURI(), request.getMethod(), ex.getClass().getName(), ex.getMessage(), ex);
        
        // Retorna resposta com stack trace completo
        return buildErrorResponseWithStackTrace(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro Interno do Servidor",
                ex.getMessage() != null ? ex.getMessage() : "Ocorreu um erro inesperado no sistema",
                request.getRequestURI(),
                request.getMethod(),
                ex
        );
    }

    /**
     * Constrói uma resposta de erro padronizada.
     *
     * @param status status HTTP
     * @param erro nome do erro
     * @param mensagem mensagem de erro
     * @param path caminho da requisição
     * @return resposta JSON com detalhes do erro
     */
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

    /**
     * Constrói uma resposta de erro padronizada com stack trace completo.
     * Usado para erros 500 para facilitar debug e ajustes rápidos.
     *
     * @param status status HTTP
     * @param erro nome do erro
     * @param mensagem mensagem de erro
     * @param path caminho da requisição
     * @param method método HTTP (GET, POST, etc.)
     * @param ex exceção que causou o erro
     * @return resposta JSON com detalhes do erro incluindo stack trace
     */
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
        
        // Adiciona stack trace como array de strings
        List<String> stackTrace = Arrays.stream(ex.getStackTrace())
            .map(StackTraceElement::toString)
            .collect(Collectors.toList());
        resposta.put("stackTrace", stackTrace);
        
        // Se houver causa (caused by), adiciona também
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

