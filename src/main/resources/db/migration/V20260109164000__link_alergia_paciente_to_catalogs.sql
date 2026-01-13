-- Description: Add columns to link AlergiaPaciente with FHIR catalogs
-- Table: alergias_paciente
ALTER TABLE public.alergias_paciente
ADD COLUMN alergeno_id UUID REFERENCES public.alergenos(id),
ADD COLUMN reacao_adversa_catalogo_id UUID REFERENCES public.reacoes_adversas_catalogo(id),
ADD COLUMN criticidade_id UUID REFERENCES public.criticidade_alergia(id),
ADD COLUMN categoria_agente_id UUID REFERENCES public.categoria_agente_alergia(id);

COMMENT ON COLUMN public.alergias_paciente.alergeno_id IS 'Vínculo opcional com o catálogo de alérgenos FHIR';
COMMENT ON COLUMN public.alergias_paciente.reacao_adversa_catalogo_id IS 'Vínculo opcional com o catálogo de reações MedDRA';
COMMENT ON COLUMN public.alergias_paciente.criticidade_id IS 'Vínculo opcional com o catálogo de criticidade FHIR';
COMMENT ON COLUMN public.alergias_paciente.categoria_agente_id IS 'Vínculo opcional com o catálogo de categorias de agente FHIR';
