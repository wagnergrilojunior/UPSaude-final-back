-- Description: Restructure ExamePaciente for FHIR compliance (ServiceRequest/DiagnosticReport) and link to Atendimento
-- Table: exames

-- 1. Update exames table
ALTER TABLE public.exames
ADD COLUMN IF NOT EXISTS atendimento_id UUID,
ADD COLUMN IF NOT EXISTS status VARCHAR(20) DEFAULT 'active',
ADD COLUMN IF NOT EXISTS intent VARCHAR(20) DEFAULT 'order',
ADD COLUMN IF NOT EXISTS priority VARCHAR(20) DEFAULT 'routine',
ADD COLUMN IF NOT EXISTS category VARCHAR(50) DEFAULT 'laboratory';

-- Add foreign key constraint for atendimento
ALTER TABLE public.exames
ADD CONSTRAINT fk_exames_atendimento
FOREIGN KEY (atendimento_id) REFERENCES public.atendimentos(id);

-- Add indexes for performance
CREATE INDEX IF NOT EXISTS idx_exames_atendimento ON public.exames(atendimento_id);
CREATE INDEX IF NOT EXISTS idx_exames_status ON public.exames(status);
CREATE INDEX IF NOT EXISTS idx_exames_category ON public.exames(category);

COMMENT ON COLUMN public.exames.status IS 'FHIR status: draft, active, on-hold, revoked, completed, entered-in-error, unknown';
COMMENT ON COLUMN public.exames.intent IS 'FHIR intent: proposal, plan, directive, order, original-order, reflex-order, filler-order, instance-order, option';
COMMENT ON COLUMN public.exames.priority IS 'FHIR priority: routine, urgent, asap, stat';
