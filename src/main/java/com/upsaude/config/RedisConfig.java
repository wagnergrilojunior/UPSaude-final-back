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
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

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
 * @author UPSaúde
 */
@Configuration
@EnableCaching
public class RedisConfig {

    @Value("${spring.redis.host:localhost}")
    private String redisHost;

    @Value("${spring.redis.port:6379}")
    private int redisPort;

    @Value("${spring.redis.password:}")
    private String redisPassword;

    @Value("${spring.redis.database:0}")
    private int redisDatabase;

    @Value("${spring.cache.redis.time-to-live:300000}")
    private long cacheTtl;

    @Value("${spring.cache.redis.key-prefix:upsaude::}")
    private String keyPrefix;

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
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisHost);
        config.setPort(redisPort);
        config.setDatabase(redisDatabase);
        
        if (redisPassword != null && !redisPassword.isEmpty()) {
            config.setPassword(redisPassword);
        }

        // Configuração do cliente Lettuce para não bloquear startup
        SocketOptions socketOptions = SocketOptions.builder()
            .connectTimeout(Duration.ofSeconds(2)) // Timeout curto para não travar
            .build();

        TimeoutOptions timeoutOptions = TimeoutOptions.builder()
            .fixedTimeout(Duration.ofSeconds(2))
            .build();

        ClientOptions clientOptions = ClientOptions.builder()
            .socketOptions(socketOptions)
            .timeoutOptions(timeoutOptions)
            .autoReconnect(true) // Reconecta automaticamente
            .build();

        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
            .clientOptions(clientOptions)
            .commandTimeout(Duration.ofSeconds(2))
            .shutdownTimeout(Duration.ofSeconds(1))
            .build();

        LettuceConnectionFactory factory = new LettuceConnectionFactory(config, clientConfig);
        factory.setValidateConnection(false); // Não valida conexão no startup
        factory.afterPropertiesSet();
        
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

