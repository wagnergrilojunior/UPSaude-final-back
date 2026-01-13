-- Description: Enrich geographic and demographic tables with FHIR fields
-- Strategy: Add FHIR codes to existing tables, never create duplicates
-- Tables: estados, cidades (geographic), NO new demographic tables (use enums)

-- 1. Enrich estados with FHIR BRDivisaoGeografica fields
ALTER TABLE public.estados
ADD COLUMN IF NOT EXISTS codigo_fhir VARCHAR(20),
ADD COLUMN IF NOT EXISTS fhir_code_system VARCHAR(200),
ADD COLUMN IF NOT EXISTS data_ultima_sincronizacao_fhir TIMESTAMP WITH TIME ZONE;

-- 2. Enrich cidades with FHIR BRDivisaoGeografica fields
ALTER TABLE public.cidades
ADD COLUMN IF NOT EXISTS codigo_fhir VARCHAR(20),
ADD COLUMN IF NOT EXISTS fhir_code_system VARCHAR(200),
ADD COLUMN IF NOT EXISTS regiao_saude VARCHAR(100),
ADD COLUMN IF NOT EXISTS macrorregiao_saude VARCHAR(100),
ADD COLUMN IF NOT EXISTS data_ultima_sincronizacao_fhir TIMESTAMP WITH TIME ZONE;

-- 3. Create indexes for FHIR fields
CREATE INDEX IF NOT EXISTS idx_estados_codigo_fhir ON public.estados(codigo_fhir);
CREATE INDEX IF NOT EXISTS idx_cidades_codigo_fhir ON public.cidades(codigo_fhir);
CREATE INDEX IF NOT EXISTS idx_cidades_regiao_saude ON public.cidades(regiao_saude);

-- 4. Add comments
COMMENT ON COLUMN public.estados.codigo_fhir IS 'Código FHIR do BRDivisaoGeografica';
COMMENT ON COLUMN public.estados.fhir_code_system IS 'CodeSystem FHIR de origem';
COMMENT ON COLUMN public.estados.data_ultima_sincronizacao_fhir IS 'Data da última sincronização FHIR';

COMMENT ON COLUMN public.cidades.codigo_fhir IS 'Código FHIR do BRDivisaoGeografica';
COMMENT ON COLUMN public.cidades.fhir_code_system IS 'CodeSystem FHIR de origem';
COMMENT ON COLUMN public.cidades.regiao_saude IS 'Região de saúde (FHIR)';
COMMENT ON COLUMN public.cidades.macrorregiao_saude IS 'Macrorregião de saúde (FHIR)';
COMMENT ON COLUMN public.cidades.data_ultima_sincronizacao_fhir IS 'Data da última sincronização FHIR';

-- NOTE: Dados demográficos (Raça/Cor, Sexo) são tratados via Enums Java
-- com códigos FHIR embutidos (RacaCorEnum, SexoEnum), não necessitam tabelas.
