package com.upsaude.config;

import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Configuração JPA separada para API e JOB
 * 
 * API: EntityManagerFactory e TransactionManager @Primary
 * JOB: EntityManagerFactory e TransactionManager com @Qualifier
 */
@Slf4j
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {
                "com.upsaude.repository.agendamento",
                "com.upsaude.repository.alergia",
                "com.upsaude.repository.clinica",
                "com.upsaude.repository.convenio",
                "com.upsaude.repository.deficiencia",
                "com.upsaude.repository.estabelecimento",
                "com.upsaude.repository.farmacia",
                "com.upsaude.repository.faturamento",
                "com.upsaude.repository.financeiro",
                "com.upsaude.repository.geral",
                "com.upsaude.repository.paciente",
                "com.upsaude.repository.profissional",
                "com.upsaude.repository.referencia",
                "com.upsaude.repository.sistema.auditoria",
                "com.upsaude.repository.sistema.auth",
                "com.upsaude.repository.sistema.lgpd",
                "com.upsaude.repository.sistema.multitenancy",
                "com.upsaude.repository.sistema.notificacao",
                "com.upsaude.repository.sistema.relatorios",
                "com.upsaude.repository.sistema.usuario",
                "com.upsaude.repository.sistema.integracao",
                "com.upsaude.repository.cnes",
                "com.upsaude.repository.fhir",
                "com.upsaude.repository.vacinacao"
// NÃO incluir sistema.importacao aqui - será configurado separadamente para JOB
}, entityManagerFactoryRef = "apiEntityManagerFactory", transactionManagerRef = "apiTransactionManager")
public class JpaConfig {

        /**
         * EntityManagerFactory da API - @Primary - USO EXCLUSIVO API
         * Gerencia todas as entidades exceto sistema.importacao
         */
        @Bean
        @Primary
        @Qualifier("apiEntityManagerFactory")
        public LocalContainerEntityManagerFactoryBean apiEntityManagerFactory(
                        EntityManagerFactoryBuilder builder,
                        @Qualifier("apiDataSource") DataSource apiDataSource) {

                log.info("Configurando EntityManagerFactory API...");

                Map<String, String> properties = new HashMap<>();
                properties.put("hibernate.hbm2ddl.auto", "none");
                properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
                properties.put("hibernate.format_sql", "false");
                properties.put("hibernate.show_sql", "false");
                properties.put("hibernate.use_sql_comments", "false");

                LocalContainerEntityManagerFactoryBean emf = builder
                                .dataSource(apiDataSource)
                                .packages(
                                                "com.upsaude.entity.agendamento",
                                                "com.upsaude.entity.alergia",
                                                "com.upsaude.entity.clinica",
                                                "com.upsaude.entity.convenio",
                                                "com.upsaude.entity.deficiencia",
                                                "com.upsaude.entity.estabelecimento",
                                                "com.upsaude.entity.farmacia",
                                                "com.upsaude.entity.faturamento",
                                                "com.upsaude.entity.financeiro",
                                                "com.upsaude.entity.geral",
                                                "com.upsaude.entity.paciente",
                                                "com.upsaude.entity.profissional",
                                                "com.upsaude.entity.referencia",
                                                "com.upsaude.entity.sistema.auditoria",
                                                "com.upsaude.entity.sistema.auth",
                                                "com.upsaude.entity.sistema.lgpd",
                                                "com.upsaude.entity.sistema.multitenancy",
                                                "com.upsaude.entity.sistema.notificacao",
                                                "com.upsaude.entity.sistema.relatorios",
                                                "com.upsaude.entity.sistema.usuario",
                                                "com.upsaude.entity.sistema.integracao",
                                                "com.upsaude.entity.cnes",
                                                "com.upsaude.entity.fhir",
                                                "com.upsaude.entity.vacinacao"
                                // NÃO incluir sistema.importacao aqui
                                )
                                .persistenceUnit("api")
                                .properties(properties)
                                .build();

                log.info("EntityManagerFactory API configurado com sucesso");
                return emf;
        }

        /**
         * TransactionManager da API - @Primary - USO EXCLUSIVO API
         * Usado automaticamente por @Transactional em controllers/services API
         */
        @Bean
        @Primary
        @Qualifier("apiTransactionManager")
        public PlatformTransactionManager apiTransactionManager(
                        @Qualifier("apiEntityManagerFactory") EntityManagerFactory apiEntityManagerFactory) {

                log.info("Configurando TransactionManager API...");
                JpaTransactionManager txManager = new JpaTransactionManager();
                txManager.setEntityManagerFactory(apiEntityManagerFactory);
                log.info("TransactionManager API configurado com sucesso");
                return txManager;
        }

        /**
         * EntityManagerFactory do JOB - @Qualifier("jobEntityManagerFactory") - USO
         * EXCLUSIVO JOB - NÃO USAR NA API
         * Gerencia entidades de sistema.importacao e entidades de referência
         * necessárias (Tenant, Estabelecimentos)
         * 
         * NOTA: Inclui Tenant e Estabelecimentos porque BaseEntity (usado por
         * ImportJob/ImportJobError)
         * tem relações com essas entidades. O JOB apenas lê essas entidades, não as
         * modifica.
         */
        @Bean
        @Qualifier("jobEntityManagerFactory")
        public LocalContainerEntityManagerFactoryBean jobEntityManagerFactory(
                        EntityManagerFactoryBuilder builder,
                        @Qualifier("jobDataSource") DataSource jobDataSource) {

                log.info("Configurando EntityManagerFactory JOB...");

                Map<String, String> properties = new HashMap<>();
                properties.put("hibernate.hbm2ddl.auto", "none");
                properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
                properties.put("hibernate.format_sql", "false");
                properties.put("hibernate.show_sql", "false");
                properties.put("hibernate.use_sql_comments", "false");
                // Configurações específicas para jobs longos
                properties.put("hibernate.jdbc.batch_size", "50");
                properties.put("hibernate.order_inserts", "true");
                properties.put("hibernate.order_updates", "true");
                // Configurações necessárias para suportar níveis de isolamento customizados
                // No Hibernate 6.x com Spring Boot 3.x, o HibernateJpaDialect precisa que:
                // 1. O provider não desabilite o autocommit (prepareConnection deve estar
                // habilitado)
                // 2. O modo de release da conexão seja ON_CLOSE (padrão no Hibernate 6.x)
                // A propriedade abaixo habilita o prepareConnection, permitindo que o
                // HibernateJpaDialect
                // configure o nível de isolamento antes de iniciar a transação
                properties.put("hibernate.connection.provider_disables_autocommit", "false");

                LocalContainerEntityManagerFactoryBean emf = builder
                                .dataSource(jobDataSource)
                                .packages(
                                                "com.upsaude.entity.sistema.importacao",
                                                // Entidades de referência necessárias para BaseEntity e suas
                                                // dependências
                                                // transitivas
                                                // NOTA: Incluímos todas as entidades que podem ser referenciadas
                                                // transitivamente
                                                // para evitar erros de "does not belong to the same persistence unit"
                                                "com.upsaude.entity.sistema.multitenancy", // Tenant
                                                "com.upsaude.entity.sistema.usuario", // UsuariosSistema
                                                "com.upsaude.entity.sistema.auth", // User (referenciado por
                                                                                   // UsuariosSistema)
                                                "com.upsaude.entity.sistema.notificacao", // Notificacao
                                                "com.upsaude.entity.sistema.integracao", // IntegracaoGov
                                                "com.upsaude.entity.sistema.lgpd", // LGPDConsentimento
                                                "com.upsaude.entity.cnes", // CNES
                                                "com.upsaude.entity.estabelecimento", // Estabelecimentos
                                                "com.upsaude.entity.farmacia", // Farmacia (referenciado por
                                                                               // IntegracaoGov)
                                                "com.upsaude.entity.paciente", // Endereco, Paciente
                                                "com.upsaude.entity.profissional", // ProfissionaisSaude
                                                "com.upsaude.entity.referencia", // Cidades, Estados, CID, SIGTAP, SIA
                                                "com.upsaude.entity.convenio", // Convenio (referenciado por Paciente)
                                                "com.upsaude.entity.agendamento", // Pode ser referenciado
                                                "com.upsaude.entity.alergia", // Pode ser referenciado
                                                "com.upsaude.entity.clinica", // Pode ser referenciado
                                                "com.upsaude.entity.deficiencia", // Pode ser referenciado
                                                "com.upsaude.entity.geral", // Pode ser referenciado
                                                "com.upsaude.entity.vacinacao", // ViaAdministracao (referenciado por
                                                                                // ReceitaItem)
                                                "com.upsaude.entity.fhir", // FHIR Sync objects
                                                "com.upsaude.entity.financeiro", // CompetenciaFinanceira (referenciado por Agendamento)
                                                "com.upsaude.entity.faturamento" // Pode ser referenciado
                                )
                                .persistenceUnit("job")
                                .properties(properties)
                                .build();

                log.info("EntityManagerFactory JOB configurado com sucesso (persistence unit: job)");
                return emf;
        }

        /**
         * TransactionManager do JOB - @Qualifier("jobTransactionManager") - USO
         * EXCLUSIVO JOB - NÃO USAR NA API
         * Deve ser injetado explicitamente nos workers e scheduler
         * 
         * IMPORTANTE: As conexões da API e JOB são completamente independentes:
         * - API usa apiDataSource e apiTransactionManager (pool separado)
         * - JOB usa jobDataSource e jobTransactionManager (pool separado)
         * - Cada um tem seu próprio EntityManagerFactory e não interfere no outro
         * 
         * O PostgreSQL usa READ_COMMITTED por padrão, que é o nível desejado para os
         * jobs.
         * Não configuramos isolamento customizado para evitar problemas com Hibernate
         * 6.x.
         */
        @Bean
        @Qualifier("jobTransactionManager")
        public PlatformTransactionManager jobTransactionManager(
                        @Qualifier("jobEntityManagerFactory") EntityManagerFactory jobEntityManagerFactory) {

                log.info("Configurando TransactionManager JOB...");
                JpaTransactionManager txManager = new JpaTransactionManager();
                txManager.setEntityManagerFactory(jobEntityManagerFactory);
                log.info("TransactionManager JOB configurado com sucesso (conexões independentes da API)");
                return txManager;
        }
}
