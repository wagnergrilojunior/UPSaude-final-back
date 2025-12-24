-- =====================================================
-- MIGRATION: Adiciona estabelecimento_id ao import_job
-- Objetivo: Permitir que jobs de importação sejam associados a um estabelecimento específico
-- =====================================================

-- Adiciona a coluna estabelecimento_id (nullable, pois nem todos os jobs precisam estar associados a um estabelecimento)
ALTER TABLE IF EXISTS public.import_job
    ADD COLUMN IF NOT EXISTS estabelecimento_id UUID NULL;

-- Adiciona a foreign key constraint (sem IF NOT EXISTS, pois PostgreSQL não suporta)
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint 
        WHERE conname = 'fk_import_job_estabelecimento'
    ) THEN
        ALTER TABLE public.import_job
            ADD CONSTRAINT fk_import_job_estabelecimento 
            FOREIGN KEY (estabelecimento_id) 
            REFERENCES public.estabelecimentos(id);
    END IF;
END $$;

-- Cria índice para otimizar consultas por estabelecimento
CREATE INDEX IF NOT EXISTS idx_import_job_estabelecimento 
    ON public.import_job (estabelecimento_id);

-- Cria índice composto para consultas por tenant e estabelecimento
CREATE INDEX IF NOT EXISTS idx_import_job_tenant_estabelecimento 
    ON public.import_job (tenant_id, estabelecimento_id);

