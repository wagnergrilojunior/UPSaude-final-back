-- =====================================================
-- MIGRATION: Aumentar tamanho da coluna nome para 300 em todas as tabelas SIGTAP
-- =====================================================
-- Objetivo: Aumentar o tamanho da coluna nome de VARCHAR(100) para VARCHAR(300)
--           em todas as tabelas SIGTAP que ainda têm esse tamanho para evitar
--           erros com nomes maiores que aparecem nos dados do SIGTAP
-- Autor: UPSaúde
-- =====================================================

-- Tabelas com campo nome VARCHAR(100) que precisam ser aumentadas para 300
ALTER TABLE IF EXISTS public.sigtap_detalhe 
    ALTER COLUMN nome TYPE VARCHAR(300);

ALTER TABLE IF EXISTS public.sigtap_financiamento 
    ALTER COLUMN nome TYPE VARCHAR(300);

ALTER TABLE IF EXISTS public.sigtap_modalidade 
    ALTER COLUMN nome TYPE VARCHAR(300);

ALTER TABLE IF EXISTS public.sigtap_registro 
    ALTER COLUMN nome TYPE VARCHAR(300);

ALTER TABLE IF EXISTS public.sigtap_regra_condicionada 
    ALTER COLUMN nome TYPE VARCHAR(300);

ALTER TABLE IF EXISTS public.sigtap_renases 
    ALTER COLUMN nome TYPE VARCHAR(300);

ALTER TABLE IF EXISTS public.sigtap_rubrica 
    ALTER COLUMN nome TYPE VARCHAR(300);

ALTER TABLE IF EXISTS public.sigtap_sia_sih 
    ALTER COLUMN nome TYPE VARCHAR(300);

ALTER TABLE IF EXISTS public.sigtap_tipo_leito 
    ALTER COLUMN nome TYPE VARCHAR(300);

ALTER TABLE IF EXISTS public.sigtap_tuss 
    ALTER COLUMN nome TYPE VARCHAR(300);

