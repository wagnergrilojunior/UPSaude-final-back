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

/**
 * Configuração customizada do DataSource (HikariCP) para resolver problemas
 * com prepared statements e transações no PostgreSQL.
 * 
 * Este problema ocorria quando:
 * - Prepared statements eram criados em uma transação
 * - A conexão era devolvida ao pool
 * - A conexão era reutilizada mas os prepared statements não existiam mais
 * - Resultava em: ERROR: prepared statement "S_1" does not exist
 * 
 * Solução implementada:
 * - Desabilitar o cache de prepared statements no driver PostgreSQL
 * - Usar server-side prepared statements apenas quando necessário
 * - Validar conexões antes de usá-las
 * - Garantir que transações sejam gerenciadas corretamente
 * 
 * @author UPSaúde
 */
@Slf4j
@Configuration
public class DataSourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.hikari")
    public HikariConfig hikariConfig(DataSourceProperties dataSourceProperties) {
        HikariConfig config = new HikariConfig();
        
        // Configurações básicas do DataSource
        config.setJdbcUrl(dataSourceProperties.getUrl());
        config.setUsername(dataSourceProperties.getUsername());
        config.setPassword(dataSourceProperties.getPassword());
        config.setDriverClassName(dataSourceProperties.getDriverClassName());
        
        // Propriedades específicas do PostgreSQL para resolver problemas com prepared statements
        // IMPORTANTE: Desabilita COMPLETAMENTE o uso de server-side prepared statements
        // Isso evita o erro "prepared statement does not exist" quando conexões são reutilizadas
        config.addDataSourceProperty("prepareThreshold", "0");
        config.addDataSourceProperty("preparedStatementCacheQueries", "0");
        config.addDataSourceProperty("preparedStatementCacheSizeMiB", "0");
        
        // Outras propriedades para melhorar a estabilidade e reduzir conexões
        config.addDataSourceProperty("tcpKeepAlive", "true");
        // Socket timeout reduzido: mata conexões travadas após 10 segundos
        config.addDataSourceProperty("socketTimeout", "10");
        // Login timeout: falha rápido se não conseguir autenticar
        config.addDataSourceProperty("loginTimeout", "5");
        // Cancelar queries lentas após 10 segundos
        config.addDataSourceProperty("cancelSignalTimeout", "10");
        
        // Configuração para revalidar conexões automaticamente (mais rápido)
        config.setConnectionTestQuery("SELECT 1");
        
        // Configurações adicionais para reduzir conexões ociosas
        config.setInitializationFailTimeout(1); // Falha rápido se não conseguir conectar
        config.setRegisterMbeans(false); // Desabilita MBeans para reduzir overhead
        
        log.info("DataSource HikariCP configurado com prepared statement cache desabilitado");
        
        return config;
    }

    @Bean
    @Primary
    public DataSource dataSource(HikariConfig hikariConfig) {
        return new HikariDataSource(hikariConfig);
    }
}

