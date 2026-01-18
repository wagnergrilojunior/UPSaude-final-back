package com.upsaude.service.impl.job;

import com.upsaude.service.api.sia.SiaMaterializedViewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class RelatoriosScheduler {

    private final SiaMaterializedViewService siaMaterializedViewService;
    private final CacheManager cacheManager;

    @Value("${relatorios.scheduler.enabled:true}")
    private boolean schedulerEnabled;

    @Value("${relatorios.scheduler.refresh-views-enabled:true}")
    private boolean refreshViewsEnabled;

    @Value("${relatorios.scheduler.clear-cache-enabled:true}")
    private boolean clearCacheEnabled;

    
    @Scheduled(cron = "${relatorios.scheduler.refresh-views-cron:0 0 1 * * ?}")
    public void refreshMaterializedViews() {
        if (!schedulerEnabled || !refreshViewsEnabled) {
            log.debug("Scheduler de refresh de views materializadas está desabilitado");
            return;
        }

        log.info("Iniciando refresh automático de views materializadas do SIA");
        try {
            siaMaterializedViewService.refreshAllViews();
            log.info("Refresh automático de views materializadas concluído com sucesso");
        } catch (Exception e) {
            log.error("Erro ao executar refresh automático de views materializadas: {}", e.getMessage(), e);
        }
    }

    
    @Scheduled(cron = "${relatorios.scheduler.clear-cache-cron:0 0 1 * * ?}")
    public void clearRelatoriosCache() {
        if (!schedulerEnabled || !clearCacheEnabled) {
            log.debug("Scheduler de limpeza de cache está desabilitado");
            return;
        }

        log.info("Iniciando limpeza de cache de relatórios e KPIs");
        try {
            String[] cacheNames = {
                    "siaKpiGeral",
                    "siaKpiEstabelecimento",
                    "siaKpiProcedimento",
                    "siaKpiTenant",
                    "siaKpiPorEstabelecimentoId",
                    "siaKpiPorMedicoId",
                    "siaRelatorioProducaoMensal",
                    "siaRelatorioTopProcedimentos",
                    "siaRelatorioTopCid",
                    "dashboardTenant",
                    "dashboardEstabelecimento",
                    "dashboardMedico",
                    "relatorioEstatisticas",
                    "relatorioComparativo"
            };

            int clearedCount = 0;
            for (String cacheName : cacheNames) {
                try {
                    var cache = cacheManager.getCache(cacheName);
                    if (cache != null) {
                        cache.clear();
                        clearedCount++;
                        log.debug("Cache '{}' limpo com sucesso", cacheName);
                    }
                } catch (Exception e) {
                    log.warn("Erro ao limpar cache '{}': {}", cacheName, e.getMessage());
                }
            }

            log.info("Limpeza de cache concluída. {} cache(s) limpo(s)", clearedCount);
        } catch (Exception e) {
            log.error("Erro ao executar limpeza de cache: {}", e.getMessage(), e);
        }
    }

    
    @Scheduled(cron = "${relatorios.scheduler.clear-reference-cache-cron:0 0 1 * * ?}")
    public void clearReferenceDataCache() {
        if (!schedulerEnabled || !clearCacheEnabled) {
            log.debug("Scheduler de limpeza de cache de referência está desabilitado");
            return;
        }

        log.info("Iniciando limpeza de cache de dados de referência");
        try {
            String[] cacheNames = {
                    "sigtapProcedimento",
                    "cid10Subcategoria",
                    "estabelecimentoPorCnes",
                    "medicoPorCns"
            };

            int clearedCount = 0;
            for (String cacheName : cacheNames) {
                try {
                    var cache = cacheManager.getCache(cacheName);
                    if (cache != null) {
                        cache.clear();
                        clearedCount++;
                        log.debug("Cache de referência '{}' limpo com sucesso", cacheName);
                    }
                } catch (Exception e) {
                    log.warn("Erro ao limpar cache de referência '{}': {}", cacheName, e.getMessage());
                }
            }

            log.info("Limpeza de cache de referência concluída. {} cache(s) limpo(s)", clearedCount);
        } catch (Exception e) {
            log.error("Erro ao executar limpeza de cache de referência: {}", e.getMessage(), e);
        }
    }
}
