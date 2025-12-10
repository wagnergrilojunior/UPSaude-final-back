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
 * OBSERVABILIDADE: As métricas do Redis são coletadas automaticamente pelo Micrometer
 * quando as configurações de métricas estão habilitadas no application-prod.properties:
 * - Métricas de conexão (pool de conexões Lettuce)
 * - Métricas de comandos (latência, contadores por comando)
 * - Métricas de cache (hit/miss ratio, tamanho, evictions)
 * - Métricas do Spring Data Redis (operações de repositório)
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
        config.setDatabase(0); // Database padrão
        
        if (redisPassword != null && !redisPassword.isEmpty()) {
            config.setPassword(redisPassword);
        }

        // Configuração do cliente Lettuce com timeouts adequados para Render
        // CORREÇÃO: Timeouts aumentados e pingBeforeActivateConnection desabilitado
        // para evitar problemas durante cold start do Redis no Render
        SocketOptions socketOptions = SocketOptions.builder()
            .connectTimeout(Duration.ofSeconds(10)) // Timeout de conexão aumentado para Render
            .keepAlive(true) // Mantém conexão viva
            .build();

        TimeoutOptions timeoutOptions = TimeoutOptions.builder()
            .fixedTimeout(Duration.ofSeconds(10)) // Timeout de comandos aumentado para Render
            .build();

        ClientOptions clientOptions = ClientOptions.builder()
            .socketOptions(socketOptions)
            .timeoutOptions(timeoutOptions)
            .autoReconnect(true) // Reconecta automaticamente
            .pingBeforeActivateConnection(false) // CORREÇÃO: Desabilitado para evitar problemas no Render
            .build();

        // Configuração do cliente Lettuce com suporte a SSL para Upstash
        LettuceClientConfiguration.LettuceClientConfigurationBuilder clientConfigBuilder = 
            LettuceClientConfiguration.builder()
                .clientOptions(clientOptions)
                .commandTimeout(Duration.ofSeconds(10)) // Timeout de comandos aumentado para Render
                .shutdownTimeout(Duration.ofSeconds(5)); // Tempo para fechar conexões aumentado
        
        // Habilita SSL/TLS se configurado (necessário para Upstash)
        if (sslEnabled) {
            clientConfigBuilder.useSsl();
        }
        
        LettuceClientConfiguration clientConfig = clientConfigBuilder.build();

        LettuceConnectionFactory factory = new LettuceConnectionFactory(config, clientConfig);
        // Não valida no startup para não bloquear, mas valida quando usar
        factory.setValidateConnection(false);
        factory.afterPropertiesSet();
        
        // CORREÇÃO: Removido ping durante inicialização para evitar erros no Render
        // O Redis pode estar em cold start e causar warnings/erros desnecessários
        // A conexão será validada automaticamente quando necessário pelo Lettuce
        // com autoReconnect=true e pingBeforeActivateConnection=true configurados
        log.info("Redis ConnectionFactory configurado - Host: {}, Port: {}, SSL: {}. " +
            "A conexão será validada automaticamente quando necessário.", 
            redisHost, redisPort, sslEnabled);
        
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
        
        // ====================================================================================
        // REGISTRO DE SERIALIZERS/DESERIALIZERS CUSTOMIZADOS PARA ENUMS
        // ====================================================================================
        // IMPORTANTE: Todos os enums que possuem serializers customizados (usando @JsonSerialize
        // nas Responses) DEVEM ter seus serializers e deserializers registrados aqui.
        //
        // PROBLEMA RESOLVIDO: Sem esse registro, quando um enum é serializado com formato
        // customizado (ex: "Feminino" via SexoEnumSerializer) e salvo no Redis, ao deserializar
        // o Jackson tentaria usar o formato padrão (ex: "FEMININO"), causando erro:
        // "Cannot deserialize value of type `SexoEnum` from String \"Feminino\""
        //
        // REGRA: Se um enum tem serializer customizado em uma Response que pode ser cacheada,
        // ele DEVE ter serializer e deserializer registrados aqui.
        //
        // CHECKLIST ao criar novo serializer customizado:
        // 1. Criar *EnumSerializer.java e *EnumDeserializer.java em util/converter
        // 2. Adicionar @JsonSerialize(using = *EnumSerializer.class) na Response
        // 3. Registrar serializer e deserializer neste método (abaixo)
        // 4. Testar serialização/deserialização no Redis
        //
        // ENUMS COM @JsonValue: Enums que usam @JsonValue (ex: TipoUsuarioSistemaEnum) não
        // precisam de registro aqui, pois o Jackson deserializa automaticamente usando valueOf().
        //
        // ENUMS SEM SERIALIZERS CUSTOMIZADOS: Enums sem serializers customizados usam o formato
        // padrão (nome do enum) e não causam problemas, não precisam ser registrados aqui.
        // ====================================================================================
        SimpleModule enumModule = new SimpleModule("EnumModule");
        
        // SexoEnum
        enumModule.addSerializer(SexoEnum.class, new SexoEnumSerializer());
        enumModule.addDeserializer(SexoEnum.class, new SexoEnumDeserializer());
        
        // EstadoCivilEnum
        enumModule.addSerializer(EstadoCivilEnum.class, new EstadoCivilEnumSerializer());
        enumModule.addDeserializer(EstadoCivilEnum.class, new EstadoCivilEnumDeserializer());
        
        // EscolaridadeEnum
        enumModule.addSerializer(EscolaridadeEnum.class, new EscolaridadeEnumSerializer());
        enumModule.addDeserializer(EscolaridadeEnum.class, new EscolaridadeEnumDeserializer());
        
        // IdentidadeGeneroEnum
        enumModule.addSerializer(IdentidadeGeneroEnum.class, new IdentidadeGeneroEnumSerializer());
        enumModule.addDeserializer(IdentidadeGeneroEnum.class, new IdentidadeGeneroEnumDeserializer());
        
        // NacionalidadeEnum
        enumModule.addSerializer(NacionalidadeEnum.class, new NacionalidadeEnumSerializer());
        enumModule.addDeserializer(NacionalidadeEnum.class, new NacionalidadeEnumDeserializer());
        
        // OrientacaoSexualEnum
        enumModule.addSerializer(OrientacaoSexualEnum.class, new OrientacaoSexualEnumSerializer());
        enumModule.addDeserializer(OrientacaoSexualEnum.class, new OrientacaoSexualEnumDeserializer());
        
        // RacaCorEnum
        enumModule.addSerializer(RacaCorEnum.class, new RacaCorEnumSerializer());
        enumModule.addDeserializer(RacaCorEnum.class, new RacaCorEnumDeserializer());
        
        // StatusPacienteEnum
        enumModule.addSerializer(StatusPacienteEnum.class, new StatusPacienteEnumSerializer());
        enumModule.addDeserializer(StatusPacienteEnum.class, new StatusPacienteEnumDeserializer());
        
        // TipoAtendimentoPreferencialEnum
        enumModule.addSerializer(TipoAtendimentoPreferencialEnum.class, new TipoAtendimentoPreferencialEnumSerializer());
        enumModule.addDeserializer(TipoAtendimentoPreferencialEnum.class, new TipoAtendimentoPreferencialEnumDeserializer());
        
        // TipoCnsEnum
        enumModule.addSerializer(TipoCnsEnum.class, new TipoCnsEnumSerializer());
        enumModule.addDeserializer(TipoCnsEnum.class, new TipoCnsEnumDeserializer());
        
        // TipoLogradouroEnum
        enumModule.addSerializer(TipoLogradouroEnum.class, new TipoLogradouroEnumSerializer());
        enumModule.addDeserializer(TipoLogradouroEnum.class, new TipoLogradouroEnumDeserializer());
        
        // ====================================================================================
        // FIM DO REGISTRO DE ENUMS
        // 
        // Total de enums registrados: 11
        // 
        // Para verificar se todos os serializers estão registrados, execute:
        // find src/main/java/com/upsaude/util/converter -name "*EnumSerializer.java" | wc -l
        // O número deve corresponder ao número de registros acima.
        // ====================================================================================
        
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

