-- =====================================================
-- MIGRATION: Adiciona payload_json ao import_job
-- Objetivo: armazenar metadados por tipo de job sem criar novas colunas por tipo
-- =====================================================

ALTER TABLE IF EXISTS public.import_job
    ADD COLUMN IF NOT EXISTS payload_json JSONB NULL;

CREATE INDEX IF NOT EXISTS idx_import_job_payload_json_gin
    ON public.import_job
    USING GIN (payload_json);


