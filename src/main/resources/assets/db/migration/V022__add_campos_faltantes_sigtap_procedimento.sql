-- =====================================================
-- MIGRATION: Adicionar campos faltantes em sigtap_procedimento
-- =====================================================
-- Objetivo: Adicionar campos pontos, financiamento_id e rubrica_id que estavam faltando na tabela
-- Autor: UPSaúde
-- =====================================================

-- Adicionar campo pontos
ALTER TABLE public.sigtap_procedimento
    ADD COLUMN IF NOT EXISTS pontos INTEGER NULL;

-- Adicionar relacionamento com financiamento
ALTER TABLE public.sigtap_procedimento
    ADD COLUMN IF NOT EXISTS financiamento_id UUID NULL;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint 
        WHERE conname = 'fk_sigtap_procedimento_financiamento'
    ) THEN
        ALTER TABLE public.sigtap_procedimento
            ADD CONSTRAINT fk_sigtap_procedimento_financiamento 
            FOREIGN KEY (financiamento_id) 
            REFERENCES public.sigtap_financiamento(id);
    END IF;
END $$;

-- Adicionar relacionamento com rubrica
ALTER TABLE public.sigtap_procedimento
    ADD COLUMN IF NOT EXISTS rubrica_id UUID NULL;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint 
        WHERE conname = 'fk_sigtap_procedimento_rubrica'
    ) THEN
        ALTER TABLE public.sigtap_procedimento
            ADD CONSTRAINT fk_sigtap_procedimento_rubrica 
            FOREIGN KEY (rubrica_id) 
            REFERENCES public.sigtap_rubrica(id);
    END IF;
END $$;

-- Comentários
COMMENT ON COLUMN public.sigtap_procedimento.pontos IS 'Quantidade de pontos do procedimento';
COMMENT ON COLUMN public.sigtap_procedimento.financiamento_id IS 'Referência ao financiamento do procedimento';
COMMENT ON COLUMN public.sigtap_procedimento.rubrica_id IS 'Referência à rubrica (sub-tipo de financiamento) do procedimento';

