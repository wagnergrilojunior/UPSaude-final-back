package com.upsaude.service.impl.api.sia;

import com.upsaude.service.api.sia.SiaMaterializedViewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("siaMaterializedViewServiceImpl")
@RequiredArgsConstructor
public class SiaMaterializedViewServiceImpl implements SiaMaterializedViewService {

    private final JdbcTemplate jdbcTemplate;

    private static final List<String> VALID_VIEW_NAMES = List.of(
            "mv_sia_pa_kpi_geral",
            "mv_sia_pa_producao_mensal",
            "mv_sia_pa_top_procedimentos",
            "mv_sia_pa_top_cid"
    );

    @Override
    public void refreshAllViews() {
        log.info("Iniciando refresh de todas as views materializadas do SIA");
        try {
            jdbcTemplate.execute("SELECT refresh_sia_materialized_views()");
            log.info("Refresh de todas as views materializadas do SIA concluído com sucesso");
        } catch (Exception e) {
            log.error("Erro ao atualizar views materializadas do SIA: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao atualizar views materializadas do SIA", e);
        }
    }

    @Override
    public void refreshView(String viewName) {
        if (viewName == null || viewName.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da view não pode ser vazio");
        }

        if (!VALID_VIEW_NAMES.contains(viewName)) {
            throw new IllegalArgumentException("View materializada inválida: " + viewName);
        }

        log.info("Iniciando refresh da view materializada: {}", viewName);
        try {
            jdbcTemplate.update("SELECT refresh_sia_materialized_view(?)", (ps) -> ps.setString(1, viewName));
            log.info("Refresh da view materializada {} concluído com sucesso", viewName);
        } catch (Exception e) {
            log.error("Erro ao atualizar view materializada {}: {}", viewName, e.getMessage(), e);
            throw new RuntimeException("Erro ao atualizar view materializada: " + viewName, e);
        }
    }

    @Override
    public boolean isViewUpToDate(String viewName) {
        if (viewName == null || viewName.trim().isEmpty()) {
            return false;
        }

        try {
            
            Integer count = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM pg_matviews WHERE matviewname = ?",
                    Integer.class,
                    viewName
            );
            return count != null && count > 0;
        } catch (Exception e) {
            log.warn("Erro ao verificar se view materializada está atualizada: {}", e.getMessage());
            return false;
        }
    }
}
