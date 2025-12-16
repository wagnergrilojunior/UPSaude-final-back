package com.upsaude.exception;

import com.upsaude.exception.mapper.ApiErrorFactory;
import com.upsaude.exception.model.ApiErrorCode;
import com.upsaude.exception.model.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
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
    public ResponseEntity<ApiErrorResponse> handleBadRequestException(
            BadRequestException ex, HttpServletRequest request) {

        if (shouldIgnoreEndpoint(request.getRequestURI())) {
            throw new RuntimeException("Actuator or error endpoint exception", ex);
        }
        ApiErrorResponse body = ApiErrorFactory.badRequest(
            request.getRequestURI(),
            ApiErrorCode.BAD_REQUEST,
            "Requisição Inválida",
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiErrorResponse> handleUnauthorizedException(
            UnauthorizedException ex, HttpServletRequest request) {

        if (shouldIgnoreEndpoint(request.getRequestURI())) {
            throw new RuntimeException("Actuator or error endpoint exception", ex);
        }
        ApiErrorResponse body = ApiErrorResponse.of(HttpStatus.UNAUTHORIZED.value(), request.getRequestURI())
            .setMensagensCompat("Não Autorizado", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiErrorResponse> handleForbiddenException(
            ForbiddenException ex, HttpServletRequest request) {

        if (shouldIgnoreEndpoint(request.getRequestURI())) {
            throw new RuntimeException("Actuator or error endpoint exception", ex);
        }
        ApiErrorResponse body = ApiErrorResponse.of(HttpStatus.FORBIDDEN.value(), request.getRequestURI())
            .setMensagensCompat("Acesso Proibido", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFoundException(
            NotFoundException ex, HttpServletRequest request) {

        if (shouldIgnoreEndpoint(request.getRequestURI())) {
            throw new RuntimeException("Actuator or error endpoint exception", ex);
        }
        ApiErrorResponse body = ApiErrorResponse.of(HttpStatus.NOT_FOUND.value(), request.getRequestURI())
            .setMensagensCompat("Recurso Não Encontrado", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiErrorResponse> handleConflictException(
            ConflictException ex, HttpServletRequest request) {

        if (shouldIgnoreEndpoint(request.getRequestURI())) {
            throw new RuntimeException("Actuator or error endpoint exception", ex);
        }
        ApiErrorResponse body = ApiErrorResponse.of(HttpStatus.CONFLICT.value(), request.getRequestURI())
            .setMensagensCompat("Conflito", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<ApiErrorResponse> handleUnprocessableEntityException(
            UnprocessableEntityException ex, HttpServletRequest request) {

        if (shouldIgnoreEndpoint(request.getRequestURI())) {
            throw new RuntimeException("Actuator or error endpoint exception", ex);
        }
        ApiErrorResponse body = ApiErrorResponse.of(HttpStatus.UNPROCESSABLE_ENTITY.value(), request.getRequestURI())
            .setMensagensCompat("Entidade Não Processável", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(body);
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
    public ResponseEntity<ApiErrorResponse> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex, HttpServletRequest request) {

        if (shouldIgnoreEndpoint(request.getRequestURI())) {
            RuntimeException runtimeEx = new RuntimeException("Actuator endpoint exception", ex);
            throw runtimeEx;
        }

        Throwable root = ex.getMostSpecificCause() != null ? ex.getMostSpecificCause() : ex.getCause();
        ApiErrorResponse body = ApiErrorFactory.fromJackson(request.getRequestURI(), root);
        log.warn("Erro de deserialização JSON - Path: {}, Method: {}, Codigo: {}, Message: {}",
            request.getRequestURI(), request.getMethod(), body.getCodigo(), body.getMensagem());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        if (shouldIgnoreEndpoint(request.getRequestURI())) {
            RuntimeException runtimeEx = new RuntimeException("Actuator endpoint exception", ex);
            throw runtimeEx;
        }

        ApiErrorResponse body = ApiErrorResponse.of(HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
        body.setCodigo(ApiErrorCode.VALIDATION_ERROR);
        body.setMensagensCompat("Requisição Inválida", "Erro de validação");
        if (ex.getBindingResult() != null) {
            ex.getBindingResult().getAllErrors().forEach(error -> {
                String campo = error instanceof FieldError ? ((FieldError) error).getField() : error.getObjectName();
                String mensagem = error.getDefaultMessage() != null ? error.getDefaultMessage() : "Erro de validação";
                body.addError(new com.upsaude.exception.model.ApiFieldError(campo, mensagem, ApiErrorCode.VALIDATION_ERROR, null));
            });
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler({AuthenticationException.class, InsufficientAuthenticationException.class, AuthenticationCredentialsNotFoundException.class})
    public ResponseEntity<ApiErrorResponse> handleAuthenticationException(
            Exception ex, HttpServletRequest request) {

        if (shouldIgnoreEndpoint(request.getRequestURI())) {
            throw new RuntimeException(ex);
        }
        ApiErrorResponse body = ApiErrorResponse.of(HttpStatus.UNAUTHORIZED.value(), request.getRequestURI())
            .setMensagensCompat("Não Autorizado", "Token de autenticação inválido ou não fornecido");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> handleAccessDeniedException(
            AccessDeniedException ex, HttpServletRequest request) {

        if (shouldIgnoreEndpoint(request.getRequestURI())) {
            throw new RuntimeException("Actuator or error endpoint exception", ex);
        }
        ApiErrorResponse body = ApiErrorResponse.of(HttpStatus.FORBIDDEN.value(), request.getRequestURI())
            .setMensagensCompat("Acesso Proibido", "Você não tem permissão para acessar este recurso");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex, HttpServletRequest request) {

        if (shouldIgnoreEndpoint(request.getRequestURI())) {
            RuntimeException runtimeEx = new RuntimeException("Actuator endpoint exception", ex);
            throw runtimeEx;
        }

        ApiErrorResponse body = ApiErrorFactory.fromTypeMismatch(request.getRequestURI(), ex);
        log.warn("Erro de tipo de argumento - Path: {}, Method: {}, Param: {}, Codigo: {}",
            request.getRequestURI(), request.getMethod(), ex.getName(), body.getCodigo());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiErrorResponse> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException ex, HttpServletRequest request) {

        if (shouldIgnoreEndpoint(request.getRequestURI())) {
            RuntimeException runtimeEx = new RuntimeException("Actuator endpoint exception", ex);
            throw runtimeEx;
        }

        ApiErrorResponse body = ApiErrorFactory.fromMissingParameter(request.getRequestURI(), ex);
        log.warn("Parâmetro obrigatório ausente - Path: {}, Method: {}, Param: {}",
            request.getRequestURI(), request.getMethod(), ex.getParameterName());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ApiErrorResponse> handleMissingRequestHeaderException(
            MissingRequestHeaderException ex, HttpServletRequest request) {

        if (shouldIgnoreEndpoint(request.getRequestURI())) {
            RuntimeException runtimeEx = new RuntimeException("Actuator endpoint exception", ex);
            throw runtimeEx;
        }

        String header = ex.getHeaderName();
        String msg = String.format("Header obrigatório ausente: '%s'", header);
        ApiErrorResponse body = ApiErrorFactory.badRequest(
            request.getRequestURI(), ApiErrorCode.MISSING_HEADER, "Requisição Inválida", msg);
        body.addError(new com.upsaude.exception.model.ApiFieldError(header, msg, ApiErrorCode.MISSING_HEADER, null));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiErrorResponse> handleBindException(
            BindException ex, HttpServletRequest request) {

        if (shouldIgnoreEndpoint(request.getRequestURI())) {
            RuntimeException runtimeEx = new RuntimeException("Actuator endpoint exception", ex);
            throw runtimeEx;
        }

        ApiErrorResponse body = ApiErrorFactory.fromBindException(request.getRequestURI(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolationException(
            ConstraintViolationException ex, HttpServletRequest request) {

        if (shouldIgnoreEndpoint(request.getRequestURI())) {
            RuntimeException runtimeEx = new RuntimeException("Actuator endpoint exception", ex);
            throw runtimeEx;
        }

        ApiErrorResponse body = ApiErrorFactory.fromConstraintViolation(request.getRequestURI(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidArgumentException(
            InvalidArgumentException ex, HttpServletRequest request) {

        if (shouldIgnoreEndpoint(request.getRequestURI())) {
            RuntimeException runtimeEx = new RuntimeException("Actuator endpoint exception", ex);
            throw runtimeEx;
        }

        ApiErrorResponse body = ApiErrorFactory.fromInvalidArgument(request.getRequestURI(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
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
                String coluna = extrairColunaNotNull(causaMsg);
                String relacao = extrairRelacao(causaMsg);
                String campoAmigavel = mapearCampoAmigavel(relacao, coluna);
                if (coluna != null) {
                    mensagemErro = String.format("Campo obrigatório '%s'%s não pode ser nulo.",
                            campoAmigavel != null ? campoAmigavel : coluna,
                            relacao != null ? String.format(" (tabela '%s')", relacao) : "");
                } else {
                    mensagemErro = "Campo obrigatório não pode ser nulo.";
                }
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

    private String extrairColunaNotNull(String causaMsg) {
        // Exemplo PostgreSQL: "null value in column \"estabelecimento_id\" of relation \"equipes_saude\" violates not-null constraint"
        try {
            int colIdx = causaMsg.indexOf("column \"");
            if (colIdx >= 0) {
                int start = colIdx + "column \"".length();
                int end = causaMsg.indexOf("\"", start);
                if (end > start) {
                    return causaMsg.substring(start, end);
                }
            }
        } catch (Exception ignored) {}
        return null;
    }

    private String extrairRelacao(String causaMsg) {
        try {
            int relIdx = causaMsg.indexOf("relation \"");
            if (relIdx >= 0) {
                int start = relIdx + "relation \"".length();
                int end = causaMsg.indexOf("\"", start);
                if (end > start) {
                    return causaMsg.substring(start, end);
                }
            }
        } catch (Exception ignored) {}
        return null;
    }

    private String mapearCampoAmigavel(String relacao, String coluna) {
        if (relacao == null || coluna == null) return null;
        // Mapeamentos amigáveis para equipes_saude
        if ("equipes_saude".equals(relacao)) {
            return switch (coluna) {
                case "estabelecimento_id" -> "estabelecimento";
                case "ine" -> "INE";
                case "nome_referencia" -> "nomeReferencia";
                case "tipo_equipe" -> "tipoEquipe";
                case "data_ativacao" -> "dataAtivacao";
                case "status" -> "status";
                default -> coluna;
            };
        }
        // Mapeamentos para vinculos_profissional_equipe
        if ("vinculos_profissional_equipe".equals(relacao)) {
            return switch (coluna) {
                case "profissional_id" -> "profissional";
                case "equipe_id" -> "equipe";
                case "data_inicio" -> "dataInicio";
                case "status" -> "status";
                default -> coluna;
            };
        }
        return coluna;
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
