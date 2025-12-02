package com.upsaude.config;

import com.upsaude.util.HttpMetricsInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuração do Web MVC para registrar interceptores.
 * 
 * @author UPSaúde
 */
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final HttpMetricsInterceptor httpMetricsInterceptor;

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(httpMetricsInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns(
                "/actuator/**",
                "/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui.html"
            );
    }
}

