package com.upsaude.config;

import com.upsaude.validation.web.EnumConverterFactory;
import com.upsaude.validation.web.ValidatedPageableResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Configurações globais de validação/conversão para requests.
 * - Enum genérico para query/path (sem enum por enum)
 * - Pageable validado (page/size/sort)
 */
@Configuration
public class ValidationWebConfig implements WebMvcConfigurer {

    private static final int DEFAULT_MAX_PAGE_SIZE = 200;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new EnumConverterFactory());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        // Coloca na frente para garantir que valida antes do resolver padrão do Spring Data
        resolvers.add(0, new ValidatedPageableResolver(DEFAULT_MAX_PAGE_SIZE));
    }
}

