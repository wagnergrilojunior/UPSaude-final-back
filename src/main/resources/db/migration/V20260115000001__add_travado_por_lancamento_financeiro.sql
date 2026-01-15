ALTER TABLE IF EXISTS public.lancamento_financeiro
    ADD COLUMN IF NOT EXISTS travado_por_id UUID;

