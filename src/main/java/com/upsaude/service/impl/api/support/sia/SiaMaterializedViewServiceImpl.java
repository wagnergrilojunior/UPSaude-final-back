package com.upsaude.service.impl.api.support.sia;

import com.upsaude.service.api.support.sia.SiaMaterializedViewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("siaSupportMaterializedViewServiceImpl")
@RequiredArgsConstructor
public class SiaMaterializedViewServiceImpl implements SiaMaterializedViewService {

    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public void refreshAllViews() {
        log.info("Atualizando todas as views materializadas do SIA-PA");
        refreshProducaoMensalView();
        refreshTopProcedimentosView();
        refreshTopCidView();
        refreshKpiGeralView();
        log.info("Todas as views materializadas do SIA-PA foram atualizadas com sucesso");
    }

    @Override
    @Transactional
    public void refreshProducaoMensalView() {
        try {
            log.debug("Atualizando view materializada mv_sia_pa_producao_mensal");
            jdbcTemplate.execute("REFRESH MATERIALIZED VIEW CONCURRENTLY public.mv_sia_pa_producao_mensal");
            log.debug("View materializada mv_sia_pa_producao_mensal atualizada com sucesso");
        } catch (Exception e) {
            log.error("Erro ao atualizar view materializada mv_sia_pa_producao_mensal: {}", e.getMessage(), e);
            // Tenta sem CONCURRENTLY se falhar (requer índice único)
            try {
                jdbcTemplate.execute("REFRESH MATERIALIZED VIEW public.mv_sia_pa_producao_mensal");
                log.debug("View materializada mv_sia_pa_producao_mensal atualizada sem CONCURRENTLY");
            } catch (Exception e2) {
                log.error("Erro ao atualizar view materializada mv_sia_pa_producao_mensal sem CONCURRENTLY: {}", e2.getMessage(), e2);
                throw new RuntimeException("Erro ao atualizar view materializada mv_sia_pa_producao_mensal", e2);
            }
        }
    }

    @Override
    @Transactional
    public void refreshTopProcedimentosView() {
        try {
            log.debug("Atualizando view materializada mv_sia_pa_top_procedimentos");
            jdbcTemplate.execute("REFRESH MATERIALIZED VIEW CONCURRENTLY public.mv_sia_pa_top_procedimentos");
            log.debug("View materializada mv_sia_pa_top_procedimentos atualizada com sucesso");
        } catch (Exception e) {
            log.error("Erro ao atualizar view materializada mv_sia_pa_top_procedimentos: {}", e.getMessage(), e);
            try {
                jdbcTemplate.execute("REFRESH MATERIALIZED VIEW public.mv_sia_pa_top_procedimentos");
                log.debug("View materializada mv_sia_pa_top_procedimentos atualizada sem CONCURRENTLY");
            } catch (Exception e2) {
                log.error("Erro ao atualizar view materializada mv_sia_pa_top_procedimentos sem CONCURRENTLY: {}", e2.getMessage(), e2);
                throw new RuntimeException("Erro ao atualizar view materializada mv_sia_pa_top_procedimentos", e2);
            }
        }
    }

    @Override
    @Transactional
    public void refreshTopCidView() {
        try {
            log.debug("Atualizando view materializada mv_sia_pa_top_cid");
            jdbcTemplate.execute("REFRESH MATERIALIZED VIEW CONCURRENTLY public.mv_sia_pa_top_cid");
            log.debug("View materializada mv_sia_pa_top_cid atualizada com sucesso");
        } catch (Exception e) {
            log.error("Erro ao atualizar view materializada mv_sia_pa_top_cid: {}", e.getMessage(), e);
            try {
                jdbcTemplate.execute("REFRESH MATERIALIZED VIEW public.mv_sia_pa_top_cid");
                log.debug("View materializada mv_sia_pa_top_cid atualizada sem CONCURRENTLY");
            } catch (Exception e2) {
                log.error("Erro ao atualizar view materializada mv_sia_pa_top_cid sem CONCURRENTLY: {}", e2.getMessage(), e2);
                throw new RuntimeException("Erro ao atualizar view materializada mv_sia_pa_top_cid", e2);
            }
        }
    }

    @Override
    @Transactional
    public void refreshKpiGeralView() {
        try {
            log.debug("Atualizando view materializada mv_sia_pa_kpi_geral");
            jdbcTemplate.execute("REFRESH MATERIALIZED VIEW CONCURRENTLY public.mv_sia_pa_kpi_geral");
            log.debug("View materializada mv_sia_pa_kpi_geral atualizada com sucesso");
        } catch (Exception e) {
            log.error("Erro ao atualizar view materializada mv_sia_pa_kpi_geral: {}", e.getMessage(), e);
            try {
                jdbcTemplate.execute("REFRESH MATERIALIZED VIEW public.mv_sia_pa_kpi_geral");
                log.debug("View materializada mv_sia_pa_kpi_geral atualizada sem CONCURRENTLY");
            } catch (Exception e2) {
                log.error("Erro ao atualizar view materializada mv_sia_pa_kpi_geral sem CONCURRENTLY: {}", e2.getMessage(), e2);
                throw new RuntimeException("Erro ao atualizar view materializada mv_sia_pa_kpi_geral", e2);
            }
        }
    }
}
