-- ===========================================================================
-- Migração V005_01: Adicionar coluna cid10_subcategorias_id em doencas
-- ===========================================================================
-- Execute este script PRIMEIRO para permitir que a aplicação funcione.
-- Depois execute V005_02 para migrar os dados e finalizar a refatoração.
-- ===========================================================================

-- 1. Adicionar coluna de FK (permitir NULL para não quebrar registros existentes)
ALTER TABLE doencas 
ADD COLUMN IF NOT EXISTS cid10_subcategorias_id UUID;

-- 2. Criar índice para performance
CREATE INDEX IF NOT EXISTS idx_doencas_cid10_subcategoria 
ON doencas(cid10_subcategorias_id);

-- 3. Adicionar Foreign Key (sem ON DELETE CASCADE para segurança)
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint 
        WHERE conname = 'fk_doencas_cid10_subcategorias'
    ) THEN
        ALTER TABLE doencas 
        ADD CONSTRAINT fk_doencas_cid10_subcategorias 
        FOREIGN KEY (cid10_subcategorias_id) 
        REFERENCES cid10_subcategorias(id);
    END IF;
END $$;

-- Comentário
COMMENT ON COLUMN doencas.cid10_subcategorias_id IS 
'FK para subcategoria CID-10 oficial. Temporariamente nullable durante migração.';
