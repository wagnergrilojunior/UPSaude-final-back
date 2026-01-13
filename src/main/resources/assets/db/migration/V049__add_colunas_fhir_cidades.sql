-- =====================================================
-- MIGRATION: Adicionar colunas FHIR nas tabelas estados e cidades
-- =====================================================
-- Objetivo: Adicionar colunas FHIR para integração com BRDivisaoGeografica
-- Autor: UPSaúde
-- =====================================================
-- 
-- IMPORTANTE: Esta migração APENAS ADICIONA COLUNAS às tabelas existentes.
-- NÃO altera nomes de tabelas, NÃO cria novas tabelas, NÃO remove dados.
-- Usa ALTER TABLE ... ADD COLUMN IF NOT EXISTS para adicionar colunas.
-- =====================================================

-- Adicionar colunas FHIR em estados (todas nullable para garantir retrocompatibilidade)
-- A tabela "estados" permanece com o mesmo nome e estrutura existente
ALTER TABLE public.estados ADD COLUMN IF NOT EXISTS codigo_fhir VARCHAR(20);
ALTER TABLE public.estados ADD COLUMN IF NOT EXISTS fhir_code_system VARCHAR(200);
ALTER TABLE public.estados ADD COLUMN IF NOT EXISTS data_ultima_sincronizacao_fhir TIMESTAMPTZ;

-- Adicionar colunas FHIR em cidades (todas nullable para garantir retrocompatibilidade)
-- A tabela "cidades" permanece com o mesmo nome e estrutura existente
ALTER TABLE public.cidades ADD COLUMN IF NOT EXISTS codigo_fhir VARCHAR(20);
ALTER TABLE public.cidades ADD COLUMN IF NOT EXISTS fhir_code_system VARCHAR(200);
ALTER TABLE public.cidades ADD COLUMN IF NOT EXISTS regiao_saude VARCHAR(100);
ALTER TABLE public.cidades ADD COLUMN IF NOT EXISTS macrorregiao_saude VARCHAR(100);
ALTER TABLE public.cidades ADD COLUMN IF NOT EXISTS data_ultima_sincronizacao_fhir TIMESTAMPTZ;
