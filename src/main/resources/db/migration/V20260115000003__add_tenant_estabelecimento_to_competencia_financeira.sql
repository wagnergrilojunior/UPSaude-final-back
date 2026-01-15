ALTER TABLE public.competencia_financeira
    ADD COLUMN IF NOT EXISTS tenant_id UUID,
    ADD COLUMN IF NOT EXISTS estabelecimento_id UUID;

ALTER TABLE public.competencia_financeira
    DROP CONSTRAINT IF EXISTS uk_competencia_financeira_codigo;

ALTER TABLE public.competencia_financeira
    ADD CONSTRAINT uk_competencia_financeira_tenant_codigo UNIQUE (tenant_id, codigo);

CREATE INDEX IF NOT EXISTS idx_competencia_financeira_tenant_data
ON public.competencia_financeira(tenant_id, data_inicio, data_fim);

