-- Description: Restructure Receita for FHIR compliance and link to Atendimento
-- Tables: receitas, receita_itens

-- 1. Update receitas table
ALTER TABLE public.receitas
ADD COLUMN IF NOT EXISTS atendimento_id UUID,
ADD COLUMN IF NOT EXISTS fhir_status VARCHAR(20) DEFAULT 'active',
ADD COLUMN IF NOT EXISTS intent VARCHAR(20) DEFAULT 'order';

-- Add foreign key constraint for atendimento
ALTER TABLE public.receitas
ADD CONSTRAINT fk_receita_atendimento
FOREIGN KEY (atendimento_id) REFERENCES public.atendimentos(id);

-- Add index for performance
CREATE INDEX IF NOT EXISTS idx_receita_atendimento ON public.receitas(atendimento_id);
CREATE INDEX IF NOT EXISTS idx_receita_fhir_status ON public.receitas(fhir_status);

-- 2. Update receita_itens table
ALTER TABLE public.receita_itens
ADD COLUMN IF NOT EXISTS dosage_instruction JSONB;

-- Ensure indexes for structured mapping
CREATE INDEX IF NOT EXISTS idx_receita_item_medicamento ON public.receita_itens(medicamento_id);
CREATE INDEX IF NOT EXISTS idx_receita_item_via ON public.receita_itens(via_administracao_id);

COMMENT ON COLUMN public.receitas.fhir_status IS 'FHIR status: active, on-hold, cancelled, completed, entered-in-error, stopped, draft, unknown';
COMMENT ON COLUMN public.receitas.intent IS 'FHIR intent: proposal, plan, order, original-order, reflex-order, filler-order, instance-order, option';
