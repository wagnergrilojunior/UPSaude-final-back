-- =====================================================
-- MIGRATION: Adicionar campo consorcio na tabela tenants
-- =====================================================
-- Objetivo: Adicionar campo boolean para indicar se o tenant é um consórcio
-- Autor: UPSaúde
-- =====================================================

-- Adicionar coluna consorcio na tabela tenants
ALTER TABLE public.tenants
ADD COLUMN IF NOT EXISTS consorcio BOOLEAN NOT NULL DEFAULT false;

-- Criar índice para melhorar performance em consultas por consórcio
CREATE INDEX IF NOT EXISTS idx_tenants_consorcio ON public.tenants(consorcio);

-- Comentário na coluna
COMMENT ON COLUMN public.tenants.consorcio IS 'Indica se o tenant é um consórcio';
