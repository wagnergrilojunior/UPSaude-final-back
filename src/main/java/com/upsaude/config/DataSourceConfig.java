package com.upsaude.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class DataSourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.hikari")
    public HikariConfig hikariConfig(DataSourceProperties dataSourceProperties) {
        HikariConfig config = new HikariConfig();

        String jdbcUrl = dataSourceProperties.getUrl();
        String username = dataSourceProperties.getUsername();
        String password = dataSourceProperties.getPassword();
        String driverClassName = dataSourceProperties.getDriverClassName();

        if (jdbcUrl == null || jdbcUrl.isEmpty()) {
            log.error("ERRO CRÍTICO: spring.datasource.url não está configurado!");
            throw new IllegalStateException("spring.datasource.url é obrigatório. Verifique as variáveis de ambiente ou application.properties");
        }

        if (username == null || username.isEmpty()) {
            log.error("ERRO CRÍTICO: spring.datasource.username não está configurado!");
            throw new IllegalStateException("spring.datasource.username é obrigatório. Verifique as variáveis de ambiente ou application.properties");
        }

        String urlForLog = jdbcUrl.replaceAll("password=[^&;]*", "password=***");
        log.info("Configurando DataSource HikariCP - URL: {}, Username: {}, Driver: {}",
                urlForLog, username, driverClassName);

        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password != null ? password : "");
        config.setDriverClassName(driverClassName != null ? driverClassName : "org.postgresql.Driver");

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

        log.info("DataSource HikariCP configurado com prepared statement cache desabilitado");
        log.debug("Configurações do pool: maxPoolSize={}, minIdle={}, connectionTimeout={}ms",
                config.getMaximumPoolSize(), config.getMinimumIdle(), config.getConnectionTimeout());

        return config;
    }

    @Bean
    @Primary
    public DataSource dataSource(HikariConfig hikariConfig) {
        try {
            log.info("Inicializando pool de conexões HikariCP...");
            HikariDataSource dataSource = new HikariDataSource(hikariConfig);

            try (java.sql.Connection connection = dataSource.getConnection()) {
                log.info("Conexão com banco de dados estabelecida com sucesso!");
            } catch (Exception e) {
                log.error("ERRO ao validar conexão inicial com banco de dados: {}", e.getMessage(), e);
                log.error("Verifique se:");
                log.error("1. O host do banco está acessível do ambiente de produção");
                log.error("2. As credenciais estão corretas");
                log.error("3. Não há firewall bloqueando a conexão");
                log.error("4. A URL do banco está correta: {}", hikariConfig.getJdbcUrl().replaceAll("password=[^&;]*", "password=***"));
                throw new RuntimeException("Falha ao conectar ao banco de dados. Verifique os logs acima para detalhes.", e);
            }

            return dataSource;
        } catch (Exception e) {
            log.error("ERRO CRÍTICO ao criar DataSource: {}", e.getMessage(), e);
            if (e.getCause() instanceof java.net.SocketException) {
                log.error("PROBLEMA DE REDE DETECTADO:");
                log.error("- Verifique se o ambiente tem acesso à internet");
                log.error("- Verifique se há firewall bloqueando conexões");
                log.error("- Verifique se o host do banco está correto e acessível");
                log.error("- Host tentado: {}", extractHostFromUrl(hikariConfig.getJdbcUrl()));
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
