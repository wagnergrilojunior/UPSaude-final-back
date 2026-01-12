-- Migration para Unificar Diagnósticos e evitar duplicidade
-- Seguindo orientação de enriquecer tabelas existentes

-- 1. Remover a tabela recém-criada (que duplicava a função da doencas_paciente)
DROP TABLE IF EXISTS diagnosticos_paciente;

-- 2. Enriquecer a tabela existente doencas_paciente
ALTER TABLE doencas_paciente
ADD COLUMN IF NOT EXISTS ciap2_id UUID REFERENCES ciap2(id),
ADD COLUMN IF NOT EXISTS tipo_catalogo VARCHAR(10) DEFAULT 'CID10', -- CID10, CIAP2, OUTRO
ADD COLUMN IF NOT EXISTS codigo VARCHAR(20),
ADD COLUMN IF NOT EXISTS descricao_personalizada VARCHAR(500),
ADD COLUMN IF NOT EXISTS status VARCHAR(20) DEFAULT 'ativo',
ADD COLUMN IF NOT EXISTS cronico BOOLEAN DEFAULT FALSE,
ADD COLUMN IF NOT EXISTS data_sincronizacao TIMESTAMPTZ;

-- 3. Atualizar índices
CREATE INDEX IF NOT EXISTS idx_doencas_paciente_ciap2 ON doencas_paciente(ciap2_id);
CREATE INDEX IF NOT EXISTS idx_doencas_paciente_tipo ON doencas_paciente(tipo_catalogo);
