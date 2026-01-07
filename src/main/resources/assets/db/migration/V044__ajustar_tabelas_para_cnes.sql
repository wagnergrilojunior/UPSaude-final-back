-- =====================================================
-- MIGRATION: Ajustar Tabelas Existentes para CNES
-- =====================================================
-- Objetivo: Adicionar campos CNES nas tabelas existentes
-- Autor: UPSaúde
-- =====================================================

-- ========== TABELA estabelecimentos ==========
ALTER TABLE public.estabelecimentos
    ADD COLUMN IF NOT EXISTS esfera_administrativa VARCHAR(50) NULL,
    ADD COLUMN IF NOT EXISTS codigo_ibge_municipio VARCHAR(7) NULL,
    ADD COLUMN IF NOT EXISTS data_ultima_sincronizacao_cnes TIMESTAMPTZ NULL,
    ADD COLUMN IF NOT EXISTS versao_cnes VARCHAR(6) NULL;

-- Índices para estabelecimentos
CREATE INDEX IF NOT EXISTS idx_estabelecimentos_esfera_administrativa
    ON public.estabelecimentos (esfera_administrativa);

CREATE INDEX IF NOT EXISTS idx_estabelecimentos_codigo_ibge
    ON public.estabelecimentos (codigo_ibge_municipio);

-- ========== TABELA profissionais_saude ==========
ALTER TABLE public.profissionais_saude
    ADD COLUMN IF NOT EXISTS cns VARCHAR(15) NULL,
    ADD COLUMN IF NOT EXISTS data_ultima_sincronizacao_cnes TIMESTAMPTZ NULL;

-- Índice para profissionais_saude
CREATE INDEX IF NOT EXISTS idx_profissional_cns
    ON public.profissionais_saude (cns);

-- ========== TABELA equipes_saude ==========
ALTER TABLE public.equipes_saude
    ADD COLUMN IF NOT EXISTS data_ultima_sincronizacao_cnes TIMESTAMPTZ NULL;

-- ========== TABELA equipamentos ==========
ALTER TABLE public.equipamentos
    ADD COLUMN IF NOT EXISTS data_ultima_sincronizacao_cnes TIMESTAMPTZ NULL;

