-- =====================================================
-- MIGRATION: Corrigir DEFAULT gen_random_uuid() para tabelas SIGTAP e adicionar estabelecimento_id em import_job_error
-- =====================================================
-- Objetivo: 
-- 1. Adicionar DEFAULT gen_random_uuid() nas tabelas SIGTAP que não têm (necessário para JdbcEntityBatchWriter)
-- 2. Adicionar estabelecimento_id na tabela import_job_error (necessário porque ImportJobError estende BaseEntity)
-- Autor: UPSaúde
-- =====================================================

-- =====================================================
-- 1. Adicionar DEFAULT gen_random_uuid() nas tabelas SIGTAP
-- =====================================================

-- Tabelas de referência (tb_*)
ALTER TABLE IF EXISTS public.sigtap_cid 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE IF EXISTS public.sigtap_ocupacao 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE IF EXISTS public.sigtap_financiamento 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE IF EXISTS public.sigtap_rubrica 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE IF EXISTS public.sigtap_modalidade 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE IF EXISTS public.sigtap_registro 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE IF EXISTS public.sigtap_tipo_leito 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE IF EXISTS public.sigtap_servico 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE IF EXISTS public.sigtap_servico_classificacao 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE IF EXISTS public.sigtap_habilitacao 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE IF EXISTS public.sigtap_grupo_habilitacao 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE IF EXISTS public.sigtap_regra_condicionada 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE IF EXISTS public.sigtap_renases 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE IF EXISTS public.sigtap_tuss 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE IF EXISTS public.sigtap_componente_rede 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE IF EXISTS public.sigtap_rede_atencao 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE IF EXISTS public.sigtap_sia_sih 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE IF EXISTS public.sigtap_detalhe 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE IF EXISTS public.sigtap_descricao 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE IF EXISTS public.sigtap_descricao_detalhe 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE IF EXISTS public.sigtap_forma_organizacao 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE IF EXISTS public.sigtap_procedimento 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE IF EXISTS public.sigtap_grupo 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE IF EXISTS public.sigtap_sub_grupo 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

-- Tabelas relacionais (rl_*)
ALTER TABLE IF EXISTS public.sigtap_rl_procedimento_modalidade 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE IF EXISTS public.sigtap_rl_procedimento_registro 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE IF EXISTS public.sigtap_rl_procedimento_comp_rede 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE IF EXISTS public.sigtap_rl_procedimento_origem 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE IF EXISTS public.sigtap_rl_procedimento_regra_cond 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE IF EXISTS public.sigtap_rl_procedimento_renases 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE IF EXISTS public.sigtap_rl_procedimento_tuss 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE IF EXISTS public.sigtap_rl_procedimento_cid 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE IF EXISTS public.sigtap_rl_procedimento_ocupacao 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE IF EXISTS public.sigtap_rl_procedimento_habilitacao 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE IF EXISTS public.sigtap_rl_procedimento_leito 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE IF EXISTS public.sigtap_rl_procedimento_servico 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE IF EXISTS public.sigtap_rl_procedimento_incremento 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE IF EXISTS public.sigtap_rl_procedimento_sia_sih 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE IF EXISTS public.sigtap_rl_procedimento_detalhe 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE IF EXISTS public.sigtap_rl_excecao_compatibilidade 
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

-- =====================================================
-- 2. Adicionar estabelecimento_id na tabela import_job_error
-- =====================================================

-- Adiciona a coluna estabelecimento_id (nullable inicialmente para não quebrar dados existentes)
ALTER TABLE IF EXISTS public.import_job_error 
    ADD COLUMN IF NOT EXISTS estabelecimento_id UUID NULL;

-- Adiciona foreign key para estabelecimentos
DO $$ 
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint 
        WHERE conname = 'fk_import_job_error_estabelecimento'
    ) THEN
        ALTER TABLE public.import_job_error
            ADD CONSTRAINT fk_import_job_error_estabelecimento 
            FOREIGN KEY (estabelecimento_id) 
            REFERENCES public.estabelecimentos(id) 
            ON DELETE SET NULL;
    END IF;
END $$;

-- Adiciona índice para melhorar performance de consultas
CREATE INDEX IF NOT EXISTS idx_import_job_error_estabelecimento_id 
    ON public.import_job_error (estabelecimento_id);

