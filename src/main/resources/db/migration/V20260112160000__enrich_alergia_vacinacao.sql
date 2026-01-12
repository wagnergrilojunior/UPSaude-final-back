-- Description: Enrich AlergiaPaciente with FHIR status fields and add supporting indexes
-- Table: alergias_paciente

ALTER TABLE public.alergias_paciente
ADD COLUMN clinical_status VARCHAR(20) DEFAULT 'active',
ADD COLUMN verification_status VARCHAR(20) DEFAULT 'unconfirmed',
ADD COLUMN grau_certeza VARCHAR(50);

COMMENT ON COLUMN public.alergias_paciente.clinical_status IS 'Estado clínico da alergia (active, inactive, resolved)';
COMMENT ON COLUMN public.alergias_paciente.verification_status IS 'Estado de verificação da alergia (unconfirmed, confirmed, refuted, entered-in-error)';

-- Add indexes for better performance on clinical alerts
CREATE INDEX idx_alergia_paciente_clinical_status ON public.alergias_paciente(clinical_status);
CREATE INDEX idx_alergia_paciente_verification_status ON public.alergias_paciente(verification_status);

-- Ensure lotes_vacina has the necessary columns (already exists in entity but good to ensure indexes)
CREATE INDEX IF NOT EXISTS idx_lotes_vacina_validade ON public.lotes_vacina(data_validade);
CREATE INDEX IF NOT EXISTS idx_lotes_vacina_status ON public.lotes_vacina(ativo, quantidade_disponivel);
