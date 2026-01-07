-- Migração para adicionar colunas IBGE na tabela cidades
-- Todas as colunas são NULLABLE para garantir retrocompatibilidade

-- Adicionar colunas IBGE
ALTER TABLE cidades ADD COLUMN IF NOT EXISTS nome_oficial_ibge VARCHAR(200);
ALTER TABLE cidades ADD COLUMN IF NOT EXISTS uf_ibge VARCHAR(2);
ALTER TABLE cidades ADD COLUMN IF NOT EXISTS populacao_estimada INTEGER;
ALTER TABLE cidades ADD COLUMN IF NOT EXISTS ativo_ibge BOOLEAN DEFAULT TRUE;
ALTER TABLE cidades ADD COLUMN IF NOT EXISTS data_ultima_sincronizacao_ibge TIMESTAMPTZ;

-- Índice único para codigo_ibge (se ainda não existir)
CREATE UNIQUE INDEX IF NOT EXISTS idx_cidades_codigo_ibge_unique 
ON cidades(codigo_ibge) WHERE codigo_ibge IS NOT NULL;

