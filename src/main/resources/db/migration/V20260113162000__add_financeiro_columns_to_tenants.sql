-- =====================================================
-- MIGRATION: Adicionar colunas financeiras à tabela tenants
-- =====================================================
-- Descrição: Adiciona colunas do embeddable ConfiguracaoFinanceiraTenant
--            à tabela tenants para suportar configurações financeiras
-- Data: 2026-01-13
-- =====================================================

-- Adicionar colunas de configuração financeira
ALTER TABLE public.tenants
ADD COLUMN IF NOT EXISTS financeiro_habilitado BOOLEAN NOT NULL DEFAULT false,
ADD COLUMN IF NOT EXISTS plano_contas_padrao_id UUID,
ADD COLUMN IF NOT EXISTS regra_competencia VARCHAR(50),
ADD COLUMN IF NOT EXISTS politica_reserva_consumo VARCHAR(50);

-- Adicionar comentários nas colunas
COMMENT ON COLUMN public.tenants.financeiro_habilitado IS 'Indica se o módulo financeiro está habilitado para este tenant';
COMMENT ON COLUMN public.tenants.plano_contas_padrao_id IS 'Referência ao plano de contas padrão do tenant';
COMMENT ON COLUMN public.tenants.regra_competencia IS 'Regra de competência financeira: MENSAL | CUSTOM';
COMMENT ON COLUMN public.tenants.politica_reserva_consumo IS 'Política de reserva e consumo: HIBRIDO | RESERVA_AGENDAMENTO | CONSUMO_ATENDIMENTO';

-- Adicionar foreign key para plano_contas_padrao_id (se a tabela plano_contas existir)
DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = 'public' AND table_name = 'plano_contas') THEN
        IF NOT EXISTS (
            SELECT 1 FROM information_schema.table_constraints 
            WHERE constraint_schema = 'public' 
            AND constraint_name = 'fk_tenants_plano_contas_padrao'
        ) THEN
            ALTER TABLE public.tenants
            ADD CONSTRAINT fk_tenants_plano_contas_padrao
            FOREIGN KEY (plano_contas_padrao_id)
            REFERENCES public.plano_contas(id)
            ON DELETE SET NULL;
        END IF;
    END IF;
END $$;

-- Criar índice para melhorar performance de consultas
CREATE INDEX IF NOT EXISTS idx_tenants_financeiro_habilitado 
ON public.tenants(financeiro_habilitado) 
WHERE financeiro_habilitado = true;
