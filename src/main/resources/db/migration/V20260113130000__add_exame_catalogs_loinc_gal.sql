-- Description: Create unified exam catalog with multi-source support
-- Strategy: Single canonical table supporting SIGTAP, LOINC, GAL, TUSS
-- IMPORTANT: Does NOT create duplicate tables. All sources share same catalog.

-- 1. Create unified exam catalog table
CREATE TABLE IF NOT EXISTS public.catalogo_exames (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    
    -- Source control fields
    source_system VARCHAR(20) NOT NULL, -- SIGTAP, LOINC, GAL, TUSS, FHIR
    external_code VARCHAR(50) NOT NULL,
    external_version VARCHAR(50),
    last_sync_at TIMESTAMP WITH TIME ZONE,
    
    -- Multi-source codes (same exam may have codes in multiple systems)
    codigo_loinc VARCHAR(20),
    codigo_gal VARCHAR(20),
    codigo_sigtap VARCHAR(20),
    codigo_tuss VARCHAR(20),
    
    -- Exam data
    nome VARCHAR(500) NOT NULL,
    descricao TEXT,
    categoria VARCHAR(100),
    tipo_amostra VARCHAR(200),
    material VARCHAR(200),
    metodo VARCHAR(200),
    
    -- SIGTAP specific (when applicable)
    sigtap_competencia_inicial VARCHAR(6),
    sigtap_competencia_final VARCHAR(6),
    
    -- FHIR specific (when applicable)
    fhir_code_system VARCHAR(200),
    fhir_value_set VARCHAR(200),
    
    -- Standard fields
    active BOOLEAN DEFAULT true,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT uk_catalogo_exames_source_code UNIQUE(source_system, external_code)
);

-- 2. Create indexes for performance
CREATE INDEX IF NOT EXISTS idx_catalogo_exames_code ON public.catalogo_exames(external_code);
CREATE INDEX IF NOT EXISTS idx_catalogo_exames_source ON public.catalogo_exames(source_system);
CREATE INDEX IF NOT EXISTS idx_catalogo_exames_nome ON public.catalogo_exames(nome);
CREATE INDEX IF NOT EXISTS idx_catalogo_exames_loinc ON public.catalogo_exames(codigo_loinc);
CREATE INDEX IF NOT EXISTS idx_catalogo_exames_gal ON public.catalogo_exames(codigo_gal);
CREATE INDEX IF NOT EXISTS idx_catalogo_exames_sigtap ON public.catalogo_exames(codigo_sigtap);

-- 3. Add reference to exames table (keeping backward compatibility)
ALTER TABLE public.exames
ADD COLUMN IF NOT EXISTS catalogo_exame_id UUID;

ALTER TABLE public.exames
ADD CONSTRAINT fk_exames_catalogo
FOREIGN KEY (catalogo_exame_id) REFERENCES public.catalogo_exames(id);

CREATE INDEX IF NOT EXISTS idx_exames_catalogo_ref ON public.exames(catalogo_exame_id);

-- 4. Add comments
COMMENT ON TABLE public.catalogo_exames IS 'Catálogo unificado de exames. Suporta múltiplas origens: SIGTAP, LOINC, GAL, TUSS.';
COMMENT ON COLUMN public.catalogo_exames.source_system IS 'Sistema de origem: SIGTAP, LOINC, GAL, TUSS, FHIR';
COMMENT ON COLUMN public.catalogo_exames.external_code IS 'Código no sistema de origem';
COMMENT ON COLUMN public.catalogo_exames.codigo_loinc IS 'Código LOINC (padrão internacional) quando disponível';
COMMENT ON COLUMN public.catalogo_exames.codigo_gal IS 'Código GAL (LACEN) quando disponível';
COMMENT ON COLUMN public.catalogo_exames.codigo_sigtap IS 'Código SIGTAP (SUS) quando disponível';
COMMENT ON COLUMN public.exames.catalogo_exame_id IS 'Referência ao catálogo unificado de exames';

-- 5. Remove deprecated tables if they exist (optional cleanup)
-- These tables were created in error and should not exist
-- DROP TABLE IF EXISTS public.exames_loinc CASCADE;
-- DROP TABLE IF EXISTS public.exames_gal CASCADE;
-- DROP TABLE IF EXISTS public.tipos_amostra_gal CASCADE;
