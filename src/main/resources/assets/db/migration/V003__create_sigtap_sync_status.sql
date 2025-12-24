-- =====================================================
-- MIGRATION: Tabela de Status de Sincroniza??o SIGTAP
-- =====================================================
-- Objetivo: Rastrear o estado da sincroniza??o para permitir retomar de onde parou
-- Autor: UPSa?de
-- =====================================================

CREATE TABLE IF NOT EXISTS public.sigtap_sync_status (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,

    competencia VARCHAR(6) NOT NULL,
    etapa_atual VARCHAR(50) NOT NULL,
    status VARCHAR(20) NOT NULL,
    progresso_atual VARCHAR(500) NULL,
    total_registros_processados BIGINT NULL,
    ultimo_erro TEXT NULL,
    tentativas_erro INTEGER NULL DEFAULT 0,
    ultima_tentativa_em TIMESTAMPTZ NULL
);

CREATE INDEX IF NOT EXISTS idx_sigtap_sync_status_competencia
    ON public.sigtap_sync_status (competencia);

CREATE INDEX IF NOT EXISTS idx_sigtap_sync_status_status
    ON public.sigtap_sync_status (status);

CREATE INDEX IF NOT EXISTS idx_sigtap_sync_status_competencia_status
    ON public.sigtap_sync_status (competencia, status);
