package com.upsaude.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * Configuração do MessageSource para mensagens de validação centralizadas.
 * 
 * <p>
 * Este bean permite que as mensagens de validação sejam carregadas do arquivo
 * application-validation-messages.properties, centralizando todas as mensagens
 * do sistema em um único local.
 * </p>
 */
@Configuration
public class ValidationMessagesConfig {

    /**
     * Configura o MessageSource para carregar mensagens de validação.
     * 
     * <p>
     * O arquivo de propriedades está localizado em:
     * config/common/validation/application-validation-messages.properties
     * </p>
     * 
     * <p>
     * Marcado como @Primary para garantir que seja usado pelo Bean Validation.
     * </p>
     * 
     * @return MessageSource configurado
     */
    @Bean
    @Primary
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("config/common/validation/application-validation-messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(false);
        messageSource.setFallbackToSystemLocale(true);
        return messageSource;
    }

    /**
     * Configura o LocalValidatorFactoryBean para usar o MessageSource configurado.
     * Isso garante que as mensagens de validação sejam resolvidas corretamente.
     * 
     * @param messageSource MessageSource injetado
     * @return LocalValidatorFactoryBean configurado
     */
    @Bean
    public LocalValidatorFactoryBean validator(MessageSource messageSource) {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource);
        return validator;
    }
}
