-- Migration: Remover SigtapCid e migrar sigtap_procedimento_cid para usar cid10_subcategorias
-- Esta migration migra os relacionamentos de sigtap_procedimento_cid para usar cid10_subcategorias oficial

-- Passo 1: Adicionar nova coluna cid10_subcategorias_id na tabela sigtap_procedimento_cid
ALTER TABLE sigtap_procedimento_cid ADD COLUMN IF NOT EXISTS cid10_subcategorias_id UUID;

-- Passo 2: Migrar dados existentes - mapear pelo código CID
-- O código em sigtap_cid.codigo_oficial corresponde ao subcat em cid10_subcategorias
UPDATE sigtap_procedimento_cid spc
SET cid10_subcategorias_id = cs.id
FROM sigtap_cid sc, cid10_subcategorias cs
WHERE spc.cid_id = sc.id
  AND sc.codigo_oficial = cs.subcat
  AND spc.cid10_subcategorias_id IS NULL;

-- Passo 3: Criar índice na nova coluna
CREATE INDEX IF NOT EXISTS idx_sigtap_proc_cid_cid10_id ON sigtap_procedimento_cid(cid10_subcategorias_id);

-- Passo 4: Adicionar foreign key para cid10_subcategorias
DO $$ 
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint WHERE conname = 'fk_sigtap_proc_cid_cid10_subcategorias'
    ) THEN
        ALTER TABLE sigtap_procedimento_cid 
        ADD CONSTRAINT fk_sigtap_proc_cid_cid10_subcategorias 
        FOREIGN KEY (cid10_subcategorias_id) REFERENCES cid10_subcategorias(id);
    END IF;
END $$;

-- Passo 5: Remover registros órfãos (que não conseguiram migrar)
DELETE FROM sigtap_procedimento_cid WHERE cid10_subcategorias_id IS NULL;

-- Passo 6: Remover constraint antiga e coluna cid_id
DO $$ 
BEGIN
    IF EXISTS (
        SELECT 1 FROM pg_constraint WHERE conname = 'fk_sigtap_proc_cid_cid_id'
    ) THEN
        ALTER TABLE sigtap_procedimento_cid DROP CONSTRAINT fk_sigtap_proc_cid_cid_id;
    END IF;
END $$;

-- Passo 7: Remover índice antigo
DROP INDEX IF EXISTS idx_sigtap_proc_cid_cid_id;

-- Passo 8: Remover constraint única antiga se existir
DO $$ 
BEGIN
    IF EXISTS (
        SELECT 1 FROM pg_constraint WHERE conname = 'uk_sigtap_proc_cid_proc_cid_comp'
    ) THEN
        ALTER TABLE sigtap_procedimento_cid DROP CONSTRAINT uk_sigtap_proc_cid_proc_cid_comp;
    END IF;
END $$;

-- Passo 9: Remover coluna antiga cid_id
ALTER TABLE sigtap_procedimento_cid DROP COLUMN IF EXISTS cid_id;

-- Passo 10: Tornar cid10_subcategorias_id NOT NULL
ALTER TABLE sigtap_procedimento_cid ALTER COLUMN cid10_subcategorias_id SET NOT NULL;

-- Passo 11: Criar nova constraint única
DO $$ 
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint WHERE conname = 'uk_sigtap_proc_cid_proc_cid10_comp'
    ) THEN
        ALTER TABLE sigtap_procedimento_cid 
        ADD CONSTRAINT uk_sigtap_proc_cid_proc_cid10_comp 
        UNIQUE (procedimento_id, cid10_subcategorias_id, competencia_inicial);
    END IF;
END $$;

-- Passo 12: Remover tabela sigtap_cid
DROP TABLE IF EXISTS sigtap_cid CASCADE;

COMMENT ON COLUMN sigtap_procedimento_cid.cid10_subcategorias_id IS 'FK para subcategoria CID-10 oficial. Migrado de sigtap_cid.';
