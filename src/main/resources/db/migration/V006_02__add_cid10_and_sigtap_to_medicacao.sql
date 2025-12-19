-- Migration: Adicionar relações CID-10 e SIGTAP na tabela medicacoes
-- Esta migration adiciona as colunas de FK para cid10_subcategorias e sigtap_procedimento

-- Passo 1: Adicionar coluna cid10_subcategorias_id
ALTER TABLE medicacoes ADD COLUMN IF NOT EXISTS cid10_subcategorias_id UUID;

-- Passo 2: Criar índice para cid10_subcategorias_id
CREATE INDEX IF NOT EXISTS idx_medicacao_cid10 ON medicacoes(cid10_subcategorias_id);

-- Passo 3: Adicionar foreign key para cid10_subcategorias
DO $$ 
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint WHERE conname = 'fk_medicacao_cid10_subcategorias'
    ) THEN
        ALTER TABLE medicacoes 
        ADD CONSTRAINT fk_medicacao_cid10_subcategorias 
        FOREIGN KEY (cid10_subcategorias_id) REFERENCES cid10_subcategorias(id);
    END IF;
END $$;

-- Passo 4: Adicionar coluna sigtap_procedimento_id
ALTER TABLE medicacoes ADD COLUMN IF NOT EXISTS sigtap_procedimento_id UUID;

-- Passo 5: Criar índice para sigtap_procedimento_id
CREATE INDEX IF NOT EXISTS idx_medicacao_sigtap ON medicacoes(sigtap_procedimento_id);

-- Passo 6: Adicionar foreign key para sigtap_procedimento
DO $$ 
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint WHERE conname = 'fk_medicacao_sigtap_procedimento'
    ) THEN
        ALTER TABLE medicacoes 
        ADD CONSTRAINT fk_medicacao_sigtap_procedimento 
        FOREIGN KEY (sigtap_procedimento_id) REFERENCES sigtap_procedimento(id);
    END IF;
END $$;

-- Passo 7: Tentar migrar dados existentes de codigo_sigtap para sigtap_procedimento_id
-- (somente se existir a coluna codigo_sigtap e houver dados para migrar)
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'medicacoes' AND column_name = 'codigo_sigtap'
    ) THEN
        UPDATE medicacoes m
        SET sigtap_procedimento_id = sp.id
        FROM sigtap_procedimento sp
        WHERE m.codigo_sigtap = sp.codigo_oficial
          AND m.sigtap_procedimento_id IS NULL
          AND m.codigo_sigtap IS NOT NULL
          AND m.codigo_sigtap != '';
    END IF;
END $$;

COMMENT ON COLUMN medicacoes.cid10_subcategorias_id IS 'FK para subcategoria CID-10 oficial. Campo opcional.';
COMMENT ON COLUMN medicacoes.sigtap_procedimento_id IS 'FK para procedimento SIGTAP oficial. Campo opcional.';
