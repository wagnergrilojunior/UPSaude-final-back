-- =========================================================
-- FINANCEIRO: Regra de Classificação Contábil
-- Regra: somente criação/enriquecimento (sem DROP/RENAME)
-- =========================================================

CREATE TABLE regra_classificacao_contabil (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    estabelecimento_id UUID REFERENCES estabelecimentos(id),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    conta_contabil_destino_id UUID NOT NULL REFERENCES conta_contabil(id),
    escopo VARCHAR(50) NOT NULL,
    prioridade INTEGER NOT NULL,
    condicao_jsonb JSONB
);

CREATE INDEX idx_regra_classificacao_tenant_escopo ON regra_classificacao_contabil (tenant_id, escopo, prioridade);

