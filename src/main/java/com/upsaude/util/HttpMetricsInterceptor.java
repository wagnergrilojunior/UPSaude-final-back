package com.upsaude.util;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class HttpMetricsInterceptor implements HandlerInterceptor {

    private final MeterRegistry meterRegistry;
    private static final String TIMER_ATTRIBUTE = "http.request.timer";

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {

        Timer.Sample sample = Timer.start(meterRegistry);
        request.setAttribute(TIMER_ATTRIBUTE, sample);

        Counter.builder("upsaude.http.requests.total")
            .description("Total de requisições HTTP recebidas")
            .tag("method", request.getMethod())
            .tag("uri", getUriTag(request))
            .register(meterRegistry)
            .increment();

        return true;
    }

    @Override
    public void afterCompletion(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler,
            @Nullable Exception ex) {

        Timer.Sample sample = (Timer.Sample) request.getAttribute(TIMER_ATTRIBUTE);
        if (sample != null) {

            sample.stop(Timer.builder("upsaude.http.requests.latency")
                .description("Latência das requisições HTTP")
                .tag("method", request.getMethod())
                .tag("uri", getUriTag(request))
                .tag("status", String.valueOf(response.getStatus()))
                .register(meterRegistry));
        }

        int status = response.getStatus();
        if (status >= 400 && status < 500) {
            Counter.builder("upsaude.http.requests.failed")
                .description("Total de requisições HTTP que falharam")
                .tag("method", request.getMethod())
                .tag("uri", getUriTag(request))
                .tag("status", String.valueOf(status))
                .tag("type", "4xx")
                .register(meterRegistry)
                .increment();
        } else if (status >= 500) {
            Counter.builder("upsaude.http.requests.failed")
                .description("Total de requisições HTTP que falharam")
                .tag("method", request.getMethod())
                .tag("uri", getUriTag(request))
                .tag("status", String.valueOf(status))
                .tag("type", "5xx")
                .register(meterRegistry)
                .increment();
        }

        if (ex != null) {
            Counter.builder("upsaude.http.requests.exceptions")
                .description("Total de exceções não tratadas em requisições HTTP")
                .tag("method", request.getMethod())
                .tag("uri", getUriTag(request))
                .tag("exception", ex.getClass().getSimpleName())
                .register(meterRegistry)
                .increment();
        }
    }

    private String getUriTag(HttpServletRequest request) {
        String uri = request.getRequestURI();

        String contextPath = request.getContextPath();
        if (uri.startsWith(contextPath)) {
            uri = uri.substring(contextPath.length());
        }

        uri = uri.replaceAll("/[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}", "/{id}");
        uri = uri.replaceAll("/\\d+", "/{id}");

        return uri;
    }
}
