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
        config.setInitializationFailTimeout(-1); // -1 = nunca falhar no startup, conexão lazy
        config.setRegisterMbeans(false);

        // Define valores padrão ANTES do @ConfigurationProperties aplicar
        // Isso garante que sempre teremos valores válidos, mesmo se @ConfigurationProperties falhar
        config.setMaximumPoolSize(7); // Valor padrão para produção (será sobrescrito se configurado)
        config.setMinimumIdle(2); // Valor padrão para produção (será sobrescrito se configurado)

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
        config.setInitializationFailTimeout(-1); // -1 = nunca falhar no startup, conexão lazy
        config.setRegisterMbeans(false);

        // Define valores padrão ANTES do @ConfigurationProperties aplicar
        // Isso garante que sempre teremos valores válidos, mesmo se @ConfigurationProperties falhar
        config.setMaximumPoolSize(4); // Valor padrão para produção (será sobrescrito se configurado)
        config.setMinimumIdle(1); // Valor padrão para produção (será sobrescrito se configurado)

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
        log.info("Criando DataSource API HikariCP (lazy initialization - conexão será estabelecida sob demanda)...");
        HikariDataSource dataSource = new HikariDataSource(apiHikariConfig);
        
        // NÃO validamos conexão no startup para evitar fail-fast
        // O pool será inicializado de forma lazy quando a primeira conexão for solicitada
        // O health check do Actuator será responsável por verificar a disponibilidade do banco
        
        log.info("DataSource API HikariCP criado com sucesso. Pool: maxPoolSize={}, minIdle={}", 
                apiHikariConfig.getMaximumPoolSize(), apiHikariConfig.getMinimumIdle());
        log.info("Nota: Conexão com o banco será estabelecida sob demanda. Use /api/actuator/health para verificar disponibilidade.");
        
        return dataSource;
    }

    /**
     * DataSource do JOB - @Qualifier("jobDataSource") - USO EXCLUSIVO JOB - NÃO USAR NA API
     * Usado por scheduler, processor, workers e writers de importação
     */
    @Bean
    @Qualifier("jobDataSource")
    public DataSource jobDataSource(@Qualifier("jobHikariConfig") HikariConfig jobHikariConfig) {
        log.info("Criando DataSource JOB HikariCP (lazy initialization - conexão será estabelecida sob demanda)...");
        HikariDataSource dataSource = new HikariDataSource(jobHikariConfig);
        
        // NÃO validamos conexão no startup para evitar fail-fast
        // O pool será inicializado de forma lazy quando a primeira conexão for solicitada
        // O health check do Actuator será responsável por verificar a disponibilidade do banco
        
        log.info("DataSource JOB HikariCP criado com sucesso. Pool: maxPoolSize={}, minIdle={}", 
                jobHikariConfig.getMaximumPoolSize(), jobHikariConfig.getMinimumIdle());
        log.info("Nota: Conexão com o banco será estabelecida sob demanda. Use /api/actuator/health para verificar disponibilidade.");
        
        return dataSource;
    }

}
