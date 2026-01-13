-- =====================================================
-- SCRIPT SQL: Adicionar colunas FHIR na tabela cidades
-- =====================================================
-- Objetivo: Adicionar colunas FHIR para integração com BRDivisaoGeografica
-- Autor: UPSaúde
-- Data: 2026-01-13
-- =====================================================
-- 
-- IMPORTANTE: Este script APENAS ADICIONA COLUNAS à tabela existente.
-- NÃO altera o nome da tabela, NÃO cria nova tabela, NÃO remove dados.
-- 
-- INSTRUÇÕES:
-- Execute este script diretamente no Supabase SQL Editor
-- ou via psql conectado ao banco de dados.
-- 
-- Este script adiciona as seguintes colunas na tabela EXISTENTE "cidades":
-- - codigo_fhir VARCHAR(20)
-- - fhir_code_system VARCHAR(200)
-- - regiao_saude VARCHAR(100)
-- - macrorregiao_saude VARCHAR(100)
-- - data_ultima_sincronizacao_fhir TIMESTAMPTZ
-- 
-- Todas as colunas são nullable para garantir retrocompatibilidade.
-- A tabela "cidades" permanece com o mesmo nome e estrutura existente.
-- =====================================================

BEGIN;

-- =====================================================
-- GARANTIA: Verificar que a tabela existe antes de modificar
-- =====================================================
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.tables 
        WHERE table_schema = 'public' 
        AND table_name = 'cidades'
    ) THEN
        RAISE EXCEPTION 'Tabela cidades não existe! Não é possível adicionar colunas.';
    END IF;
END $$;

-- =====================================================
-- ADICIONAR COLUNAS: Apenas adiciona novas colunas à tabela existente
-- =====================================================
-- IMPORTANTE: Usamos ALTER TABLE ... ADD COLUMN
-- Isso APENAS adiciona colunas, NÃO altera o nome da tabela
-- =====================================================
DO $$
BEGIN
    -- Adicionar codigo_fhir se não existir
    IF NOT EXISTS (
        SELECT 1 
        FROM information_schema.columns 
        WHERE table_schema = 'public' 
          AND table_name = 'cidades' 
          AND column_name = 'codigo_fhir'
    ) THEN
        ALTER TABLE public.cidades ADD COLUMN codigo_fhir VARCHAR(20);
        RAISE NOTICE 'Coluna codigo_fhir adicionada com sucesso';
    ELSE
        RAISE NOTICE 'Coluna codigo_fhir já existe';
    END IF;
    
    -- Adicionar fhir_code_system se não existir
    IF NOT EXISTS (
        SELECT 1 
        FROM information_schema.columns 
        WHERE table_schema = 'public' 
          AND table_name = 'cidades' 
          AND column_name = 'fhir_code_system'
    ) THEN
        ALTER TABLE public.cidades ADD COLUMN fhir_code_system VARCHAR(200);
        RAISE NOTICE 'Coluna fhir_code_system adicionada com sucesso';
    ELSE
        RAISE NOTICE 'Coluna fhir_code_system já existe';
    END IF;
    
    -- Adicionar regiao_saude se não existir
    IF NOT EXISTS (
        SELECT 1 
        FROM information_schema.columns 
        WHERE table_schema = 'public' 
          AND table_name = 'cidades' 
          AND column_name = 'regiao_saude'
    ) THEN
        ALTER TABLE public.cidades ADD COLUMN regiao_saude VARCHAR(100);
        RAISE NOTICE 'Coluna regiao_saude adicionada com sucesso';
    ELSE
        RAISE NOTICE 'Coluna regiao_saude já existe';
    END IF;
    
    -- Adicionar macrorregiao_saude se não existir
    IF NOT EXISTS (
        SELECT 1 
        FROM information_schema.columns 
        WHERE table_schema = 'public' 
          AND table_name = 'cidades' 
          AND column_name = 'macrorregiao_saude'
    ) THEN
        ALTER TABLE public.cidades ADD COLUMN macrorregiao_saude VARCHAR(100);
        RAISE NOTICE 'Coluna macrorregiao_saude adicionada com sucesso';
    ELSE
        RAISE NOTICE 'Coluna macrorregiao_saude já existe';
    END IF;
    
    -- Adicionar data_ultima_sincronizacao_fhir se não existir
    IF NOT EXISTS (
        SELECT 1 
        FROM information_schema.columns 
        WHERE table_schema = 'public' 
          AND table_name = 'cidades' 
          AND column_name = 'data_ultima_sincronizacao_fhir'
    ) THEN
        ALTER TABLE public.cidades ADD COLUMN data_ultima_sincronizacao_fhir TIMESTAMPTZ;
        RAISE NOTICE 'Coluna data_ultima_sincronizacao_fhir adicionada com sucesso';
    ELSE
        RAISE NOTICE 'Coluna data_ultima_sincronizacao_fhir já existe';
    END IF;
END $$;

COMMIT;

-- =====================================================
-- VERIFICAÇÃO: Confirmar que as colunas foram criadas
-- =====================================================
SELECT 
    column_name,
    data_type,
    is_nullable,
    character_maximum_length,
    CASE 
        WHEN column_name IN ('codigo_fhir', 'fhir_code_system', 'regiao_saude', 'macrorregiao_saude', 'data_ultima_sincronizacao_fhir') 
        THEN '✓ FHIR'
        ELSE 'IBGE/BASE'
    END as categoria
FROM information_schema.columns 
WHERE table_schema = 'public' 
  AND table_name = 'cidades'
ORDER BY 
    CASE 
        WHEN column_name IN ('codigo_fhir', 'fhir_code_system', 'regiao_saude', 'macrorregiao_saude', 'data_ultima_sincronizacao_fhir') 
        THEN 0 
        ELSE 1 
    END,
    ordinal_position;

-- =====================================================
-- RESUMO: Verificar apenas as colunas FHIR
-- =====================================================
SELECT 
    'Colunas FHIR em cidades' as status,
    COUNT(*) as total_colunas_fhir
FROM information_schema.columns 
WHERE table_schema = 'public' 
  AND table_name = 'cidades'
  AND column_name IN ('codigo_fhir', 'fhir_code_system', 'regiao_saude', 'macrorregiao_saude', 'data_ultima_sincronizacao_fhir');
