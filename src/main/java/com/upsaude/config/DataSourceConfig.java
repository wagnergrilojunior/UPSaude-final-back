package com.upsaude.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class DataSourceConfig {

    /**
     * HikariConfig para API - USO EXCLUSIVO API
     * Pool menor, estável, baixa latência para requisições HTTP
     */
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.api.hikari")
    public HikariConfig apiHikariConfig(
            @Value("${spring.datasource.api.url:${spring.datasource.url:}}") String jdbcUrl,
            @Value("${spring.datasource.api.username:${spring.datasource.username:}}") String username,
            @Value("${spring.datasource.api.password:${spring.datasource.password:}}") String password,
            @Value("${spring.datasource.api.driver-class-name:${spring.datasource.driver-class-name:org.postgresql.Driver}}") String driverClassName) {
        HikariConfig config = new HikariConfig();

        if (jdbcUrl == null || jdbcUrl.isEmpty()) {
            log.error("ERRO CRÍTICO: spring.datasource.api.url não está configurado!");
            throw new IllegalStateException("spring.datasource.api.url é obrigatório. Verifique as variáveis de ambiente ou application.properties");
        }

        if (username == null || username.isEmpty()) {
            log.error("ERRO CRÍTICO: spring.datasource.api.username não está configurado!");
            throw new IllegalStateException("spring.datasource.api.username é obrigatório. Verifique as variáveis de ambiente ou application.properties");
        }

        String urlForLog = jdbcUrl.replaceAll("password=[^&;]*", "password=***");
        log.info("Configurando DataSource API HikariCP - URL: {}, Username: {}, Driver: {}",
                urlForLog, username, driverClassName);

        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password != null ? password : "");
        config.setDriverClassName(driverClassName != null ? driverClassName : "org.postgresql.Driver");

        // Configurações específicas do PostgreSQL
        config.addDataSourceProperty("prepareThreshold", "0");
        config.addDataSourceProperty("preparedStatementCacheQueries", "0");
        config.addDataSourceProperty("preparedStatementCacheSizeMiB", "0");
        config.addDataSourceProperty("tcpKeepAlive", "true");
        config.addDataSourceProperty("socketTimeout", "30");
        config.addDataSourceProperty("loginTimeout", "10");
        config.addDataSourceProperty("cancelSignalTimeout", "30");

        config.setConnectionTestQuery("SELECT 1");
        config.setInitializationFailTimeout(30000);
        config.setRegisterMbeans(false);

        log.info("DataSource API HikariCP configurado com prepared statement cache desabilitado");
        log.info("Configurações do pool API: maxPoolSize={}, minIdle={}, connectionTimeout={}ms",
                config.getMaximumPoolSize(), config.getMinimumIdle(), config.getConnectionTimeout());

        return config;
    }

    /**
     * HikariConfig para JOB - USO EXCLUSIVO JOB - NÃO USAR NA API
     * Pool dedicado, tolera conexões longas para processamento pesado
     */
    @Bean
    @Qualifier("jobHikariConfig")
    @ConfigurationProperties("spring.datasource.job.hikari")
    public HikariConfig jobHikariConfig(
            @Value("${spring.datasource.job.url:${spring.datasource.url:}}") String jdbcUrl,
            @Value("${spring.datasource.job.username:${spring.datasource.username:}}") String username,
            @Value("${spring.datasource.job.password:${spring.datasource.password:}}") String password,
            @Value("${spring.datasource.job.driver-class-name:${spring.datasource.driver-class-name:org.postgresql.Driver}}") String driverClassName) {
        HikariConfig config = new HikariConfig();

        if (jdbcUrl == null || jdbcUrl.isEmpty()) {
            log.error("ERRO CRÍTICO: spring.datasource.job.url não está configurado!");
            throw new IllegalStateException("spring.datasource.job.url é obrigatório. Verifique as variáveis de ambiente ou application.properties");
        }

        if (username == null || username.isEmpty()) {
            log.error("ERRO CRÍTICO: spring.datasource.job.username não está configurado!");
            throw new IllegalStateException("spring.datasource.job.username é obrigatório. Verifique as variáveis de ambiente ou application.properties");
        }

        String urlForLog = jdbcUrl.replaceAll("password=[^&;]*", "password=***");
        log.info("Configurando DataSource JOB HikariCP - URL: {}, Username: {}, Driver: {}",
                urlForLog, username, driverClassName);

        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password != null ? password : "");
        config.setDriverClassName(driverClassName != null ? driverClassName : "org.postgresql.Driver");

        // Configurações específicas do PostgreSQL
        config.addDataSourceProperty("prepareThreshold", "0");
        config.addDataSourceProperty("preparedStatementCacheQueries", "0");
        config.addDataSourceProperty("preparedStatementCacheSizeMiB", "0");
        config.addDataSourceProperty("tcpKeepAlive", "true");
        config.addDataSourceProperty("socketTimeout", "30");
        config.addDataSourceProperty("loginTimeout", "10");
        config.addDataSourceProperty("cancelSignalTimeout", "30");

        config.setConnectionTestQuery("SELECT 1");
        config.setInitializationFailTimeout(30000);
        config.setRegisterMbeans(false);

        log.info("DataSource JOB HikariCP configurado com prepared statement cache desabilitado");
        log.info("Configurações do pool JOB: maxPoolSize={}, minIdle={}, connectionTimeout={}ms",
                config.getMaximumPoolSize(), config.getMinimumIdle(), config.getConnectionTimeout());

        return config;
    }

    /**
     * DataSource da API - @Primary - USO EXCLUSIVO API
     * Usado por controllers, services e repositories da API HTTP
     */
    @Bean
    @Primary
    @Qualifier("apiDataSource")
    public DataSource apiDataSource(@Qualifier("apiHikariConfig") HikariConfig apiHikariConfig) {
        try {
            log.info("Inicializando pool de conexões HikariCP API...");
            HikariDataSource dataSource = new HikariDataSource(apiHikariConfig);

            try (java.sql.Connection connection = dataSource.getConnection()) {
                log.info("Conexão API com banco de dados estabelecida com sucesso!");
            } catch (Exception e) {
                log.error("ERRO ao validar conexão inicial API com banco de dados: {}", e.getMessage(), e);
                log.error("Verifique se:");
                log.error("1. O host do banco está acessível do ambiente de produção");
                log.error("2. As credenciais estão corretas");
                log.error("3. Não há firewall bloqueando a conexão");
                log.error("4. A URL do banco está correta: {}", apiHikariConfig.getJdbcUrl().replaceAll("password=[^&;]*", "password=***"));
                throw new RuntimeException("Falha ao conectar ao banco de dados (API). Verifique os logs acima para detalhes.", e);
            }

            log.info("Pool API inicializado: maxPoolSize={}, minIdle={}", 
                    apiHikariConfig.getMaximumPoolSize(), apiHikariConfig.getMinimumIdle());
            return dataSource;
        } catch (Exception e) {
            log.error("ERRO CRÍTICO ao criar DataSource API: {}", e.getMessage(), e);
            if (e.getCause() instanceof java.net.SocketException) {
                log.error("PROBLEMA DE REDE DETECTADO:");
                log.error("- Verifique se o ambiente tem acesso à internet");
                log.error("- Verifique se há firewall bloqueando conexões");
                log.error("- Verifique se o host do banco está correto e acessível");
                log.error("- Host tentado: {}", extractHostFromUrl(apiHikariConfig.getJdbcUrl()));
            }
            throw e;
        }
    }

    /**
     * DataSource do JOB - @Qualifier("jobDataSource") - USO EXCLUSIVO JOB - NÃO USAR NA API
     * Usado por scheduler, processor, workers e writers de importação
     */
    @Bean
    @Qualifier("jobDataSource")
    public DataSource jobDataSource(@Qualifier("jobHikariConfig") HikariConfig jobHikariConfig) {
        try {
            log.info("Inicializando pool de conexões HikariCP JOB...");
            HikariDataSource dataSource = new HikariDataSource(jobHikariConfig);

            try (java.sql.Connection connection = dataSource.getConnection()) {
                log.info("Conexão JOB com banco de dados estabelecida com sucesso!");
            } catch (Exception e) {
                log.error("ERRO ao validar conexão inicial JOB com banco de dados: {}", e.getMessage(), e);
                log.error("Verifique se:");
                log.error("1. O host do banco está acessível do ambiente de produção");
                log.error("2. As credenciais estão corretas");
                log.error("3. Não há firewall bloqueando a conexão");
                log.error("4. A URL do banco está correta: {}", jobHikariConfig.getJdbcUrl().replaceAll("password=[^&;]*", "password=***"));
                throw new RuntimeException("Falha ao conectar ao banco de dados (JOB). Verifique os logs acima para detalhes.", e);
            }

            log.info("Pool JOB inicializado: maxPoolSize={}, minIdle={}", 
                    jobHikariConfig.getMaximumPoolSize(), jobHikariConfig.getMinimumIdle());
            return dataSource;
        } catch (Exception e) {
            log.error("ERRO CRÍTICO ao criar DataSource JOB: {}", e.getMessage(), e);
            if (e.getCause() instanceof java.net.SocketException) {
                log.error("PROBLEMA DE REDE DETECTADO:");
                log.error("- Verifique se o ambiente tem acesso à internet");
                log.error("- Verifique se há firewall bloqueando conexões");
                log.error("- Verifique se o host do banco está correto e acessível");
                log.error("- Host tentado: {}", extractHostFromUrl(jobHikariConfig.getJdbcUrl()));
            }
            throw e;
        }
    }

    private String extractHostFromUrl(String jdbcUrl) {
        if (jdbcUrl == null) {
            return "null";
        }
        try {

            int start = jdbcUrl.indexOf("://") + 3;
            int end = jdbcUrl.indexOf("/", start);
            if (end == -1) {
                end = jdbcUrl.indexOf("?", start);
            }
            if (end == -1) {
                end = jdbcUrl.length();
            }
            return jdbcUrl.substring(start, end);
        } catch (Exception e) {
            return jdbcUrl;
        }
    }
}
