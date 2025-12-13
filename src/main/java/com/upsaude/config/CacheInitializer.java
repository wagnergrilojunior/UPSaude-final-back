package com.upsaude.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Configuration
@Profile("!local")
public class CacheInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired(required = false)
    private CacheManager cacheManager;

    @Autowired(required = false)
    private RedisConnectionFactory redisConnectionFactory;

    private static final List<String> KNOWN_CACHES = Arrays.asList(
        "cidades", "estados", "medicos", "pacientes", "especialidades",
        "profissionaisSaude", "vacinas", "medicacoes", "exames",
        "acoesPromocaoPrevencao", "agendamentos", "alergias",
        "catalogoExames", "equipesSaude",
        "atendimentos", "consultas", "prontuarios", "preNatal", "puericultura",
        "vacinacoes", "exames", "estoquesVacina", "medicacao", "medicacaoPaciente",
        "catalogoProcedimentos", "doencas",
        "convenio", "dadosClinicosBasicos", "dadosSociodemograficos",
        "ciddoencas", "cirurgias",
        "especialidadesmedicas", "estabelecimentos",
        "fabricantesequipamento", "fabricantesmedicamento", "fabricantesvacina",
        "deficiencias", "departamentos", "atividadesprofissionais",
        "faltas", "filaEspera",
        "perfisusuarios", "permissoes", "planejamentofamiliar",
        "plantoes", "procedimentosCirurgicos", "consultasPreNatal",
        "procedimentosOdontologicos", "consultasPuericultura",
        "receitasmedicas", "responsavellegal", "servicosEstabelecimento",
        "templatesNotificacao", "notificacoes", "configuracoesEstabelecimento",
        "tratamentosodontologicos", "visitasdomiciliares", "conselhosprofissionais",
        "controleponto", "cuidadosenfermagem", "dispensacoesmedicamentos", "educacaosaude",
        "equipamentos", "equipamentosestabelecimento", "escalatrabalho",
        "atendimento",
        "checkinatendimento", "historicoclinico", "historicohabilitacaoprofissional", "infraestruturaestabelecimento",
        "integracaogov", "lgpdconsentimento", "medicaoclinica", "movimentacoesestoque", "logsauditoria"
    );

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {

        if (event.getApplicationContext().getParent() == null) {
            initializeRedisMetrics();
            initializeCaches();
        }
    }

    private void initializeRedisMetrics() {
        if (redisConnectionFactory == null) {
            log.debug("RedisConnectionFactory não disponível - pulando inicialização de métricas Redis");
            return;
        }

        try {
            RedisConnection connection = redisConnectionFactory.getConnection();
            try {

                connection.ping();
                log.debug("Comando Redis executado com sucesso - métricas do Lettuce devem estar disponíveis");
            } catch (Exception e) {
                log.warn("Erro ao executar comando Redis durante inicialização: {}. " +
                    "Métricas do Lettuce estarão disponíveis após o primeiro uso do Redis.", e.getMessage());
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (Exception e) {
            log.warn("Erro ao obter conexão Redis durante inicialização: {}. " +
                "Métricas do Redis estarão disponíveis após o primeiro uso.", e.getMessage());
        }
    }

    private void initializeCaches() {
        if (cacheManager == null) {
            log.debug("CacheManager não disponível - pulando inicialização de cache");
            return;
        }

        try {
            var cacheNames = cacheManager.getCacheNames();

            if (cacheNames == null || cacheNames.isEmpty()) {
                log.debug("Nenhum cache criado ainda - tentando inicializar caches conhecidos");
                initializeKnownCaches();
                return;
            }

            int initializedCount = 0;
            for (String cacheName : cacheNames) {
                try {
                    Cache cache = cacheManager.getCache(Objects.requireNonNull(cacheName, "cacheName"));
                    if (cache != null) {
                        initializeCache(cache, cacheName);
                        initializedCount++;
                    }
                } catch (Exception e) {
                    log.warn("Erro ao inicializar cache '{}': {}. Métricas podem não estar disponíveis até o primeiro uso.",
                        cacheName, e.getMessage());
                }
            }

            if (initializedCount > 0) {
                log.info("{} cache(s) inicializado(s) para métricas do Actuator", initializedCount);
            }
        } catch (Exception e) {
            log.warn("Erro ao inicializar caches para métricas: {}. " +
                "Métricas de cache estarão disponíveis após o primeiro uso do cache.", e.getMessage());
        }
    }

    private void initializeKnownCaches() {
        int initializedCount = 0;
        for (String cacheName : KNOWN_CACHES) {
            try {
                Cache cache = cacheManager.getCache(Objects.requireNonNull(cacheName, "cacheName"));
                if (cache != null) {
                    initializeCache(cache, cacheName);
                    initializedCount++;
                }
            } catch (Exception e) {

                log.trace("Cache '{}' não disponível ainda: {}", cacheName, e.getMessage());
            }
        }

        if (initializedCount > 0) {
            log.info("{} cache(s) conhecido(s) inicializado(s) para métricas do Actuator", initializedCount);
        }
    }

    private void initializeCache(Cache cache, String cacheName) {
        try {

            String initKey = "__cache_init__" + System.currentTimeMillis();
            cache.put(initKey, "initialized");
            cache.evict(initKey);
            log.debug("Cache '{}' inicializado para métricas", cacheName);
        } catch (Exception e) {
            log.warn("Erro ao inicializar cache '{}': {}", cacheName, e.getMessage());
            throw e;
        }
    }
}
