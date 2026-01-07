-- Migração para adicionar colunas IBGE na tabela estados
-- Todas as colunas são NULLABLE para garantir retrocompatibilidade

-- Adicionar colunas IBGE
ALTER TABLE estados ADD COLUMN IF NOT EXISTS nome_oficial_ibge VARCHAR(200);
ALTER TABLE estados ADD COLUMN IF NOT EXISTS sigla_ibge VARCHAR(2);
ALTER TABLE estados ADD COLUMN IF NOT EXISTS regiao_ibge VARCHAR(50);
ALTER TABLE estados ADD COLUMN IF NOT EXISTS ativo_ibge BOOLEAN DEFAULT TRUE;
ALTER TABLE estados ADD COLUMN IF NOT EXISTS data_ultima_sincronizacao_ibge TIMESTAMPTZ;

-- Índice único para codigo_ibge (se ainda não existir)
CREATE UNIQUE INDEX IF NOT EXISTS idx_estados_codigo_ibge_unique 
ON estados(codigo_ibge) WHERE codigo_ibge IS NOT NULL;

