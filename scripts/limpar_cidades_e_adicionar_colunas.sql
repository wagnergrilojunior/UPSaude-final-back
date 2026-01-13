-- =====================================================
-- SCRIPT: Limpar dados de cidades e adicionar colunas FHIR
-- =====================================================
-- Objetivo: Limpar dados para permitir adicionar colunas FHIR sem timeout
-- Autor: UPSaúde
-- =====================================================
-- 
-- INSTRUÇÕES:
-- Execute este script diretamente no banco de dados PostgreSQL
-- usando psql ou uma ferramenta de administração de banco.
-- 
-- ATENÇÃO: Este script irá DELETAR TODOS os dados da tabela cidades!
-- =====================================================

BEGIN;

-- 1. Desabilitar temporariamente a foreign key
ALTER TABLE public.enderecos DROP CONSTRAINT IF EXISTS fki7v6nvtiwatf10fe6c8n7qbde;

-- 2. Limpar dados da tabela cidades
TRUNCATE TABLE public.cidades CASCADE;

-- 3. Adicionar colunas FHIR
ALTER TABLE public.cidades ADD COLUMN IF NOT EXISTS codigo_fhir VARCHAR(20);
ALTER TABLE public.cidades ADD COLUMN IF NOT EXISTS fhir_code_system VARCHAR(200);
ALTER TABLE public.cidades ADD COLUMN IF NOT EXISTS regiao_saude VARCHAR(100);
ALTER TABLE public.cidades ADD COLUMN IF NOT EXISTS macrorregiao_saude VARCHAR(100);
ALTER TABLE public.cidades ADD COLUMN IF NOT EXISTS data_ultima_sincronizacao_fhir TIMESTAMPTZ;

-- 4. Recriar a foreign key
ALTER TABLE public.enderecos 
ADD CONSTRAINT fki7v6nvtiwatf10fe6c8n7qbde 
FOREIGN KEY (cidade_id) 
REFERENCES public.cidades(id);

COMMIT;

-- Verificar se as colunas foram criadas
SELECT column_name, data_type, is_nullable
FROM information_schema.columns 
WHERE table_schema = 'public' 
  AND table_name = 'cidades'
  AND column_name IN ('codigo_fhir', 'fhir_code_system', 'regiao_saude', 'macrorregiao_saude', 'data_ultima_sincronizacao_fhir')
ORDER BY column_name;
