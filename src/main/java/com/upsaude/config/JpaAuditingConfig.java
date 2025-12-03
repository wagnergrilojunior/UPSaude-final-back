package com.upsaude.config;

import java.time.OffsetDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Configuração para JPA Auditing usando OffsetDateTime ao invés de LocalDateTime.
 * Isso resolve o erro: "Cannot convert unsupported date type java.time.LocalDateTime to java.time.OffsetDateTime"
 */
@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
public class JpaAuditingConfig {

    /**
     * Provider customizado que retorna OffsetDateTime ao invés de LocalDateTime.
     * Necessário porque nossas entidades usam OffsetDateTime nos campos de auditoria.
     */
    @Bean
    public DateTimeProvider auditingDateTimeProvider() {
        return () -> Optional.of(OffsetDateTime.now());
    }
}

