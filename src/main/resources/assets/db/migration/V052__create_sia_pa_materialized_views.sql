-- =====================================================
-- MIGRATION: Criar views materializadas para otimização de relatórios SIA-PA
-- =====================================================
-- Objetivo: Criar views materializadas para melhorar performance
--           de consultas de relatórios e KPIs do SIA-PA
-- Autor: UPSaúde
-- =====================================================

-- View materializada para agregações mensais por tenant (via CNES)
-- Esta view agrega dados mensais por competência, facilitando relatórios de produção
CREATE MATERIALIZED VIEW IF NOT EXISTS public.mv_sia_pa_producao_mensal AS
SELECT 
    s.uf,
    s.competencia,
    s.codigo_cnes,
    COUNT(*) AS total_registros,
    COUNT(DISTINCT s.procedimento_codigo) AS procedimentos_unicos,
    COUNT(DISTINCT s.cid_principal_codigo) AS cids_unicos,
    COALESCE(SUM(COALESCE(s.quantidade_produzida, 0)), 0) AS quantidade_produzida_total,
    COALESCE(SUM(COALESCE(s.quantidade_aprovada, 0)), 0) AS quantidade_aprovada_total,
    COALESCE(SUM(COALESCE(s.valor_produzido, 0)), 0) AS valor_produzido_total,
    COALESCE(SUM(COALESCE(s.valor_aprovado, 0)), 0) AS valor_aprovado_total,
    COALESCE(SUM(CASE WHEN s.flag_erro IS NOT NULL AND s.flag_erro <> '0' THEN 1 ELSE 0 END), 0) AS registros_com_erro
FROM public.sia_pa s
WHERE s.ativo = true
GROUP BY s.uf, s.competencia, s.codigo_cnes;

-- Índices para otimizar consultas na view materializada
CREATE INDEX IF NOT EXISTS idx_mv_sia_pa_producao_mensal_uf_competencia 
    ON public.mv_sia_pa_producao_mensal (uf, competencia);
CREATE INDEX IF NOT EXISTS idx_mv_sia_pa_producao_mensal_cnes 
    ON public.mv_sia_pa_producao_mensal (codigo_cnes);
CREATE INDEX IF NOT EXISTS idx_mv_sia_pa_producao_mensal_competencia 
    ON public.mv_sia_pa_producao_mensal (competencia);

-- View materializada para top procedimentos por competência
-- Esta view agrega dados de procedimentos para facilitar relatórios de top procedimentos
CREATE MATERIALIZED VIEW IF NOT EXISTS public.mv_sia_pa_top_procedimentos AS
SELECT 
    s.uf,
    s.competencia,
    s.procedimento_codigo,
    COUNT(*) AS total_registros,
    COUNT(DISTINCT s.codigo_cnes) AS estabelecimentos_unicos,
    COUNT(DISTINCT s.municipio_ufmun_codigo) AS municipios_unicos,
    COALESCE(SUM(COALESCE(s.quantidade_produzida, 0)), 0) AS quantidade_produzida_total,
    COALESCE(SUM(COALESCE(s.valor_aprovado, 0)), 0) AS valor_aprovado_total
FROM public.sia_pa s
WHERE s.procedimento_codigo IS NOT NULL
GROUP BY s.uf, s.competencia, s.procedimento_codigo;

-- Índice único para permitir REFRESH CONCURRENTLY
CREATE UNIQUE INDEX IF NOT EXISTS idx_mv_sia_pa_top_procedimentos_unique 
    ON public.mv_sia_pa_top_procedimentos (uf, competencia, procedimento_codigo);

-- Índices para otimizar consultas na view materializada de top procedimentos
CREATE INDEX IF NOT EXISTS idx_mv_sia_pa_top_procedimentos_uf_competencia 
    ON public.mv_sia_pa_top_procedimentos (uf, competencia);
CREATE INDEX IF NOT EXISTS idx_mv_sia_pa_top_procedimentos_procedimento 
    ON public.mv_sia_pa_top_procedimentos (procedimento_codigo);
CREATE INDEX IF NOT EXISTS idx_mv_sia_pa_top_procedimentos_quantidade 
    ON public.mv_sia_pa_top_procedimentos (quantidade_produzida_total DESC);

-- View materializada para top CID por competência
-- Esta view agrega dados de CID para facilitar relatórios de top CID
CREATE MATERIALIZED VIEW IF NOT EXISTS public.mv_sia_pa_top_cid AS
SELECT 
    s.uf,
    s.competencia,
    s.cid_principal_codigo,
    COUNT(*) AS total_registros,
    COUNT(DISTINCT s.codigo_cnes) AS estabelecimentos_unicos,
    COUNT(DISTINCT s.procedimento_codigo) AS procedimentos_unicos,
    COALESCE(SUM(COALESCE(s.quantidade_produzida, 0)), 0) AS quantidade_produzida_total,
    COALESCE(SUM(COALESCE(s.valor_aprovado, 0)), 0) AS valor_aprovado_total
FROM public.sia_pa s
WHERE s.cid_principal_codigo IS NOT NULL AND s.ativo = true
GROUP BY s.uf, s.competencia, s.cid_principal_codigo;

-- Índice único para permitir REFRESH CONCURRENTLY
CREATE UNIQUE INDEX IF NOT EXISTS idx_mv_sia_pa_top_cid_unique 
    ON public.mv_sia_pa_top_cid (uf, competencia, cid_principal_codigo);

-- Índices para otimizar consultas na view materializada de top CID
CREATE INDEX IF NOT EXISTS idx_mv_sia_pa_top_cid_uf_competencia 
    ON public.mv_sia_pa_top_cid (uf, competencia);
CREATE INDEX IF NOT EXISTS idx_mv_sia_pa_top_cid_cid 
    ON public.mv_sia_pa_top_cid (cid_principal_codigo);
CREATE INDEX IF NOT EXISTS idx_mv_sia_pa_top_cid_quantidade 
    ON public.mv_sia_pa_top_cid (quantidade_produzida_total DESC);

-- View materializada para KPIs gerais por competência e UF
-- Esta view agrega dados gerais para facilitar cálculos de KPIs
CREATE MATERIALIZED VIEW IF NOT EXISTS public.mv_sia_pa_kpi_geral AS
SELECT 
    s.uf,
    s.competencia,
    COUNT(*) AS total_registros,
    COUNT(DISTINCT s.procedimento_codigo) AS procedimentos_unicos,
    COUNT(DISTINCT s.codigo_cnes) AS estabelecimentos_unicos,
    COUNT(DISTINCT s.cid_principal_codigo) AS cids_unicos,
    COALESCE(SUM(COALESCE(s.quantidade_produzida, 0)), 0) AS quantidade_produzida_total,
    COALESCE(SUM(COALESCE(s.quantidade_aprovada, 0)), 0) AS quantidade_aprovada_total,
    COALESCE(SUM(COALESCE(s.valor_produzido, 0)), 0) AS valor_produzido_total,
    COALESCE(SUM(COALESCE(s.valor_aprovado, 0)), 0) AS valor_aprovado_total,
    COALESCE(SUM(CASE WHEN s.flag_erro IS NOT NULL AND s.flag_erro <> '0' THEN 1 ELSE 0 END), 0) AS registros_com_erro
FROM public.sia_pa s
WHERE s.ativo = true
GROUP BY s.uf, s.competencia;

-- Índice único para permitir REFRESH CONCURRENTLY
CREATE UNIQUE INDEX IF NOT EXISTS idx_mv_sia_pa_kpi_geral_unique 
    ON public.mv_sia_pa_kpi_geral (uf, competencia);

-- Índices para otimizar consultas na view materializada de KPIs gerais
CREATE INDEX IF NOT EXISTS idx_mv_sia_pa_kpi_geral_competencia 
    ON public.mv_sia_pa_kpi_geral (competencia);

-- Comentários para documentação
COMMENT ON MATERIALIZED VIEW public.mv_sia_pa_producao_mensal IS 
    'View materializada com agregações mensais de produção SIA-PA por CNES. Atualizar periodicamente com REFRESH MATERIALIZED VIEW.';
COMMENT ON MATERIALIZED VIEW public.mv_sia_pa_top_procedimentos IS 
    'View materializada com agregações de top procedimentos SIA-PA por competência. Atualizar periodicamente com REFRESH MATERIALIZED VIEW.';
COMMENT ON MATERIALIZED VIEW public.mv_sia_pa_top_cid IS 
    'View materializada com agregações de top CID SIA-PA por competência. Atualizar periodicamente com REFRESH MATERIALIZED VIEW.';
COMMENT ON MATERIALIZED VIEW public.mv_sia_pa_kpi_geral IS 
    'View materializada com KPIs gerais SIA-PA por competência e UF. Atualizar periodicamente com REFRESH MATERIALIZED VIEW.';
