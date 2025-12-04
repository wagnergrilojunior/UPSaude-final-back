package com.upsaude.config;

import com.upsaude.interceptor.MetricsInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuração para registrar interceptor de métricas.
 * 
 * @author UPSaude
 */
@Configuration
@RequiredArgsConstructor
public class WebMvcConfigCustomMetrics implements WebMvcConfigurer {

    private final MetricsInterceptor metricsInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(metricsInterceptor)
            .addPathPatterns("/v1/**")
            .excludePathPatterns("/actuator/**", "/swagger-ui/**", "/api-docs/**");
    }
}

