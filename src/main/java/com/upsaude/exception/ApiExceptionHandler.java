package com.upsaude.exception;

import jakarta.servlet.http.HttpServletRequest;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Handler global para tratamento de exceções da API UPSaúde.
 * Captura exceções e retorna respostas JSON padronizadas.
 */
@RestControllerAdvice
public class ApiExceptionHandler {

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
     * @return resposta JSON com detalhes do erro
     */
    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<Map<String, Object>> handleInternalServerErrorException(
            InternalServerErrorException ex, HttpServletRequest request) {
        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro Interno do Servidor",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    /**
     * Trata exceções de validação do Spring (MethodArgumentNotValidException).
     *
     * @param ex exceção lançada
     * @param request requisição HTTP
     * @return resposta JSON com detalhes do erro
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> erros = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String campo = ((FieldError) error).getField();
            String mensagem = error.getDefaultMessage();
            erros.put(campo, mensagem);
        });

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("timestamp", LocalDateTime.now());
        resposta.put("status", HttpStatus.BAD_REQUEST.value());
        resposta.put("erro", "Erro de Validação");
        resposta.put("mensagem", "Dados inválidos fornecidos");
        resposta.put("erros", erros);
        resposta.put("path", request.getRequestURI());

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
     * @return resposta JSON com detalhes do erro
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(
            Exception ex, HttpServletRequest request) {
        // Log detalhado do erro para facilitar diagnóstico
        System.err.println("=== ERRO NÃO TRATADO ===");
        System.err.println("Path: " + request.getRequestURI());
        System.err.println("Method: " + request.getMethod());
        System.err.println("Exception: " + ex.getClass().getName());
        System.err.println("Message: " + ex.getMessage());
        ex.printStackTrace();
        System.err.println("========================");
        
        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro Interno do Servidor",
                "Ocorreu um erro inesperado no sistema",
                request.getRequestURI()
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
}

