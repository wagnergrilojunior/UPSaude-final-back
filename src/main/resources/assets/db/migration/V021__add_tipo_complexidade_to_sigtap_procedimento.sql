-- =====================================================
-- MIGRATION: Adicionar campo tipo_complexidade em sigtap_procedimento
-- =====================================================
-- Objetivo: Adicionar campo tipo_complexidade (TP_COMPLEXIDADE) que estava faltando na tabela
-- Autor: UPSaúde
-- =====================================================

ALTER TABLE public.sigtap_procedimento
    ADD COLUMN IF NOT EXISTS tipo_complexidade VARCHAR(1) NULL;

COMMENT ON COLUMN public.sigtap_procedimento.tipo_complexidade IS 'Tipo de complexidade do procedimento. Valores: 1 (Baixa), 2 (Média), 3 (Alta)';

