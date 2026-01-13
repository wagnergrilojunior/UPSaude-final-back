-- Description: Enrich SIGTAP Ocupacao with FHIR CBO fields and add Conselhos Profissionais
-- Tables: sigtap_ocupacao, conselhos_profissionais

-- 1. Add FHIR CBO fields to existing sigtap_ocupacao table
ALTER TABLE public.sigtap_ocupacao
ADD COLUMN IF NOT EXISTS grande_grupo VARCHAR(100),
ADD COLUMN IF NOT EXISTS subgrupo_principal VARCHAR(100),
ADD COLUMN IF NOT EXISTS subgrupo VARCHAR(100),
ADD COLUMN IF NOT EXISTS familia VARCHAR(100),
ADD COLUMN IF NOT EXISTS descricao_fhir TEXT,
ADD COLUMN IF NOT EXISTS codigo_cbo_completo VARCHAR(10);

-- 2. Create professional councils catalog table
CREATE TABLE IF NOT EXISTS public.conselhos_profissionais (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo VARCHAR(20) NOT NULL UNIQUE,
    sigla VARCHAR(20) NOT NULL,
    nome VARCHAR(200) NOT NULL,
    descricao TEXT,
    uf VARCHAR(2),
    tipo VARCHAR(50),
    active BOOLEAN DEFAULT true,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- 3. Add foreign key to profissionais_saude table
ALTER TABLE public.profissionais_saude
ADD COLUMN IF NOT EXISTS conselho_profissional_id UUID;

-- 4. Add foreign key constraint
ALTER TABLE public.profissionais_saude
ADD CONSTRAINT fk_profissionais_conselho
FOREIGN KEY (conselho_profissional_id) REFERENCES public.conselhos_profissionais(id);

-- 5. Create indexes for performance
CREATE INDEX IF NOT EXISTS idx_sigtap_ocupacao_cbo_completo ON public.sigtap_ocupacao(codigo_cbo_completo);
CREATE INDEX IF NOT EXISTS idx_conselho_codigo ON public.conselhos_profissionais(codigo);
CREATE INDEX IF NOT EXISTS idx_conselho_sigla ON public.conselhos_profissionais(sigla);
CREATE INDEX IF NOT EXISTS idx_profissionais_conselho_ref ON public.profissionais_saude(conselho_profissional_id);

-- 6. Add comments
COMMENT ON COLUMN public.sigtap_ocupacao.grande_grupo IS 'Grande Grupo CBO (FHIR)';
COMMENT ON COLUMN public.sigtap_ocupacao.subgrupo_principal IS 'Subgrupo Principal CBO (FHIR)';
COMMENT ON COLUMN public.sigtap_ocupacao.subgrupo IS 'Subgrupo CBO (FHIR)';
COMMENT ON COLUMN public.sigtap_ocupacao.familia IS 'Família CBO (FHIR)';
COMMENT ON COLUMN public.sigtap_ocupacao.descricao_fhir IS 'Descrição detalhada do FHIR';
COMMENT ON COLUMN public.sigtap_ocupacao.codigo_cbo_completo IS 'Código CBO completo (ex: 2251-01)';
COMMENT ON TABLE public.conselhos_profissionais IS 'Catálogo de Conselhos Profissionais sincronizado do FHIR';
COMMENT ON COLUMN public.profissionais_saude.conselho_profissional_id IS 'Referência ao catálogo de Conselhos FHIR';
