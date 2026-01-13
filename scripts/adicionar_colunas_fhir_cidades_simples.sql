-- =====================================================
-- SCRIPT SQL SIMPLES: Adicionar colunas FHIR na tabela cidades
-- =====================================================
-- Este script APENAS adiciona colunas à tabela existente "cidades"
-- NÃO altera nomes, NÃO cria tabelas, NÃO remove dados
-- =====================================================

-- Adicionar colunas FHIR em cidades
ALTER TABLE public.cidades ADD COLUMN IF NOT EXISTS codigo_fhir VARCHAR(20);
ALTER TABLE public.cidades ADD COLUMN IF NOT EXISTS fhir_code_system VARCHAR(200);
ALTER TABLE public.cidades ADD COLUMN IF NOT EXISTS regiao_saude VARCHAR(100);
ALTER TABLE public.cidades ADD COLUMN IF NOT EXISTS macrorregiao_saude VARCHAR(100);
ALTER TABLE public.cidades ADD COLUMN IF NOT EXISTS data_ultima_sincronizacao_fhir TIMESTAMPTZ;
