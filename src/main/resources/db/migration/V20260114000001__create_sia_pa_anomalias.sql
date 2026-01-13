-- =====================================================
-- MIGRATION: SIA-PA - Registro de Anomalias
-- =====================================================
-- Objetivo: Persistir anomalias detectadas no SIA-PA para auditoria e dashboards
-- Data: 2026-01-14
-- =====================================================

CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE IF NOT EXISTS public.sia_pa_anomalia (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ,
    ativo BOOLEAN NOT NULL DEFAULT true,

    -- Contexto
    competencia VARCHAR(6) NOT NULL,
    uf VARCHAR(2) NOT NULL,

    -- Identificação / classificação
    tipo_anomalia VARCHAR(80) NOT NULL,
    severidade VARCHAR(20) NOT NULL, -- BAIXA | MEDIA | ALTA | CRITICA

    -- Referência do alvo (pode ser CNES, procedimento, município, etc)
    chave VARCHAR(120),
    registro_id UUID,

    -- Descrição e métricas
    descricao TEXT NOT NULL,
    valor_atual NUMERIC(18, 2),
    valor_referencia NUMERIC(18, 2),
    delta NUMERIC(18, 2),
    delta_pct NUMERIC(18, 6),

    -- Payload opcional para detalhamento
    dados JSONB
);

-- Evita duplicidade para o mesmo alvo/tipo no mesmo recorte (competência/UF)
CREATE UNIQUE INDEX IF NOT EXISTS uk_sia_pa_anomalia_comp_uf_tipo_chave
    ON public.sia_pa_anomalia (competencia, uf, tipo_anomalia, chave);

CREATE INDEX IF NOT EXISTS idx_sia_pa_anomalia_comp_uf
    ON public.sia_pa_anomalia (competencia, uf);

CREATE INDEX IF NOT EXISTS idx_sia_pa_anomalia_tipo
    ON public.sia_pa_anomalia (tipo_anomalia);

