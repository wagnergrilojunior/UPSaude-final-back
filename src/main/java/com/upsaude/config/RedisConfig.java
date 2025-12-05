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
                "Verifique se o Redis está rodando e se as variáveis de ambiente estão configuradas corretamente.", 
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

