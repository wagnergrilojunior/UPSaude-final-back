package com.upsaude.service.impl.api.sia;

import com.upsaude.service.api.sia.SiaPaAggregationRefreshService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "sia.aggregation.refresh", name = "enabled", havingValue = "true", matchIfMissing = true)
public class SiaPaAggregationRefreshServiceImpl implements SiaPaAggregationRefreshService {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void refreshAll() {
        refreshEstabelecimento();
        refreshProcedimento();
        refreshCid();
        refreshTemporal();
    }

    @Override
    public void refreshEstabelecimento() {
        refreshMatView("public.sia_pa_agregado_estabelecimento");
    }

    @Override
    public void refreshProcedimento() {
        refreshMatView("public.sia_pa_agregado_procedimento");
    }

    @Override
    public void refreshCid() {
        refreshMatView("public.sia_pa_agregado_cid");
    }

    @Override
    public void refreshTemporal() {
        refreshMatView("public.sia_pa_agregado_temporal");
    }

    
    @Scheduled(cron = "${sia.aggregation.refresh.cron:0 0 2 * * ?}")
    public void scheduledRefresh() {
        try {
            log.info("Iniciando refresh das views materializadas do SIA-PA...");
            refreshAll();
            log.info("Refresh das views materializadas do SIA-PA concluído com sucesso");
        } catch (Exception e) {
            
            log.warn("Falha ao executar refresh das views materializadas do SIA-PA: {}", e.getMessage(), e);
        }
    }

    private void refreshMatView(String qualifiedName) {
        try {
            jdbcTemplate.execute("REFRESH MATERIALIZED VIEW " + qualifiedName);
        } catch (Exception e) {
            log.warn("Falha ao refresh materialized view {}: {}", qualifiedName, e.getMessage());
            throw e;
        }
    }
}

