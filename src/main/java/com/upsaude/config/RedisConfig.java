package com.upsaude.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import io.lettuce.core.TimeoutOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.time.Duration;

/**
 * Configuração do Redis/Valkey para cache da aplicação.
 * 
 * Compatível com Redis e Valkey (fork do Redis usado pelo Render).
 * Esta configuração utiliza Lettuce como cliente e configura:
 * - TTL padrão de 5 minutos
 * - Serialização JSON para valores
 * - Prefixo de chave "upsaude::"
 * - Suporte a tipos Java modernos (LocalDateTime, OffsetDateTime, etc.)
 * 
 * Nota: Valkey é 100% compatível com Redis e funciona com a mesma configuração.
 * O Render usa Valkey nas novas instâncias Key-Value.
 * 
 * IMPORTANTE: Esta configuração é desabilitada no profile 'local' para permitir
 * desenvolvimento sem Redis. Use @Profile("!local") para desabilitar.
 * 
 * @author UPSaúde
 */
@Slf4j
@Configuration
@EnableCaching
@Profile("!local")
public class RedisConfig {

    @Value("${spring.redis.url:redis://localhost:6379}")
    private String redisUrl;

    @Value("${spring.cache.redis.time-to-live:300000}")
    private long cacheTtl;

    @Value("${spring.cache.redis.key-prefix:upsaude::}")
    private String keyPrefix;

    /**
     * Faz o parse da URL do Redis e extrai host, porta, senha e database.
     * Suporta formatos: redis://host:port, redis://password@host:port, redis://host:port/database
     * 
     * @param url URL do Redis no formato redis://[password@]host:port[/database]
     * @return array com [host, port, password, database]
     */
    private String[] parseRedisUrl(String url) {
        try {
            URI uri = URI.create(url);
            String host = uri.getHost();
            if (host == null || host.isEmpty()) {
                throw new IllegalArgumentException("Host não pode ser nulo ou vazio na URL do Redis");
            }
            
            int port = uri.getPort() > 0 ? uri.getPort() : 6379;
            String password = null;
            int database = 0;
            
            // Extrai senha se presente no formato password@host
            String userInfo = uri.getUserInfo();
            if (userInfo != null && !userInfo.isEmpty()) {
                password = userInfo;
            }
            
            // Extrai database do path se presente
            String path = uri.getPath();
            if (path != null && path.length() > 1) {
                try {
                    database = Integer.parseInt(path.substring(1));
                } catch (NumberFormatException e) {
                    log.warn("Database inválido na URL do Redis: {}. Usando database 0.", path);
                }
            }
            
            return new String[]{host, String.valueOf(port), password != null ? password : "", String.valueOf(database)};
        } catch (Exception e) {
            log.error("Erro ao fazer parse da URL do Redis: {}. Usando valores padrão.", url, e);
            return new String[]{"localhost", "6379", "", "0"};
        }
    }

    /**
     * Configura a conexão com o Redis/Valkey usando Lettuce.
     * Configurado para não bloquear a inicialização da aplicação.
     * 
     * Compatível com Redis e Valkey (protocolo RESP).
     * 
     * @return RedisConnectionFactory configurado
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        String[] redisConfig = parseRedisUrl(redisUrl);
        String redisHost = redisConfig[0];
        int redisPort = Integer.parseInt(redisConfig[1]);
        String redisPassword = redisConfig[2];
        int redisDatabase = Integer.parseInt(redisConfig[3]);
        
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisHost);
        config.setPort(redisPort);
        config.setDatabase(redisDatabase);
        
        if (redisPassword != null && !redisPassword.isEmpty()) {
            config.setPassword(redisPassword);
        }

        // Configuração do cliente Lettuce com timeouts adequados
        SocketOptions socketOptions = SocketOptions.builder()
            .connectTimeout(Duration.ofSeconds(5)) // Timeout de conexão aumentado
            .keepAlive(true) // Mantém conexão viva
            .build();

        TimeoutOptions timeoutOptions = TimeoutOptions.builder()
            .fixedTimeout(Duration.ofSeconds(5)) // Timeout de comandos aumentado
            .build();

        ClientOptions clientOptions = ClientOptions.builder()
            .socketOptions(socketOptions)
            .timeoutOptions(timeoutOptions)
            .autoReconnect(true) // Reconecta automaticamente
            .pingBeforeActivateConnection(true) // Valida conexão antes de usar
            .build();

        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
            .clientOptions(clientOptions)
            .commandTimeout(Duration.ofSeconds(5)) // Timeout de comandos aumentado
            .shutdownTimeout(Duration.ofSeconds(2)) // Tempo para fechar conexões
            .build();

        LettuceConnectionFactory factory = new LettuceConnectionFactory(config, clientConfig);
        // Não valida no startup para não bloquear, mas valida quando usar
        factory.setValidateConnection(false);
        factory.afterPropertiesSet();
        
        // Tenta validar a conexão de forma assíncrona e loga o resultado
        try {
            var connection = factory.getConnection();
            connection.ping();
            connection.close();
            log.info("✅ Redis conectado com sucesso - Host: {}, Port: {}, Database: {}", 
                redisHost, redisPort, redisDatabase);
        } catch (Exception e) {
            log.warn("⚠️  Redis não está disponível no momento - Host: {}, Port: {}, Database: {}. " +
                "A aplicação continuará sem cache. Erro: {}. " +
                "Verifique se o Redis está rodando e se a variável de ambiente REDIS_URL está configurada corretamente.", 
                redisHost, redisPort, redisDatabase, e.getMessage());
            // Não lança exceção para não bloquear startup - aplicação funciona sem cache
        }
        
        return factory;
    }

    /**
     * Configura o CacheManager global com TTL padrão e serialização JSON.
     * 
     * IMPORTANTE: Cria um ObjectMapper específico para Redis diretamente aqui,
     * sem criar um bean separado, para evitar conflitos com o ObjectMapper padrão
     * usado pelo Spring Boot para requisições HTTP.
     * 
     * @param connectionFactory Factory de conexão Redis
     * @return CacheManager configurado
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        // Cria ObjectMapper específico para Redis (não é um bean para evitar conflitos)
        ObjectMapper redisObjectMapper = new ObjectMapper();
        redisObjectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        redisObjectMapper.registerModule(new JavaTimeModule());
        redisObjectMapper.activateDefaultTyping(
            redisObjectMapper.getPolymorphicTypeValidator(),
            ObjectMapper.DefaultTyping.NON_FINAL
        );
        
        GenericJackson2JsonRedisSerializer jsonSerializer = new GenericJackson2JsonRedisSerializer(redisObjectMapper);
        
        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMillis(cacheTtl))
            .prefixCacheNameWith(keyPrefix)
            .serializeKeysWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    new StringRedisSerializer()
                )
            )
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer)
            )
            .disableCachingNullValues();

        return RedisCacheManager.builder(connectionFactory)
            .cacheDefaults(cacheConfig)
            .transactionAware()
            .build();
    }
}

