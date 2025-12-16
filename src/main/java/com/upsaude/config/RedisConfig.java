package com.upsaude.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.upsaude.enums.EscolaridadeEnum;
import com.upsaude.enums.EstadoCivilEnum;
import com.upsaude.enums.IdentidadeGeneroEnum;
import com.upsaude.enums.NacionalidadeEnum;
import com.upsaude.enums.OrientacaoSexualEnum;
import com.upsaude.enums.RacaCorEnum;
import com.upsaude.enums.SexoEnum;
import com.upsaude.enums.StatusPacienteEnum;
import com.upsaude.enums.TipoAtendimentoPreferencialEnum;
import com.upsaude.enums.TipoCnsEnum;
import com.upsaude.enums.TipoLogradouroEnum;
import com.upsaude.util.converter.EscolaridadeEnumDeserializer;
import com.upsaude.util.converter.EscolaridadeEnumSerializer;
import com.upsaude.util.converter.EstadoCivilEnumDeserializer;
import com.upsaude.util.converter.EstadoCivilEnumSerializer;
import com.upsaude.util.converter.IdentidadeGeneroEnumDeserializer;
import com.upsaude.util.converter.IdentidadeGeneroEnumSerializer;
import com.upsaude.util.converter.NacionalidadeEnumDeserializer;
import com.upsaude.util.converter.NacionalidadeEnumSerializer;
import com.upsaude.util.converter.OrientacaoSexualEnumDeserializer;
import com.upsaude.util.converter.OrientacaoSexualEnumSerializer;
import com.upsaude.util.converter.RacaCorEnumDeserializer;
import com.upsaude.util.converter.RacaCorEnumSerializer;
import com.upsaude.util.converter.SexoEnumDeserializer;
import com.upsaude.util.converter.SexoEnumSerializer;
import com.upsaude.util.converter.StatusPacienteEnumDeserializer;
import com.upsaude.util.converter.StatusPacienteEnumSerializer;
import com.upsaude.util.converter.TipoAtendimentoPreferencialEnumDeserializer;
import com.upsaude.util.converter.TipoAtendimentoPreferencialEnumSerializer;
import com.upsaude.util.converter.TipoCnsEnumDeserializer;
import com.upsaude.util.converter.TipoCnsEnumSerializer;
import com.upsaude.util.converter.TipoLogradouroEnumDeserializer;
import com.upsaude.util.converter.TipoLogradouroEnumSerializer;
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

@Slf4j
@Configuration
@EnableCaching
@Profile("!local")
public class RedisConfig {

    @Value("${spring.data.redis.host:localhost}")
    private String redisHost;

    @Value("${spring.data.redis.port:6379}")
    private int redisPort;

    @Value("${spring.data.redis.password:}")
    private String redisPassword;

    @Value("${spring.data.redis.ssl.enabled:false}")
    private boolean sslEnabled;

    @Value("${spring.cache.redis.time-to-live:300000}")
    private long cacheTtl;

    @Value("${spring.cache.redis.key-prefix:upsaude::}")
    private String keyPrefix;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisHost);
        config.setPort(redisPort);
        config.setDatabase(0);

        if (redisPassword != null && !redisPassword.isEmpty()) {
            config.setPassword(redisPassword);
        }

        SocketOptions socketOptions = SocketOptions.builder()
            .connectTimeout(Duration.ofSeconds(10))
            .keepAlive(true)
            .build();

        TimeoutOptions timeoutOptions = TimeoutOptions.builder()
            .fixedTimeout(Duration.ofSeconds(10))
            .build();

        ClientOptions clientOptions = ClientOptions.builder()
            .socketOptions(socketOptions)
            .timeoutOptions(timeoutOptions)
            .autoReconnect(true)
            .pingBeforeActivateConnection(false)
            .build();

        LettuceClientConfiguration.LettuceClientConfigurationBuilder clientConfigBuilder =
            LettuceClientConfiguration.builder()
                .clientOptions(clientOptions)
                .commandTimeout(Duration.ofSeconds(10))
                .shutdownTimeout(Duration.ofSeconds(5));

        if (sslEnabled) {
            clientConfigBuilder.useSsl();
        }

        LettuceClientConfiguration clientConfig = clientConfigBuilder.build();

        LettuceConnectionFactory factory = new LettuceConnectionFactory(config, clientConfig);

        factory.setValidateConnection(false);
        factory.afterPropertiesSet();

        log.info("Redis ConnectionFactory configurado - Host: {}, Port: {}, SSL: {}. " +
            "A conexão será validada automaticamente quando necessário.",
            redisHost, redisPort, sslEnabled);

        return factory;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {

        ObjectMapper redisObjectMapper = new ObjectMapper();
        redisObjectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        redisObjectMapper.registerModule(new JavaTimeModule());

        SimpleModule enumModule = new SimpleModule("EnumModule");

        enumModule.addSerializer(SexoEnum.class, new SexoEnumSerializer());
        enumModule.addDeserializer(SexoEnum.class, new SexoEnumDeserializer());

        enumModule.addSerializer(EstadoCivilEnum.class, new EstadoCivilEnumSerializer());
        enumModule.addDeserializer(EstadoCivilEnum.class, new EstadoCivilEnumDeserializer());

        enumModule.addSerializer(EscolaridadeEnum.class, new EscolaridadeEnumSerializer());
        enumModule.addDeserializer(EscolaridadeEnum.class, new EscolaridadeEnumDeserializer());

        enumModule.addSerializer(IdentidadeGeneroEnum.class, new IdentidadeGeneroEnumSerializer());
        enumModule.addDeserializer(IdentidadeGeneroEnum.class, new IdentidadeGeneroEnumDeserializer());

        enumModule.addSerializer(NacionalidadeEnum.class, new NacionalidadeEnumSerializer());
        enumModule.addDeserializer(NacionalidadeEnum.class, new NacionalidadeEnumDeserializer());

        enumModule.addSerializer(OrientacaoSexualEnum.class, new OrientacaoSexualEnumSerializer());
        enumModule.addDeserializer(OrientacaoSexualEnum.class, new OrientacaoSexualEnumDeserializer());

        enumModule.addSerializer(RacaCorEnum.class, new RacaCorEnumSerializer());
        enumModule.addDeserializer(RacaCorEnum.class, new RacaCorEnumDeserializer());

        enumModule.addSerializer(StatusPacienteEnum.class, new StatusPacienteEnumSerializer());
        enumModule.addDeserializer(StatusPacienteEnum.class, new StatusPacienteEnumDeserializer());

        enumModule.addSerializer(TipoAtendimentoPreferencialEnum.class, new TipoAtendimentoPreferencialEnumSerializer());
        enumModule.addDeserializer(TipoAtendimentoPreferencialEnum.class, new TipoAtendimentoPreferencialEnumDeserializer());

        enumModule.addSerializer(TipoCnsEnum.class, new TipoCnsEnumSerializer());
        enumModule.addDeserializer(TipoCnsEnum.class, new TipoCnsEnumDeserializer());

        enumModule.addSerializer(TipoLogradouroEnum.class, new TipoLogradouroEnumSerializer());
        enumModule.addDeserializer(TipoLogradouroEnum.class, new TipoLogradouroEnumDeserializer());

        redisObjectMapper.registerModule(enumModule);
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
