-- =====================================================
-- MIGRATION: Aumentar tamanho da coluna nome em sigtap_servico
-- =====================================================
-- Objetivo: Aumentar o tamanho da coluna nome de VARCHAR(100) para VARCHAR(300)
--           para suportar nomes maiores que aparecem nos dados do SIGTAP
-- Autor: UPSaúde
-- =====================================================

ALTER TABLE IF EXISTS public.sigtap_servico 
    ALTER COLUMN nome TYPE VARCHAR(300);

