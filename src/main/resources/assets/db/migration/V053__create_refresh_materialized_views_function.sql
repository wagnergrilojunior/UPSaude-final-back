-- =====================================================
-- MIGRATION: Criar função para refresh das views materializadas SIA
-- =====================================================
-- Objetivo: Criar função para atualizar as views materializadas
--           de forma eficiente e permitir refresh incremental
-- Autor: UPSaúde
-- =====================================================

-- Função para refresh completo de todas as views materializadas SIA
CREATE OR REPLACE FUNCTION refresh_sia_materialized_views()
RETURNS void
LANGUAGE plpgsql
AS $$
BEGIN
    REFRESH MATERIALIZED VIEW CONCURRENTLY mv_sia_pa_kpi_geral;
    REFRESH MATERIALIZED VIEW CONCURRENTLY mv_sia_pa_producao_mensal;
    REFRESH MATERIALIZED VIEW CONCURRENTLY mv_sia_pa_top_procedimentos;
    REFRESH MATERIALIZED VIEW CONCURRENTLY mv_sia_pa_top_cid;
END;
$$;

-- Função para refresh de uma view materializada específica
CREATE OR REPLACE FUNCTION refresh_sia_materialized_view(view_name TEXT)
RETURNS void
LANGUAGE plpgsql
AS $$
BEGIN
    CASE view_name
        WHEN 'mv_sia_pa_kpi_geral' THEN
            REFRESH MATERIALIZED VIEW CONCURRENTLY mv_sia_pa_kpi_geral;
        WHEN 'mv_sia_pa_producao_mensal' THEN
            REFRESH MATERIALIZED VIEW CONCURRENTLY mv_sia_pa_producao_mensal;
        WHEN 'mv_sia_pa_top_procedimentos' THEN
            REFRESH MATERIALIZED VIEW CONCURRENTLY mv_sia_pa_top_procedimentos;
        WHEN 'mv_sia_pa_top_cid' THEN
            REFRESH MATERIALIZED VIEW CONCURRENTLY mv_sia_pa_top_cid;
        ELSE
            RAISE EXCEPTION 'View materializada não encontrada: %', view_name;
    END CASE;
END;
$$;

-- Comentários nas funções
COMMENT ON FUNCTION refresh_sia_materialized_views() IS 'Atualiza todas as views materializadas do SIA-PA de forma concorrente';
COMMENT ON FUNCTION refresh_sia_materialized_view(TEXT) IS 'Atualiza uma view materializada específica do SIA-PA de forma concorrente';
