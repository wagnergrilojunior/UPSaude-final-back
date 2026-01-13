-- =====================================================
-- SCRIPT SQL: Verificar estrutura das tabelas
-- =====================================================
-- Objetivo: Verificar se as tabelas estão alinhadas com as entidades Java
-- Autor: UPSaúde
-- Data: 2026-01-13
-- =====================================================

-- =====================================================
-- 1. VERIFICAR TABELA cidades
-- =====================================================
SELECT 
    'cidades' as tabela,
    column_name,
    data_type,
    is_nullable,
    character_maximum_length,
    CASE 
        WHEN column_name IN ('codigo_fhir', 'fhir_code_system', 'regiao_saude', 'macrorregiao_saude', 'data_ultima_sincronizacao_fhir') 
        THEN 'FHIR'
        WHEN column_name IN ('codigo_ibge', 'nome_oficial_ibge', 'uf_ibge', 'populacao_estimada', 'ativo_ibge', 'data_ultima_sincronizacao_ibge')
        THEN 'IBGE'
        ELSE 'BASE'
    END as categoria
FROM information_schema.columns 
WHERE table_schema = 'public' 
  AND table_name = 'cidades'
ORDER BY 
    CASE categoria
        WHEN 'BASE' THEN 1
        WHEN 'IBGE' THEN 2
        WHEN 'FHIR' THEN 3
    END,
    ordinal_position;

-- =====================================================
-- 2. VERIFICAR TABELA estados
-- =====================================================
SELECT 
    'estados' as tabela,
    column_name,
    data_type,
    is_nullable,
    character_maximum_length,
    CASE 
        WHEN column_name IN ('codigo_fhir', 'fhir_code_system', 'data_ultima_sincronizacao_fhir') 
        THEN 'FHIR'
        WHEN column_name LIKE '%ibge%'
        THEN 'IBGE'
        ELSE 'BASE'
    END as categoria
FROM information_schema.columns 
WHERE table_schema = 'public' 
  AND table_name = 'estados'
ORDER BY 
    CASE categoria
        WHEN 'BASE' THEN 1
        WHEN 'IBGE' THEN 2
        WHEN 'FHIR' THEN 3
    END,
    ordinal_position;

-- =====================================================
-- 3. VERIFICAR TABELA integracao_eventos
-- =====================================================
SELECT 
    'integracao_eventos' as tabela,
    column_name,
    data_type,
    is_nullable,
    character_maximum_length
FROM information_schema.columns 
WHERE table_schema = 'public' 
  AND table_name = 'integracao_eventos'
ORDER BY ordinal_position;

-- =====================================================
-- 4. VERIFICAR ÍNDICES DA TABELA integracao_eventos
-- =====================================================
SELECT 
    indexname,
    indexdef
FROM pg_indexes
WHERE schemaname = 'public' 
  AND tablename = 'integracao_eventos'
ORDER BY indexname;

-- =====================================================
-- 5. RESUMO: Colunas FHIR faltantes
-- =====================================================
SELECT 
    'cidades' as tabela,
    CASE 
        WHEN NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'public' AND table_name = 'cidades' AND column_name = 'codigo_fhir')
        THEN 'codigo_fhir - FALTANDO'
        ELSE 'codigo_fhir - OK'
    END as codigo_fhir,
    CASE 
        WHEN NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'public' AND table_name = 'cidades' AND column_name = 'fhir_code_system')
        THEN 'fhir_code_system - FALTANDO'
        ELSE 'fhir_code_system - OK'
    END as fhir_code_system,
    CASE 
        WHEN NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'public' AND table_name = 'cidades' AND column_name = 'regiao_saude')
        THEN 'regiao_saude - FALTANDO'
        ELSE 'regiao_saude - OK'
    END as regiao_saude,
    CASE 
        WHEN NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'public' AND table_name = 'cidades' AND column_name = 'macrorregiao_saude')
        THEN 'macrorregiao_saude - FALTANDO'
        ELSE 'macrorregiao_saude - OK'
    END as macrorregiao_saude,
    CASE 
        WHEN NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'public' AND table_name = 'cidades' AND column_name = 'data_ultima_sincronizacao_fhir')
        THEN 'data_ultima_sincronizacao_fhir - FALTANDO'
        ELSE 'data_ultima_sincronizacao_fhir - OK'
    END as data_ultima_sincronizacao_fhir;
