-- Script de Manutenção: Configurar VACUUM Agressivo para Tabelas Problemáticas
-- Data: 2026-01-18
-- Propósito: Resolver problema de queries travadas por dead tuples

-- IMPORTANTE: Execute este script via Supabase SQL Editor ou pgAdmin
-- NÃO é uma migração Flyway (não colocar em db/migration)

-- ========================================
-- 1. CONFIGURAR AUTOVACUUM AGRESSIVO
-- ========================================

-- sia_pa: Tabela grande (7.6GB, 17M registros)
-- VACUUM mais frequente para evitar acúmulo de dead tuples
ALTER TABLE public.sia_pa SET (
    autovacuum_vacuum_scale_factor = 0.01,   -- VACUUM a cada 1% de mudanças
    autovacuum_analyze_scale_factor = 0.005, -- ANALYZE a cada 0.5% de mudanças
    autovacuum_vacuum_cost_delay = 2,        -- Mais agressivo (default: 20)
    autovacuum_vacuum_cost_limit = 500       -- Maior throughput (default: 200)
);

-- estados: Tabela pequena (29 registros) com muitos updates
-- VACUUM após poucos updates para manter tabela limpa
ALTER TABLE public.estados SET (
    autovacuum_vacuum_scale_factor = 0.05,   -- VACUUM a cada 5% de mudanças
    autovacuum_vacuum_threshold = 10,        -- VACUUM após 10 updates
    autovacuum_analyze_threshold = 10        -- ANALYZE após 10 updates
);

-- competencia_financeira: Tabela pequena (2 registros) com 92% dead tuples
-- VACUUM imediato após qualquer mudança
ALTER TABLE public.competencia_financeira SET (
    autovacuum_vacuum_scale_factor = 0.1,    -- VACUUM a cada 10% de mudanças
    autovacuum_vacuum_threshold = 5,         -- VACUUM após 5 mudanças
    autovacuum_vacuum_cost_delay = 0         -- Sem delay (mais rápido)
);

-- ========================================
-- 2. EXECUTAR VACUUM IMEDIATO
-- ========================================

-- Executar VACUUM nas tabelas problemáticas AGORA
-- Isso vai remover dead tuples acumulados

-- sia_pa: VACUUM normal (não FULL para não bloquear)
VACUUM (ANALYZE, VERBOSE) public.sia_pa;

-- estados: VACUUM normal
VACUUM (ANALYZE, VERBOSE) public.estados;

-- competencia_financeira: VACUUM FULL para recuperar espaço
-- ATENÇÃO: VACUUM FULL bloqueia a tabela! Executar fora de horário de pico
VACUUM FULL ANALYZE public.competencia_financeira;

-- ========================================
-- 3. OUTRAS TABELAS COM DEAD TUPLES ALTOS
-- ========================================

-- Baseado na análise, estas tabelas também precisam de atenção:

-- medicos_especialidades: 71% dead tuples
VACUUM FULL ANALYZE public.medicos_especialidades;

-- plano_contas: 100% dead tuples (sem registros vivos!)
VACUUM FULL ANALYZE public.plano_contas;

-- paciente_dados_sociodemograficos: 75% dead tuples
VACUUM FULL ANALYZE public.paciente_dados_sociodemograficos;

-- estabelecimentos: 81% dead tuples
VACUUM FULL ANALYZE public.estabelecimentos;

-- ========================================
-- 4. VERIFICAR RESULTADO
-- ========================================

-- Após executar VACUUM, verificar melhoria:
SELECT 
    schemaname,
    relname,
    n_dead_tup,
    n_live_tup,
    ROUND(100.0 * n_dead_tup / NULLIF(n_live_tup + n_dead_tup, 0), 2) AS dead_ratio_pct,
    pg_size_pretty(pg_total_relation_size(schemaname||'.'||relname)) AS size,
    last_autovacuum,
    autovacuum_count
FROM pg_stat_user_tables
WHERE relname IN ('sia_pa', 'estados', 'competencia_financeira')
ORDER BY relname;

-- ========================================
-- 5. MONITORAMENTO CONTÍNUO
-- ========================================

-- Query para monitorar dead tuples continuamente
-- Salvar como favorito no Supabase SQL Editor
SELECT 
    schemaname,
    relname,
    n_dead_tup,
    n_live_tup,
    ROUND(100.0 * n_dead_tup / NULLIF(n_live_tup + n_dead_tup, 0), 2) AS dead_ratio_pct,
    pg_size_pretty(pg_total_relation_size(schemaname||'.'||relname)) AS size,
    last_autovacuum
FROM pg_stat_user_tables
WHERE n_dead_tup > 100 OR 
      (n_dead_tup > 0 AND n_dead_tup::float / NULLIF(n_live_tup, 0) > 0.1)
ORDER BY dead_ratio_pct DESC NULLS LAST, n_dead_tup DESC
LIMIT 20;

-- Query para monitorar queries longas (>10 segundos)
SELECT 
    pid,
    now() - pg_stat_activity.query_start AS duration,
    usename,
    application_name,
    state,
    wait_event_type,
    wait_event,
    LEFT(query, 200) as query_preview
FROM pg_stat_activity
WHERE state != 'idle'
  AND query NOT ILIKE '%pg_stat_activity%'
  AND pid != pg_backend_pid()
  AND (now() - pg_stat_activity.query_start) > interval '10 seconds'
ORDER BY duration DESC;

-- ========================================
-- 6. NOTAS IMPORTANTES
-- ========================================

-- 1. VACUUM FULL bloqueia a tabela durante execução
--    Executar apenas em horários de baixo tráfego (madrugada)
--
-- 2. Para tabelas grandes como sia_pa, prefira VACUUM normal
--    VACUUM FULL em tabelas de GB pode demorar horas
--
-- 3. Após VACUUM FULL, o espaço é devolvido ao sistema operacional
--    Após VACUUM normal, o espaço fica disponível internamente no Postgres
--
-- 4. Estas configurações são persistentes e sobrevivem a restarts
--
-- 5. Monitorar logs do Postgres para verificar se autovacuum está rodando
--    Procurar por "automatic vacuum of table" nos logs
