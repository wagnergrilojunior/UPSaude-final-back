package com.upsaude.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Configuração JPA para repositories de JOB
 * 
 * USO EXCLUSIVO JOB - NÃO USAR NA API
 * 
 * Configura repositories de sistema.importacao para usar EntityManagerFactory JOB
 */
@Configuration
@EnableJpaRepositories(
    basePackages = "com.upsaude.repository.sistema.importacao",
    entityManagerFactoryRef = "jobEntityManagerFactory",
    transactionManagerRef = "jobTransactionManager"
)
public class JpaJobConfig {
    // Configuração via @EnableJpaRepositories acima
}

