CREATE INDEX IF NOT EXISTS idx_competencia_financeira_tenant_codigo
ON public.competencia_financeira(tenant_id, codigo);

