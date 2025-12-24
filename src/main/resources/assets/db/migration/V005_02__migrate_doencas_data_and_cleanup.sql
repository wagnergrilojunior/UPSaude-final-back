-- ===========================================================================
-- Migração V005_02: Migrar dados e finalizar refatoração Doencas/CID-10
-- ===========================================================================
-- Execute este script DEPOIS de V005_01 e após verificar que a aplicação 
-- está funcionando corretamente.
--
-- Este script:
-- 1. Migra dados existentes para usar o CID-10 oficial
-- 2. Remove colunas duplicadas (nome, descricao, codigo_cid_principal, etc)
-- ===========================================================================

-- ===========================================================================
-- ETAPA 1: MIGRAR DADOS EXISTENTES
-- ===========================================================================

-- 1.1 Tentar associar pelo codigo_cid_principal (se existir)
UPDATE doencas d
SET cid10_subcategorias_id = (
    SELECT c.id 
    FROM cid10_subcategorias c 
    WHERE c.subcat = d.codigo_cid_principal
    LIMIT 1
)
WHERE d.cid10_subcategorias_id IS NULL
AND d.codigo_cid_principal IS NOT NULL
AND d.codigo_cid_principal != ''
AND EXISTS (
    SELECT 1 FROM cid10_subcategorias c WHERE c.subcat = d.codigo_cid_principal
);

-- 1.2 Log de registros não migrados
DO $$
DECLARE
    v_count INTEGER;
BEGIN
    SELECT COUNT(*) INTO v_count
    FROM doencas d
    WHERE d.cid10_subcategorias_id IS NULL;
    
    IF v_count > 0 THEN
        RAISE NOTICE 'ATENÇÃO: % registros em doencas ainda sem CID-10 associado.', v_count;
        RAISE NOTICE 'Esses registros precisam de associação manual antes de tornar a FK obrigatória.';
    ELSE
        RAISE NOTICE 'SUCESSO: Todos os registros foram migrados para CID-10.';
    END IF;
END $$;

-- ===========================================================================
-- ETAPA 2: REMOVER COLUNAS DUPLICADAS (somente após migração completa)
-- ===========================================================================
-- ATENÇÃO: Descomentar estas linhas APENAS após verificar que todos os
-- registros foram migrados (cid10_subcategorias_id preenchido em todos).

-- Verificar se pode prosseguir
-- DO $$
-- DECLARE
--     v_null_count INTEGER;
-- BEGIN
--     SELECT COUNT(*) INTO v_null_count FROM doencas WHERE cid10_subcategorias_id IS NULL;
--     IF v_null_count > 0 THEN
--         RAISE EXCEPTION 'Migração incompleta: % registros sem CID-10', v_null_count;
--     END IF;
-- END $$;

-- Remover índice antigo do nome
-- DROP INDEX IF EXISTS idx_doenca_nome;

-- Remover coluna nome (agora vem de cid10_subcategorias.descricao)
-- ALTER TABLE doencas DROP COLUMN IF EXISTS nome;

-- Remover coluna descricao (agora vem de cid10_subcategorias.descricao)
-- ALTER TABLE doencas DROP COLUMN IF EXISTS descricao;

-- Remover campos do embeddable classificacao_doenca que agora vêm do CID-10
-- ALTER TABLE doencas DROP COLUMN IF EXISTS codigo_cid_principal;
-- ALTER TABLE doencas DROP COLUMN IF EXISTS categoria;
-- ALTER TABLE doencas DROP COLUMN IF EXISTS subcategoria;

-- ===========================================================================
-- ETAPA 3: TORNAR FK OBRIGATÓRIA (somente após migração completa)
-- ===========================================================================
-- ATENÇÃO: Descomentar APENAS após migração completa

-- ALTER TABLE doencas ALTER COLUMN cid10_subcategorias_id SET NOT NULL;

-- Adicionar constraint de unicidade (uma doença por CID-10)
-- ALTER TABLE doencas ADD CONSTRAINT uk_doencas_cid10_subcategoria 
-- UNIQUE (cid10_subcategorias_id);

-- ===========================================================================
-- COMENTÁRIOS
-- ===========================================================================

COMMENT ON COLUMN doencas.cid10_subcategorias_id IS 
'FK para subcategoria CID-10 oficial. Fonte de verdade para nome, descrição e código.';

-- ===========================================================================
-- FIM DA MIGRAÇÃO
-- ===========================================================================
