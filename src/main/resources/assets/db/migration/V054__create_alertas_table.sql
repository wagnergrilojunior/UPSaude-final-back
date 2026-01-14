-- =====================================================
-- MIGRATION: Criar tabela de alertas
-- =====================================================
-- Objetivo: Criar tabela para armazenar regras de alertas
--           e seus estados de disparo
-- Autor: UPSaúde
-- =====================================================

CREATE TABLE IF NOT EXISTS public.alertas (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ,
    ativo BOOLEAN NOT NULL DEFAULT true,
    
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT,
    
    tipo_metrica VARCHAR(50) NOT NULL,
    operador VARCHAR(20) NOT NULL,
    valor_limite NUMERIC(19, 2) NOT NULL,
    valor_atual NUMERIC(19, 2),
    periodo_dias INTEGER,
    
    estabelecimento_id UUID,
    medico_id UUID,
    
    severidade VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ATIVO',
    
    disparado_em TIMESTAMPTZ,
    resolvido_em TIMESTAMPTZ,
    
    CONSTRAINT fk_alerta_tenant FOREIGN KEY (tenant_id) REFERENCES public.tenants(id) ON DELETE CASCADE
);

-- Índices
CREATE INDEX IF NOT EXISTS idx_alerta_tenant ON public.alertas(tenant_id);
CREATE INDEX IF NOT EXISTS idx_alerta_status ON public.alertas(status);
CREATE INDEX IF NOT EXISTS idx_alerta_tipo_metrica ON public.alertas(tipo_metrica);
CREATE INDEX IF NOT EXISTS idx_alerta_ativo ON public.alertas(ativo);
CREATE INDEX IF NOT EXISTS idx_alerta_estabelecimento ON public.alertas(estabelecimento_id);
CREATE INDEX IF NOT EXISTS idx_alerta_medico ON public.alertas(medico_id);

-- Comentários
COMMENT ON TABLE public.alertas IS 'Tabela para armazenar regras de alertas e seus estados';
COMMENT ON COLUMN public.alertas.tipo_metrica IS 'Tipo de métrica a ser monitorada';
COMMENT ON COLUMN public.alertas.operador IS 'Operador de comparação (MAIOR_QUE, MENOR_QUE, etc)';
COMMENT ON COLUMN public.alertas.valor_limite IS 'Valor limite para disparo do alerta';
COMMENT ON COLUMN public.alertas.valor_atual IS 'Valor atual da métrica (atualizado na verificação)';
COMMENT ON COLUMN public.alertas.status IS 'Status do alerta: ATIVO, DISPARADO, RESOLVIDO, DESATIVADO';
