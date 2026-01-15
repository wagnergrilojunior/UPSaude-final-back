-- Migration: Adicionar campos de fechamento em competencia_financeira
-- Estes campos foram incorporados de CompetenciaFinanceiraTenant

-- Adicionar colunas de status e fechamento
ALTER TABLE public.competencia_financeira
ADD COLUMN IF NOT EXISTS status VARCHAR(20) NOT NULL DEFAULT 'ABERTA',
ADD COLUMN IF NOT EXISTS fechada_em TIMESTAMP WITH TIME ZONE,
ADD COLUMN IF NOT EXISTS fechada_por UUID,
ADD COLUMN IF NOT EXISTS motivo_fechamento TEXT,
ADD COLUMN IF NOT EXISTS snapshot_hash VARCHAR(64),
ADD COLUMN IF NOT EXISTS documento_bpa_fechamento_id UUID,
ADD COLUMN IF NOT EXISTS hash_movimentacoes VARCHAR(64),
ADD COLUMN IF NOT EXISTS hash_bpa VARCHAR(64),
ADD COLUMN IF NOT EXISTS validacao_integridade BOOLEAN;

-- Adicionar constraint de foreign key para documento_bpa_fechamento
ALTER TABLE public.competencia_financeira
ADD CONSTRAINT fk_competencia_financeira_documento_bpa_fechamento
FOREIGN KEY (documento_bpa_fechamento_id)
REFERENCES public.documento_faturamento(id)
ON DELETE SET NULL;

-- Adicionar índice para status
CREATE INDEX IF NOT EXISTS idx_competencia_financeira_status
ON public.competencia_financeira(status);

-- Comentários nas colunas
COMMENT ON COLUMN public.competencia_financeira.status IS 'Status da competência: ABERTA ou FECHADA';
COMMENT ON COLUMN public.competencia_financeira.fechada_em IS 'Data e hora do fechamento da competência';
COMMENT ON COLUMN public.competencia_financeira.fechada_por IS 'ID do usuário que fechou a competência';
COMMENT ON COLUMN public.competencia_financeira.motivo_fechamento IS 'Motivo do fechamento da competência';
COMMENT ON COLUMN public.competencia_financeira.snapshot_hash IS 'Hash para integridade do fechamento';
COMMENT ON COLUMN public.competencia_financeira.documento_bpa_fechamento_id IS 'Referência ao documento BPA gerado no fechamento';
COMMENT ON COLUMN public.competencia_financeira.hash_movimentacoes IS 'Hash das movimentações no momento do fechamento';
COMMENT ON COLUMN public.competencia_financeira.hash_bpa IS 'Hash dos dados BPA consolidados';
COMMENT ON COLUMN public.competencia_financeira.validacao_integridade IS 'Flag indicando se hashMovimentacoes == hashBpa';
