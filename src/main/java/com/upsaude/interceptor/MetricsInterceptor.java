package com.upsaude.interceptor;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Interceptor para coletar métricas detalhadas de requisições HTTP.
 * 
 * Coleta:
 * - Total de requisições por endpoint
 * - Tempo de resposta por endpoint
 * - Erros por endpoint
 * - Status codes
 * 
 * Essas métricas aparecem automaticamente no Spring Boot Admin.
 * 
 * @author UPSaude
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MetricsInterceptor implements HandlerInterceptor {

    private final MeterRegistry meterRegistry;
    private static final String START_TIME_ATTRIBUTE = "startTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Marca tempo de início
        request.setAttribute(START_TIME_ATTRIBUTE, System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
                                Object handler, Exception ex) {
        try {
            Long startTime = (Long) request.getAttribute(START_TIME_ATTRIBUTE);
            if (startTime != null) {
                long duration = System.currentTimeMillis() - startTime;
                String uri = request.getRequestURI();
                String method = request.getMethod();
                int status = response.getStatus();

                // Registra tempo de resposta
                Timer.builder("upsaude.http.request.duration")
                    .description("Duração de requisições HTTP")
                    .tag("uri", uri)
                    .tag("method", method)
                    .tag("status", String.valueOf(status))
                    .register(meterRegistry)
                    .record(duration, java.util.concurrent.TimeUnit.MILLISECONDS);

                // Conta requisições
                Counter.builder("upsaude.http.request.total")
                    .description("Total de requisições HTTP")
                    .tag("uri", uri)
                    .tag("method", method)
                    .tag("status", String.valueOf(status))
                    .register(meterRegistry)
                    .increment();

                // Conta erros (status >= 400)
                if (status >= 400) {
                    Counter.builder("upsaude.http.request.errors")
                        .description("Total de requisições com erro")
                        .tag("uri", uri)
                        .tag("method", method)
                        .tag("status", String.valueOf(status))
                        .tag("tipo", status >= 500 ? "server_error" : "client_error")
                        .register(meterRegistry)
                        .increment();
                }

                log.debug("Métrica registrada: {} {} - {}ms - Status: {}", 
                    method, uri, duration, status);
            }
        } catch (Exception e) {
            log.warn("Erro ao registrar métricas: {}", e.getMessage());
        }
    }
}

